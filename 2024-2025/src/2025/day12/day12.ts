import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day12(inputFile, countFittableRegions);
}

async function day12(inputFile: string, calcFn?: (lines: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(lines);
}

type ShapeInfo = {
    area: number;
    imbalance: number;
};

type Region = {
    width: number;
    height: number;
    counts: number[];
};

function countFittableRegions(lines: string[]): number {
    const {shapes, regions} = parseInput(lines);
    const shapeCount = shapes.length;
    let total = 0;

    for (const region of regions) {
        if (region.counts.length !== shapeCount) {
            continue;
        }
        const areaNeeded = region.counts.reduce((sum, count, idx) => sum + count * shapes[idx].area, 0);
        const areaAvailable = region.width * region.height;
        if (areaNeeded > areaAvailable) continue;

        if (areaNeeded === areaAvailable) {
            const boardImbalance = (areaAvailable % 2 === 0) ? 0 : 1;
            if (!canMatchImbalance(region.counts, shapes, boardImbalance)) continue;
        }

        total++;
    }

    return total;
}

function parseInput(lines: string[]) {
    const shapes: ShapeInfo[] = [];
    const regions: Region[] = [];

    const shapeHeader = /^(\d+):$/;
    const regionHeader = /^(\d+)x(\d+):/;

    let i = 0;
    while (i < lines.length) {
        const line = lines[i].trim();
        if (shapeHeader.test(line)) {
            i++;
            const grid: string[] = [];
            while (i < lines.length) {
                const next = lines[i].trim();
                if (shapeHeader.test(next) || regionHeader.test(next)) break;
                grid.push(next);
                i++;
            }
            shapes.push(analyzeShape(grid));
            continue;
        }
        if (regionHeader.test(line)) {
            const [dimPart, countsPart] = line.split(":");
            const [width, height] = dimPart.trim().split("x").map(value => Number.parseInt(value, 10));
            const counts = countsPart.trim().split(/\s+/).map(value => Number.parseInt(value, 10));
            regions.push({width, height, counts});
        }
        i++;
    }

    return {shapes, regions};
}

function analyzeShape(grid: string[]): ShapeInfo {
    let area = 0;
    let black = 0;
    let white = 0;
    for (let y = 0; y < grid.length; y++) {
        for (let x = 0; x < grid[y].length; x++) {
            if (grid[y][x] !== "#") continue;
            area++;
            if ((x + y) % 2 === 0) black++;
            else white++;
        }
    }
    return {area, imbalance: Math.abs(black - white)};
}

function canMatchImbalance(counts: number[], shapes: ShapeInfo[], boardImbalance: number): boolean {
    let maxSum = 0;
    for (let i = 0; i < counts.length; i++) {
        maxSum += counts[i] * shapes[i].imbalance;
    }
    if (maxSum === 0) {
        return boardImbalance === 0;
    }
    const offset = maxSum;
    let possible = new Array<boolean>(2 * maxSum + 1).fill(false);
    possible[offset] = true;

    for (let i = 0; i < counts.length; i++) {
        const count = counts[i];
        const d = shapes[i].imbalance;
        if (count === 0 || d === 0) continue;
        const next = new Array<boolean>(2 * maxSum + 1).fill(false);
        for (let sum = -count * d; sum <= count * d; sum += 2 * d) {
            const shifted = sum + offset;
            for (let idx = 0; idx < possible.length; idx++) {
                if (!possible[idx]) continue;
                const target = idx + sum;
                if (target >= 0 && target < next.length) {
                    next[target] = true;
                }
            }
        }
        possible = next;
    }

    return possible[offset + boardImbalance] ?? false;
}
