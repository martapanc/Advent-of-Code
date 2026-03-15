import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string): Promise<number> {
    return await day1(inputFile, calcDistanceChecksum);
}

export async function part2(inputFile: string): Promise<number> {
    return await day1(inputFile, calcSimilarityScore);
}

async function day1(inputFile: string, calcFn: (list1: number[], list2: number[]) => number): Promise<number> {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const leftList: number[] = [];
    const rightList: number[] = [];

    lines.forEach(line => {
        const numbers = line.split(/\s+/).map(str => Number.parseInt(str));
        leftList.push(numbers[0]);
        rightList.push(numbers[1]);
    });

    leftList.sort();
    rightList.sort();

    return calcFn(leftList, rightList);
}

function calcSimilarityScore(leftList: number[], rightList: number[]): number {
    let totalSimilarityScore = 0;
    leftList.forEach((num) => {
        const occurrences = rightList.filter(rightNum => rightNum === num).length;
        totalSimilarityScore += num * occurrences;
    });

    return totalSimilarityScore;
}

function calcDistanceChecksum(leftList: number[], rightList: number[]): number {
    let distanceChecksum = 0;
    leftList.forEach((num, index) => {
        distanceChecksum += Math.abs(num - rightList[index]);
    });

    return distanceChecksum;
}
