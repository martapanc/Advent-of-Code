import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day3(inputFile, computeMultiplicationResult);
}

export async function part2(inputFile: string) {
    return await day3(inputFile);
}

async function day3(inputFile: string, calcFn?: (lines: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(lines);
}

function computeMultiplicationResult(lines: string[]){
    let result = 0;
    lines.forEach(line => {
        const matches = getMulExpressions(line);

        matches?.forEach(match => {
            const parsedMatch = match.replace("mul(", "").replace(")", "");
            const nums: number[] = parsedMatch.split(",").map(str => Number.parseInt(str));
            result += (nums[0] * nums[1]);
        })
    });

    return result
}

export function getMulExpressions(input: string) {
    return input.match(/mul\([0-9]{1,3},[0-9]{1,3}\)/g);
}

export function getEnabledMulExpressions(input: string) {
    const matches = input.match(/don't\(\)|do\(\)|mul\([0-9]{1,3},[0-9]{1,3}\)/g);


}
