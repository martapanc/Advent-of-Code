import path from "node:path";
import {readInputLineByLine} from "@utils/io";
export async function part1(inputFile: string) {
    return await day6(inputFile, calcGrantTotal);
}

export async function part2(inputFile: string) {
    return await day6raw(inputFile, calcGrantTotalCephalopod);
}

async function day6raw(inputFile: string, calcFn: (lines: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    return calcFn(lines);
}

async function day6(inputFile: string, calcFn?: (numberRows: number[][], ops: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const numberRows: number[][] = lines.slice(0, -1).map(line => line.trim().split(/\s+/).map(Number));
    const ops: string[] = lines[lines.length - 1].split(/\s+/);

    return calcFn?.(numberRows, ops);
}

function calcGrantTotalCephalopod(lines: string[]): number {
    const maxLen = Math.max(...lines.map(l => l.length));
    const grid = lines.map(line => line.padEnd(maxLen, ' '));
    const numRows = grid.length - 1; // exclude operator row
    const opRow = grid[grid.length - 1];

    // Problems are separated by full blank columns across all rows.
    const separatorCols: boolean[] = Array.from({ length: maxLen }, (_, c) =>
        grid.every(row => row[c] === ' ')
    );

    const spans: Array<[number, number]> = [];
    let start = 0;
    for (let c = 0; c <= maxLen; c++) {
        if (c === maxLen || separatorCols[c]) {
            if (start <= c - 1) spans.push([start, c - 1]);
            start = c + 1;
        }
    }

    let total = 0;
    for (let p = spans.length - 1; p >= 0; p--) {
        const [startCol, endCol] = spans[p];
        const op = opRow.slice(startCol, endCol + 1).split('').find(ch => ch !== ' ');
        if (!op) continue;
        const numbers: number[] = [];

        // Read columns right-to-left within this problem's range
        for (let c = endCol; c >= startCol; c--) {
            const chars = Array.from({ length: numRows }, (_, r) => grid[r][c]);
            if (chars.every(ch => ch === ' ')) continue;
            const digits = chars.filter(ch => ch !== ' ').join('');
            numbers.push(Number(digits));
        }

        total += numbers.reduce((acc, cur) => op === '+' ? acc + cur : acc * cur, op === '+' ? 0 : 1);
    }
    return total;
}

function calcGrantTotal(numberRows: number[][], ops: string[]): number {
    let total: number = 0;
    const cols = numberRows[0].length;
    for (let i = 0; i < cols; i++) {
        const op = ops[i];
        const column: number[] = numberRows.map(row => row[i]);
        const result = column.reduce((acc, cur) => op === '+' ? acc + cur : acc * cur, op === '+' ? 0 : 1);
        total += result;
    }
    return total;
}
