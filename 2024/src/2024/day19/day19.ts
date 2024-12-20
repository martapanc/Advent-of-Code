import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day19(inputFile);
}

export async function part2(inputFile: string) {
    return await day19(inputFile);
}

async function day19(inputFile: string, calcFn?: (lines: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(lines);
}