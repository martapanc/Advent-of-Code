import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string): Promise<number> {
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

    let distanceChecksum = 0;
    leftList.forEach((num, index) => {
        distanceChecksum += Math.abs(num - rightList[index]);
    });

    return distanceChecksum;
}

export async function part2(inputFile: string): Promise<number> {
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

    let totalSimilarityScore = 0;
    leftList.forEach((num, index) => {
        const occurrences = rightList.filter(rightNum => rightNum === num).length;
        totalSimilarityScore += num * occurrences;
    });

    return totalSimilarityScore;
}