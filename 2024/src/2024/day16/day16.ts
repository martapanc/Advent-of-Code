import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, Direction, Grid, move, readLinesToGrid, rotate} from "@utils/grid";
import {PriorityQueue} from "@utils/queue";

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
        return traverse(grid, initialPos!, endPos!).seats;
    }
}

type State = { pos: Coord, dir: Cardinal, cost: number };

function findBestPath(grid: Grid, start: Coord, end: Coord, isPart2: boolean = false) {
    const visited = new Map<string, number>();
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

type State2 = {
    position: Coord,
    direction: Cardinal,
    cost: number,
    path: Coord[]
}

type TileState = {
    pos: Coord,
    dir: Cardinal,
}

function traverse(grid: Grid, start: Coord, end: Coord) {
    const paths = new Set<string>();
    let lowest = Infinity;
    const scores = new Map<string, number>(); // Key: serialized (position + direction)
    const toVisit = new PriorityQueue<State2>((a, b) => a.cost - b.cost);

    toVisit.enqueue({ position: start, direction: Cardinal.EAST, cost: 0, path: [] });

    while (toVisit.size() > 0) {
        const { position, direction, cost, path } = toVisit.dequeue()!;

        const stateKey = `${position.serialize()}:${direction}`;
        if (cost > (scores.get(stateKey) ?? Infinity)) continue;

        scores.set(stateKey, cost);

        if (position.equals(end)) {
            if (cost > lowest) break;

            path.forEach(coord => paths.add(coord.serialize()));
            lowest = cost;
        }

        const moves: { dir: Cardinal; moveCost: number }[] = [
            { dir: direction, moveCost: 1 },
            { dir: rotate(direction, Direction.RIGHT), moveCost: 1001 },
            { dir: rotate(direction, Direction.LEFT), moveCost: 1001 },
        ];

        for (const { dir, moveCost } of moves) {
            const nextPosition = move(position, dir);
            if (grid.get(nextPosition.serialize()) !== "#") {
                toVisit.enqueue({
                    position: nextPosition,
                    direction: dir,
                    cost: moveCost + cost,
                    path: [...path, position],
                });
            }
        }
    }

    return { lowestScore: lowest, seats: paths.size + 1 };
}
