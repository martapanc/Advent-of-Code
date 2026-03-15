import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day3(inputFile, getMulExpressions);
}

export async function part2(inputFile: string) {
    return await day3(inputFile, getEnabledMulExpressions);
}

async function day3(inputFile: string, calcFn?: (input: string) => string[] | RegExpMatchArray | null) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return computeMultiplicationResult(lines, calcFn);
}

function computeMultiplicationResult(lines: string[], calcFn?: (input: string) => string[] | RegExpMatchArray | null){
    let result = 0;
    lines.forEach(line => {
        const matches = calcFn?.(line);

        matches?.forEach(match => {
            const parsedMatch = match.replace("mul(", "").replace(")", "");
            const nums: number[] = parsedMatch.split(",").map(str => Number.parseInt(str));
            result += (nums[0] * nums[1]);
        })
    });

    return result;
}

export function getMulExpressions(input: string) {
    return input.match(/mul\([0-9]{1,3},[0-9]{1,3}\)/g);
}

// Process input as a single line so that any "don't()" carry over automatically
export function getEnabledMulExpressions(input: string) {
    const matches = input.match(/don't\(\)|do\(\)|mul\([0-9]{1,3},[0-9]{1,3}\)/g);

    let enable = true;
    const enabledExpressions: string[] = [];

    matches?.forEach(match => {
        if (match === "don't()") {
            enable = false;
            return;
        } else if (match === "do()") {
            enable = true;
            return;
        } else {
            if (enable) {
                enabledExpressions.push(match);
            }
        }
    })
    return enabledExpressions;
}
