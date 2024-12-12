import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord, getNeighborCoords, getNeighbors, Grid, readLinesToGrid} from "@utils/grid";
import * as module from "node:module";

export async function part1(inputFile: string) {
    return await day12(inputFile, calcAreaAndPerimeter);
}

export async function part2(inputFile: string) {
    return await day12(inputFile);
}

async function day12(inputFile: string, calcFn?: (grid: Grid) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(readLinesToGrid(lines));
}

function calcAreaAndPerimeter(grid: Grid) {
    let sum = 0;
    const perimeterMap: Map<string, number> = new Map();
    const areaMap: Map<string, number> = new Map();

    for (const [coord, cell] of grid) {
        const neighbors = getNeighbors(Coord.deserialize(coord), grid);
        const perimeter = 4 - neighbors.filter(n => n === cell).length;
        perimeterMap.set(cell, (perimeterMap.get(cell) || 0) + perimeter);
        areaMap.set(cell, (areaMap.get(cell) || 0) + 1);
    }

    for (let [key, value] of perimeterMap) {
        sum += value * areaMap.get(key)!;
    }

    return sum;
}