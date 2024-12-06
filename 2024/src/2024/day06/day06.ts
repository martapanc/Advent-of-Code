import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, Direction, Grid, move, rotate} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day6(inputFile, calcDistinctPositions);
}

export async function part2(inputFile: string) {
    return await day6(inputFile);
}

async function day6(inputFile: string, calcFn?: (grid: Grid, init: Coord) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    let initialPos = new Coord(0, 0);
    const grid: Grid = new Map();
    for (let y = 0; y < lines.length; y++) {
        const line = lines[y].split('');
        for (let x = 0; x < line.length; x++) {
            if (line[x] === '^') {
                initialPos = new Coord(x, y);
                grid.set(new Coord(x, y).serialize(), '.');
            } else {
                grid.set(new Coord(x, y).serialize(), line[x]);
            }
        }
    }

    return calcFn?.(grid, initialPos);
}

function calcDistinctPositions(grid: Grid, initialPos: Coord) {
    let distinctPositions = new Set<string>();

    let currentPos = new Coord(initialPos.x, initialPos.y);
    distinctPositions.add(currentPos.serialize());

    let direction = Cardinal.NORTH;
    let newPosCoord = move(currentPos, direction);

    while (grid.get(newPosCoord.serialize())) {
        if (grid.get(newPosCoord.serialize()) === '#') {
            direction = rotate(direction, Direction.RIGHT);
        } else if (grid.get(newPosCoord.serialize()) === '.') {
            distinctPositions.add(newPosCoord.serialize());
            currentPos = newPosCoord;
        }
        newPosCoord = move(currentPos, direction);
    }

    return distinctPositions.size;
}