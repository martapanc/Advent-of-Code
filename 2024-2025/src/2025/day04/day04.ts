import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord, getNeighbors, Grid, readLinesToGrid} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day4(inputFile, findAccessiblePaperRolls);
}

export async function part2(inputFile: string) {
    return await day4(inputFile, findAndRemoveAccessiblePaperRolls);
}

async function day4(inputFile: string, calcFn?: (grid: Grid) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    const { grid } = readLinesToGrid(lines);
    return calcFn?.(grid);
}

function getRollCoords(grid: Map<string, string>): Coord[] {
    let paperRolls: Coord[] = [];
    grid.forEach((value, coord) => {
        if (value === '@') {
            const adjacentCells = getNeighbors(Coord.deserialize(coord), grid, true);
            const rolls = adjacentCells.filter(cell => cell === '@').length;
            if (rolls < 4)
                paperRolls.push(Coord.deserialize(coord));
        }
    });
    return paperRolls;
}

function findAccessiblePaperRolls(grid: Grid) {
    return getRollCoords(grid).length;
}

function findAndRemoveAccessiblePaperRolls(grid: Grid) {
    let totalAccessiblePaperRolls = 0;

    let paperRollCoords: Coord[] = getRollCoords(grid);
    while (paperRollCoords.length > 0) {
        totalAccessiblePaperRolls += paperRollCoords.length;

        paperRollCoords.forEach((p) => grid.set(p.serialize(), '.'));

        paperRollCoords = getRollCoords(grid);
    }

    return totalAccessiblePaperRolls;
}