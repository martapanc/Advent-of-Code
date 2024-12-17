import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, Direction, Grid, move, readLinesToGrid, rotate} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day16(inputFile);
}

export async function part2(inputFile: string) {
    return await day16(inputFile, true);
}

async function day16(inputFile: string, isPart2 = false) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const {grid, initialPos, endPos} = readLinesToGrid(lines, 'S', 'E');

    if (!isPart2) {
        return findBestPath(grid, initialPos!, endPos!);
    } else {
        return countCellsOnBestPaths(grid, initialPos!, endPos!);
    }
}

type State = { pos: Coord, dir: Cardinal, cost: number };

function findBestPath(grid: Grid, start: Coord, end: Coord, isPart2: boolean = false) {
    const visited = new Map<string, number>(); // Tracks minimum cost for each state
    const queue: State[] = [];

    queue.push({ pos: start, dir: Cardinal.EAST, cost: 0 });

    while (queue.length > 0) {
        queue.sort((a, b) => a.cost - b.cost);
        const { pos, dir, cost } = queue.shift()!;

        const state = `${pos.serialize()},${dir}`;
        if (visited.has(state) && visited.get(state)! <= cost) {
            continue;
        }

        visited.set(state, cost);

        if (pos.equals(end)) {
            if (!isPart2)
                return cost;
            continue;
        }

        const nextPos = move(pos, dir);
        if (grid.has(nextPos.serialize()) && grid.get(nextPos.serialize()) !== '#') {
            queue.push({ pos: nextPos, dir, cost: cost + 1 });
        }

        const clockwiseDir = rotate(dir, Direction.RIGHT);
        queue.push({ pos, dir: clockwiseDir, cost: cost + 1000 });

        const counterClockwiseDir = rotate(dir, Direction.LEFT);
        queue.push({ pos, dir: counterClockwiseDir, cost: cost + 1000 });
    }

    return { cost: 0, pathTiles: new Set() };
}


function countCellsOnBestPaths(grid: Grid, start: Coord, end: Coord) {
    const { cost, pathTiles } = findBestPath(grid, start, end, true) as { cost: number, pathTiles: Set<string>};

    return pathTiles.size;
}