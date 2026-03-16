import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {forEach} from "mathjs";

export async function part1(inputFile: string) {
    return await day6(inputFile, calcGrantTotal);
}

export async function part2(inputFile: string) {
    return await day6(inputFile);
}

async function day6(inputFile: string, calcFn?: (numberRows: number[][], ops: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const numberRows: number[][] = lines.slice(0, -1).map(line => line.trim().split(/\s+/).map(Number));
    const ops: string[] = lines[lines.length - 1].split(/\s+/);

    return calcFn?.(numberRows, ops);
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