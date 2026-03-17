import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord} from "@utils/grid";
import {Coord3d} from "@utils/grid3d";

export async function part1(inputFile: string) {
    return await day9(inputFile, findAreaOfLargestRectangle);
}

export async function part2(inputFile: string) {
    return await day9(inputFile);
}

async function day9(inputFile: string, calcFn?: (coords: Coord[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    const coords = lines.map(line => {
        const split = line.split(",").map(Number);
        return new Coord(split[0], split[1]);
    });

    return calcFn?.(coords);
}

function findAreaOfLargestRectangle(coords: Coord[]): number {
    let maxArea = 0;

    coords.forEach((a) => {
        coords.forEach((b) => {
            if (!a.equals(b)) {
                const area = findArea(a, b);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        });
    });

    return maxArea;
}

export function findArea(a: Coord, b: Coord): number {
    const sideA = Math.abs(a.x - b.x) + 1;
    const sideB = Math.abs(a.y - b.y) + 1;
    return sideA * sideB;
}