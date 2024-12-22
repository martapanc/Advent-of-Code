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
    const res = getBestSequence(initial);

    return res.bananas;
}

function getBestSequence(buyers: number[], steps = 2000) {
    const sequenceScores: Map<string, number> = new Map();

    for (const initial of buyers) {
        const states = calcNthSecretNumber(initial, 1999).states;
        const prices = states.map(s => s.lastDigit);
        const diffs = states.map(s => s.diff);

        for (let i = 0; i <= diffs.length - 4; i++) {
            const seq = diffs.slice(i, i + 4).join(",");
            if (!sequenceScores.has(seq)) sequenceScores.set(seq, 0);
            sequenceScores.set(seq, sequenceScores.get(seq)! + prices[i + 4]);
        }
    }

    let bestSequence = null;
    let maxBananas = 0;

    for (const [seq, score] of sequenceScores.entries()) {
        if (score > maxBananas) {
            maxBananas = score;
            bestSequence = seq.split(",").map(Number);
        }
    }

    return { sequence: bestSequence || [], bananas: maxBananas }
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