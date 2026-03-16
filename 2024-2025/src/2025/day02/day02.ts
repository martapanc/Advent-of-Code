import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day2(inputFile, findInvalidIds);
}

export async function part2(inputFile: string) {
    return await day2(inputFile);
}

type NumRange = { from: number; to: number };

async function day2(inputFile: string, calcFn?: (ranges: NumRange[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const rangeStrings = lines[0].split(',');
    const ranges: NumRange[] = rangeStrings.map(range => {
        const res = range.split('-');
        return {
            from: Number.parseInt(res[0]),
            to: Number.parseInt(res[1]),
        }
    })
    return calcFn?.(ranges);
}

function findInvalidIds(ranges: NumRange[]): number {
    let invalidIdSum = 0;
    ranges.forEach(({ from, to }) => {

        for (let n = from; n <= to; n ++) {
            const numAsString = `${n}`;
            if (numAsString.length % 2 !== 0)
                continue;

            const mid = numAsString.length / 2;

            const firstHalf = numAsString.slice(0, mid);
            const secondHalf = numAsString.slice(mid);
            if (firstHalf === secondHalf)
                invalidIdSum += n;
        }
    })

    return invalidIdSum;
}