import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord, getNeighbors, Grid, readLinesToGrid} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day4(inputFile, findAccessiblePaperRolls);
}

export async function part2(inputFile: string) {
    return await day4(inputFile);
}

async function day4(inputFile: string, calcFn?: (grid: Grid) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    const { grid } = readLinesToGrid(lines);
    return calcFn?.(grid);
}

function findAccessiblePaperRolls(grid: Grid) {
    let paperRolls = 0;

    grid.forEach((value, coord) => {
        if (value === '@') {
            const adjacentCells = getNeighbors(Coord.deserialize(coord), grid, true);
            const rolls = adjacentCells.filter(cell => cell === '@').length;
            if (rolls < 4)
                paperRolls += 1;
        }
    });

    return paperRolls;
}