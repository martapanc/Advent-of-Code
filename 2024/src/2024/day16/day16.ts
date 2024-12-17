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
        return countTilesOnBestPaths(grid, initialPos!, endPos!);
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


function findAllPaths(grid: Grid, start: Coord, end: Coord) {
    const visited = new Map<string, Set<Cardinal>>();  // Track visited positions with incoming directions
    const allPaths: Array<{ path: Coord[], cost: number }> = [];
    const queue: { pos: Coord, dir: Cardinal, path: Coord[], cost: number }[] = [];

    // Start BFS to compute all possible paths
    queue.push({ pos: start, dir: Cardinal.EAST, path: [start], cost: 0 });

    while (queue.length > 0) {
        const { pos, dir, path, cost } = queue.shift()!;

        // Check if we've already visited this position with the same incoming direction
        const stateKey = `${pos.serialize()},${dir}`;
        if (visited.has(stateKey)) {
            continue;  // Skip this state if we've already explored it with the same direction
        }

        // Mark this position and direction as visited
        visited.set(stateKey, new Set([dir]));

        // If we reach the end, record the path and cost
        if (pos.equals(end)) {
            allPaths.push({ path, cost });
            continue;
        }

        // Move forward in the current direction
        const nextPos = move(pos, dir);
        if (grid.has(nextPos.serialize()) && grid.get(nextPos.serialize()) !== '#') {
            const nextStateKey = `${nextPos.serialize()},${dir}`;
            if (!visited.has(nextStateKey)) {
                queue.push({ pos: nextPos, dir, path: [...path, nextPos], cost: cost + 1 });
            }
        }

        // Explore rotations: left and right
        for (const rotation of [Direction.LEFT, Direction.RIGHT]) {
            const newDir = rotate(dir, rotation);
            const rotateStateKey = `${pos.serialize()},${newDir}`;
            if (!visited.has(rotateStateKey)) {
                queue.push({ pos, dir: newDir, path: [...path], cost: cost + 1000 });
            }
        }
    }

    return allPaths;
}

function countTilesOnBestPaths(grid: Grid, start: Coord, end: Coord) {
    const allPaths = findAllPaths(grid, start, end);

    // Step 1: Get the minimum cost from all paths
    const minCost = Math.min(...allPaths.map(p => p.cost));

    // Step 2: Collect all tiles in the best paths (paths with minCost)
    const bestPathTiles = new Set<string>();
    allPaths.forEach(({ path, cost }) => {
        if (cost === minCost) {
            path.forEach(pos => bestPathTiles.add(pos.serialize()));
        }
    });

    // Step 3: Return the count of unique tiles in the best paths
    return bestPathTiles.size;
}
