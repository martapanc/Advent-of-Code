import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day10(inputFile, calcButtonPresses);
}

export async function part2(inputFile: string) {
    return await day10(inputFile);
}

async function day10(inputFile: string, calcFn?: (machines: Machine[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    const machines = lines.map(parseMachine);
    return calcFn?.(machines);
}

type Machine = { target: bigint; buttons: bigint[]; };

function parseMachine(line: string): Machine {
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
