import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day22(inputFile, calcAndSumSecretNumbers);
}

export async function part2(inputFile: string) {
    return await day22(inputFile);
}

async function day22(inputFile: string, calcFn?: (lines: number[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(lines.map(s => Number.parseInt(s)));
}

function calcAndSumSecretNumbers(initial: number[]) {
    let sum = 0;
    initial.forEach(num => {
        sum += calcNthSecretNumber(num, 2000);
    });
    return sum;
}

export function calcNthSecretNumber(input: number, n: number) {
    let i = 0;
    let nextSecretNumber = input;
    while (i < n) {
        nextSecretNumber = calcSecretNumber(nextSecretNumber);
        i++;
    }

    return nextSecretNumber;
}

export function calcSecretNumber(input: number) {
    let result = prune(mix(input, input * 64));
    result = prune(mix(result, Math.floor(result / 32)));
    result = prune(mix(result, result * 2048));

    return result;
}

export function mix(a: number, b: number) {
    return (a ^ b) >>> 0;
}

export function prune(number: number) {
    return number % 16777216;
}