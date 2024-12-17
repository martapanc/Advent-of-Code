import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, Direction, Grid, move, readLinesToGrid, rotate} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day16(inputFile, findBestPath);
}

export async function part2(inputFile: string) {
    return await day16(inputFile);
}

async function day16(inputFile: string, calcFn?: (grid: Grid, initialPos: Coord, endPos: Coord) => number | undefined) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const { grid, initialPos, endPos } = readLinesToGrid(lines, 'S', 'E');

    return calcFn?.(grid, initialPos!, endPos! );
}

type State = { pos: Coord, dir: Cardinal, cost: number };

function findBestPath(grid: Grid, start: Coord, end: Coord) {
    const visited = new Set<string>();
    const queue: State[] = [];

    queue.push({ pos: start, dir: Cardinal.EAST, cost: 0 });

    while (queue.length > 0) {
        queue.sort((a, b) => a.cost - b.cost);
        const { pos, dir, cost } = queue.shift()!;

        if (pos.equals(end)) {
            return cost;
        }

        const stateKey = `${pos.serialize()},${dir}`;
        if (visited.has(stateKey)) continue;
        visited.add(stateKey);


        const nextPos = move(pos, dir);
        if (grid.has(nextPos.serialize()) && grid.get(nextPos.serialize()) !== '#') {
            queue.push({ pos: nextPos, dir, cost: cost + 1 });
        }

        const clockwiseDir = rotate(dir, Direction.RIGHT);
        queue.push({ pos, dir: clockwiseDir, cost: cost + 1000 });

        const counterClockwiseDir = rotate(dir, Direction.LEFT);
        queue.push({ pos, dir: counterClockwiseDir, cost: cost + 1000 });
    }

    return -1;
}