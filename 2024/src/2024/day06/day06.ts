import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, Direction, Grid, move, rotate} from "@utils/grid";

export async function part1(inputFile: string) {
    const { distinctPositions } = await day6(inputFile, calcDistinctPositions);
    return distinctPositions.size;
}

export async function part2(inputFile: string) {
    return await day6(inputFile, findLoops);
}

async function day6(inputFile: string, calcFn?: (grid: Grid, init: Coord) => any) {
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
    let distinctPositions = new Map<string, Cardinal>();

    let currentPos = new Coord(initialPos.x, initialPos.y);
    let direction = Cardinal.NORTH;
    distinctPositions.set(currentPos.serialize(), direction);

    let newPosCoord = move(currentPos, direction);

    let foundLoop = false;

    while (grid.get(newPosCoord.serialize()) && !foundLoop) {
        if (distinctPositions.has(newPosCoord.serialize()) && distinctPositions.get(newPosCoord.serialize()) === direction) {
            foundLoop = true;
            break;
        }
        if (grid.get(newPosCoord.serialize()) === '#') {
            direction = rotate(direction, Direction.RIGHT);
        } else if (grid.get(newPosCoord.serialize()) === '.') {
            distinctPositions.set(newPosCoord.serialize(), direction);
            currentPos = newPosCoord;
        }
        newPosCoord = move(currentPos, direction);
    }

    return { distinctPositions, foundLoop };
}

function findLoops(grid: Grid, initialPos: Coord) {
    let loopCount = 0;

    const { distinctPositions } = calcDistinctPositions(grid, initialPos);
    distinctPositions.delete(initialPos.serialize());

    Array.from(distinctPositions.keys()).forEach(coord => {
         const newGrid = new Map(grid);
         newGrid.set(coord, "#");

         const { foundLoop } = calcDistinctPositions(newGrid, initialPos);

         if (foundLoop) {
             loopCount++;
         }
    });

    return loopCount;
}