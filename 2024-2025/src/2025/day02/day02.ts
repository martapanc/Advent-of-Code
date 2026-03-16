import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day2(inputFile, findInvalidIdsTwiceRule);
}

export async function part2(inputFile: string) {
    return await day2(inputFile, findInvalidIdsNTimesRule);
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

function findInvalidIdsTwiceRule(ranges: NumRange[]): number {
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

export const divisors = (num: number) => [...Array(num)]
    .map((_,i) => i)
    .filter(x => num % x === 0).slice(1);

export function chunkString(str: string, size: number): string[] {
    if (size <= 0) {
        return [str];
    }
    const regex = new RegExp(`.{1,${size}}`, 'g');
    return str.match(regex) || [];
}

export const chunkIntoNParts = (str: string, parts: number): string[] => chunkString(str, Math.floor(str.length / parts));

function findInvalidIdsNTimesRule(ranges: NumRange[]): number {
    let invalidIdSum = 0;
    ranges.forEach(({ from, to }) => {
        for (let n = from; n <= to; n++) {
            const s = `${n}`;
            const len = s.length;

            // Only divisors of len produce equal-length chunks; skip k=1
            for (let k = 2; k <= len; k++) {
                if (len % k !== 0) continue;
                const chunkSize = len / k;
                const first = s.slice(0, chunkSize);
                let equal = true;
                for (let i = 1; i < k; i++) {
                    if (s.slice(i * chunkSize, (i + 1) * chunkSize) !== first) { equal = false; break; }
                }
                if (equal) { invalidIdSum += n; break; }
            }
        }
    });

    return invalidIdSum;
}

