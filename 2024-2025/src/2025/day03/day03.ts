import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day3(inputFile, computeTotalOutputJoltage);
}

export async function part2(inputFile: string) {
    return await day3(inputFile, computeMaxJoltage12);
}

async function day3(inputFile: string, calcFn?: (batteryBanks: number[][]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    const batteryBanks = lines.map(line => {
       return line.split('').map(item => Number.parseInt(item));
    });
    return calcFn?.(batteryBanks);
}

const maxInArray = (arr: number[]) => arr.reduce((a, b) => Math.max(a, b), -Infinity);

function getAllIndices(arr: number[], value: number): number[] {
    const indexes: number[] = []
    let i = -1;
    while ((i = arr.indexOf(value, i + 1)) != -1) {
        indexes.push(i);
    }
    return indexes;
}

function largestKDigits(bank: number[], k: number): number {
    const toRemove = bank.length - k;
    const stack: number[] = [];
    let removed = 0;

    for (const digit of bank) {
        while (removed < toRemove && stack.length > 0 && stack[stack.length - 1] < digit) {
            stack.pop();
            removed++;
        }
        stack.push(digit);
    }

    return Number(stack.slice(0, k).join(''));
}

function computeMaxJoltage12(batteryBanks: number[][]): number {
    return batteryBanks.reduce((sum, bank) => sum + largestKDigits(bank, 12), 0);
}

function computeTotalOutputJoltage(batteryBanks: number[][]): number {
    let sum = 0;
    batteryBanks.forEach(batteryBank => {
        const highestBattery = maxInArray(batteryBank.slice(0, batteryBank.length - 1));
        const indicesOfHighestBattery = getAllIndices(batteryBank, highestBattery);

        let max = 0;
        indicesOfHighestBattery.forEach(batteryIdx => {
            const candidate = maxInArray(batteryBank.slice(batteryIdx + 1));
            const joltage = Number.parseInt(`${highestBattery}${candidate}`);
            if (joltage > max) {
                max = joltage;
            }
        });

        sum += max;
    });
    return sum;
}