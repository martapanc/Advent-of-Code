import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day2(inputFile, countSafeLevels);
}

export async function part2(inputFile: string) {
    return await day2(inputFile);
}

async function day2(inputFile: string, calcFn?: (lines: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(lines);
}

function countSafeLevels(lines: string[]): number {
    let safeLevelCount = 0;

    lines.forEach(line => {
        const numbers = line.split(" ").map(str => Number.parseInt(str));
        let isSafe = true;
        const deltas = getLevelDeltas(numbers);
        if (deltas.every(delta => delta >= 0) || deltas.every(delta => delta <= 0)) {
            const absDelta = deltas.map(delta => Math.abs(delta));

            if (absDelta.some(delta => delta < 1 || delta > 3)) {
                isSafe = false;
            }
        } else {
            isSafe = false;
        }

        if (isSafe) {
            safeLevelCount++;
        }
    })

    return safeLevelCount;
}

// Calc the differences between consecutive numbers (e.g. 7 6 4 2 1 => 1, 2, 2, 1)
export function getLevelDeltas(level: number[]) {
    const delta = [];
    for (let i = 0; i < level.length - 1; i++) {
        delta[i] = level[i] - level[i + 1];
    }

    return delta;
}