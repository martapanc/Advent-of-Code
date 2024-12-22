import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day22(inputFile, calcAndSumSecretNumbers);
}

export async function part2(inputFile: string) {
    return await day22(inputFile, optimiseBananaPurchases);
}

async function day22(inputFile: string, calcFn?: (lines: number[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(lines.map(s => Number.parseInt(s)));
}

function calcAndSumSecretNumbers(initial: number[]) {
    let sum = 0;
    initial.forEach(num => {
        sum += calcNthSecretNumber(num, 2000).nextSecretNumber;
    });
    return sum;
}

function optimiseBananaPurchases(initial: number[]) {
    const ranges: { [key: string]: number[] } = {};
    initial.forEach(num => {
        let secretNumber = num;
        const visited = new Set<string>();
        const differences: number[] = [];

        for (let i = 0; i < 2000; i++) {
            const nextSecretNumber = calcSecretNumber(secretNumber);
            differences.push(Number((nextSecretNumber % 10) - (secretNumber % 10)));
            secretNumber = nextSecretNumber;

            if (differences.length === 4) {
                const key = differences.join(',');
                if (!visited.has(key)) {
                    if (ranges[key] === undefined) ranges[key] = [];
                    ranges[key].push(Number((nextSecretNumber % 10)));
                    visited.add(key);
                }
                differences.shift();
            }
        }
    });

    const sums = [];
    for (const range of Object.values(ranges)) {
        let sum = 0;
        for (const num of range) {
            sum += num;
        }
        sums.push(sum);
    }

    let maxBananas = 0;
    for (const sum of sums) {
        if (sum > maxBananas) {
            maxBananas = sum;
        }
    }

    return maxBananas;
}

type SecretNumberState = { secretNum?: number, lastDigit: number, diff: number }

export function calcNthSecretNumber(input: number, n: number) {
    let i = 0;
    let nextSecretNumber = input;
    const states: SecretNumberState[] = [];
    let prevLastDigit = input % 10;

    while (i < n) {
        nextSecretNumber = calcSecretNumber(nextSecretNumber);
        states.push({ lastDigit: nextSecretNumber % 10, diff: (nextSecretNumber % 10) - prevLastDigit });
        prevLastDigit = nextSecretNumber % 10;
        i++;
    }

    return { nextSecretNumber, states };
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