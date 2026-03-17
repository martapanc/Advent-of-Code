import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day10(inputFile, parseMachines, calcButtonPresses);
}

export async function part2(inputFile: string) {
    return await day10(inputFile, parseJoltageMachines, calcJoltagePresses);
}

async function day10<T>(inputFile: string, parseFn: (lines: string[]) => T, calcFn: (parsed: T) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    const parsed = parseFn(lines);
    return calcFn(parsed);
}

type Machine = { target: bigint; buttons: bigint[]; };

function parseMachines(lines: string[]): Machine[] {
    return lines
        .map(line => line.trim())
        .filter(line => line.length > 0)
        .map(parseMachineLine);
}

function parseMachineLine(line: string): Machine {
    const diagramMatch = line.match(/\[([.#]+)\]/);
    const diagram = diagramMatch![1];
    let target = 0n;
    for (let i = 0; i < diagram.length; i++) {
        if (diagram[i] === '#') {
            target |= 1n << BigInt(i);
        }
    }

    const buttons: bigint[] = [];
    const buttonMatches = line.matchAll(/\(([^)]+)\)/g);
    for (const match of buttonMatches) {
        const content = match[1].trim();
        if (content.length === 0) continue;
        let mask = 0n;
        for (const part of content.split(',')) {
            const idx = Number.parseInt(part.trim(), 10);
            if (Number.isNaN(idx)) continue;
            mask |= 1n << BigInt(idx);
        }
        buttons.push(mask);
    }

    return {target, buttons};
}

function calcButtonPresses(machines: Machine[]): number {
    let total = 0;
    machines.forEach((machine) => {
        total += minButtonPresses(machine.target, machine.buttons);
    });
    return total;
}

type JoltageMachine = { targets: number[]; buttons: number[][]; };

function parseJoltageMachines(lines: string[]): JoltageMachine[] {
    return lines
        .map(line => line.trim())
        .filter(line => line.length > 0)
        .map(parseJoltageLine);
}

function parseJoltageLine(line: string): JoltageMachine {
    const targetMatch = line.match(/\{([^}]*)\}/);
    if (!targetMatch) {
        throw new Error(`Invalid line (missing joltage): ${line}`);
    }
    const targets = targetMatch[1]
        .split(',')
        .map(item => Number.parseInt(item.trim(), 10));

    const buttons: number[][] = [];
    const buttonMatches = line.matchAll(/\(([^)]*)\)/g);
    for (const match of buttonMatches) {
        const content = match[1].trim();
        if (content.length === 0) continue;
        const indices = content
            .split(',')
            .map(item => Number.parseInt(item.trim(), 10))
            .filter(Number.isFinite);
        buttons.push(indices);
    }

    return {targets, buttons};
}

function minButtonPresses(target: bigint, buttons: bigint[]): number {
    if (target === 0n) return 0;
    if (buttons.length === 0) return -1;

    const mid = Math.floor(buttons.length / 2);
    const left = buttons.slice(0, mid);
    const right = buttons.slice(mid);

    const leftMap = buildSubsetMinWeights(left);
    let best = Number.POSITIVE_INFINITY;

    const visitRight = (index: number, mask: bigint, weight: number) => {
        if (index === right.length) {
            const needed = target ^ mask;
            const leftWeight = leftMap.get(needed);
            if (leftWeight !== undefined) {
                const total = leftWeight + weight;
                if (total < best) best = total;
            }
            return;
        }
        visitRight(index + 1, mask, weight);
        visitRight(index + 1, mask ^ right[index], weight + 1);
    };

    visitRight(0, 0n, 0);

    return Number.isFinite(best) ? best : -1;
}

function buildSubsetMinWeights(buttons: bigint[]): Map<bigint, number> {
    const map = new Map<bigint, number>();

    const visit = (index: number, mask: bigint, weight: number) => {
        if (index === buttons.length) {
            const existing = map.get(mask);
            if (existing === undefined || weight < existing) {
                map.set(mask, weight);
            }
            return;
        }
        visit(index + 1, mask, weight);
        visit(index + 1, mask ^ buttons[index], weight + 1);
    };

    visit(0, 0n, 0);
    return map;
}

function calcJoltagePresses(machines: JoltageMachine[]): number {
    let total = 0;
    for (const machine of machines) {
        total += minJoltagePresses(machine.targets, machine.buttons);
    }
    return total;
}

function minJoltagePresses(targets: number[], buttons: number[][]): number {
    if (targets.every(value => value === 0)) return 0;

    const upperBounds = buttons.map((indices) => {
        if (indices.length === 0) return 0;
        let bound = Number.POSITIVE_INFINITY;
        for (const idx of indices) {
            bound = Math.min(bound, targets[idx]);
        }
        return Number.isFinite(bound) ? bound : 0;
    });

    const keep = upperBounds
        .map((bound, idx) => ({bound, idx}))
        .filter(item => item.bound > 0);

    if (keep.length === 0) return -1;

    const keptButtons = keep.map(item => buttons[item.idx]);
    const keptBounds = keep.map(item => item.bound);

    const rowCount = targets.length;
    const colCount = keptButtons.length;

    const matrix: Fraction[][] = Array.from({length: rowCount}, () =>
        Array.from({length: colCount}, () => fracFromInt(0))
    );

    keptButtons.forEach((indices, col) => {
        for (const row of indices) {
            matrix[row][col] = fracFromInt(1);
        }
    });

    const rhs = targets.map(fracFromInt);
    const {pivotCols, rrefMatrix, rrefRhs, inconsistent} = rrefSystem(matrix, rhs);
    if (inconsistent) return -1;

    const freeCols: number[] = [];
    for (let col = 0; col < colCount; col++) {
        if (!pivotCols.includes(col)) freeCols.push(col);
    }

    const freeOrder = freeCols
        .map(col => ({col, bound: keptBounds[col]}))
        .sort((a, b) => a.bound - b.bound);

    const freeColsOrdered = freeOrder.map(item => item.col);
    const freeBounds = freeOrder.map(item => item.bound);

    const pivotRows = new Map<number, number>();
    pivotCols.forEach((col, idx) => {
        pivotRows.set(col, idx);
    });

    const pivotInfos = pivotCols.map((col) => {
        const row = pivotRows.get(col)!;
        const coeffs = freeColsOrdered.map(freeCol => rrefMatrix[row][freeCol]);
        return {
            col,
            rhs: rrefRhs[row],
            coeffs
        };
    });

    let best = Number.POSITIVE_INFINITY;
    const freeValues = new Array<number>(freeColsOrdered.length).fill(0);

    const evaluate = () => {
        let sum = 0;
        for (const value of freeValues) sum += value;
        if (sum >= best) return;

        for (const pivot of pivotInfos) {
            let value = pivot.rhs;
            for (let i = 0; i < freeValues.length; i++) {
                const coeff = pivot.coeffs[i];
                if (coeff.num === 0n || freeValues[i] === 0) continue;
                value = fracSub(value, fracMulInt(coeff, freeValues[i]));
            }
            if (value.num % value.den !== 0n) return;
            const pivotValue = Number(value.num / value.den);
            if (pivotValue < 0) return;
            if (pivotValue > keptBounds[pivot.col]) return;
            sum += pivotValue;
            if (sum >= best) return;
        }
        best = Math.min(best, sum);
    };

    const dfs = (index: number, sum: number) => {
        if (sum >= best) return;
        if (index === freeValues.length) {
            evaluate();
            return;
        }
        const bound = freeBounds[index];
        for (let value = 0; value <= bound; value++) {
            freeValues[index] = value;
            dfs(index + 1, sum + value);
        }
    };

    dfs(0, 0);
    return Number.isFinite(best) ? best : -1;
}

type Fraction = { num: bigint; den: bigint };

function fracFromInt(value: number): Fraction {
    return {num: BigInt(value), den: 1n};
}

function fracNormalize(num: bigint, den: bigint): Fraction {
    if (num === 0n) return {num: 0n, den: 1n};
    if (den < 0n) {
        num = -num;
        den = -den;
    }
    const g = gcdBigInt(num < 0n ? -num : num, den);
    return {num: num / g, den: den / g};
}

function fracAdd(a: Fraction, b: Fraction): Fraction {
    return fracNormalize(a.num * b.den + b.num * a.den, a.den * b.den);
}

function fracSub(a: Fraction, b: Fraction): Fraction {
    return fracNormalize(a.num * b.den - b.num * a.den, a.den * b.den);
}

function fracMul(a: Fraction, b: Fraction): Fraction {
    return fracNormalize(a.num * b.num, a.den * b.den);
}

function fracMulInt(a: Fraction, value: number): Fraction {
    return fracNormalize(a.num * BigInt(value), a.den);
}

function fracDiv(a: Fraction, b: Fraction): Fraction {
    if (b.num === 0n) throw new Error("Division by zero fraction");
    return fracNormalize(a.num * b.den, a.den * b.num);
}

function gcdBigInt(a: bigint, b: bigint): bigint {
    let x = a;
    let y = b;
    while (y !== 0n) {
        const t = x % y;
        x = y;
        y = t;
    }
    return x;
}

function rrefSystem(matrix: Fraction[][], rhs: Fraction[]) {
    const rowCount = matrix.length;
    const colCount = matrix[0]?.length ?? 0;
    let row = 0;
    const pivotCols: number[] = [];

    for (let col = 0; col < colCount && row < rowCount; col++) {
        let pivot = row;
        while (pivot < rowCount && matrix[pivot][col].num === 0n) {
            pivot++;
        }
        if (pivot === rowCount) continue;

        if (pivot !== row) {
            [matrix[pivot], matrix[row]] = [matrix[row], matrix[pivot]];
            [rhs[pivot], rhs[row]] = [rhs[row], rhs[pivot]];
        }

        const pivotVal = matrix[row][col];
        for (let j = col; j < colCount; j++) {
            matrix[row][j] = fracDiv(matrix[row][j], pivotVal);
        }
        rhs[row] = fracDiv(rhs[row], pivotVal);

        for (let i = 0; i < rowCount; i++) {
            if (i === row) continue;
            const factor = matrix[i][col];
            if (factor.num === 0n) continue;
            for (let j = col; j < colCount; j++) {
                matrix[i][j] = fracSub(matrix[i][j], fracMul(factor, matrix[row][j]));
            }
            rhs[i] = fracSub(rhs[i], fracMul(factor, rhs[row]));
        }

        pivotCols.push(col);
        row++;
    }

    let inconsistent = false;
    for (let i = 0; i < rowCount; i++) {
        let allZero = true;
        for (let j = 0; j < colCount; j++) {
            if (matrix[i][j].num !== 0n) {
                allZero = false;
                break;
            }
        }
        if (allZero && rhs[i].num !== 0n) {
            inconsistent = true;
            break;
        }
    }

    return {pivotCols, rrefMatrix: matrix, rrefRhs: rhs, inconsistent};
}
