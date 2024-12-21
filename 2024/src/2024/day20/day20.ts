import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {
    Coord,
    getHorizontalNeighbors,
    getNeighborCoords,
    getVerticalNeighbors,
    Grid,
    readLinesToGrid
} from "@utils/grid";

export async function part1(inputFile: string, minCheats: number) {
    return await day20(inputFile, minCheats, false, countCheatsSavingAtLeast);
}

export async function part2(inputFile: string, minCheats: number) {
    return await day20(inputFile, minCheats, true);
}

async function day20(inputFile: string, minCheats: number, isPart2: boolean, calcFn?: (grid: Grid, start: Coord, end: Coord, minCheats: number) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const { grid, initialPos, endPos } = readLinesToGrid(lines, 'S', 'E');

    if (isPart2) {
        const distances = bfs(grid, endPos!);

        let cheats = 0;
        const walkable = Object.keys(distances);
        for (let a of walkable) {
            for (let b of walkable) {
                if (a === b) continue;

                const start = Coord.deserialize(a);
                const end = Coord.deserialize(b);
                const distance = Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
                if (distance <= 20 && distances[a] - distances[b] - distance >= minCheats) {
                    cheats++
                }
            }
        }

        return cheats;
    } else {
        return calcFn?.(grid, initialPos!, endPos!, minCheats);
    }
}

function countCheatsSavingAtLeast(grid: Grid, start: Coord, end: Coord, minCheats: number) {
    const cheatMap = new Map<string, number>();

    findCandidateCheats(grid).forEach(cheat => {
        const newGrid = new Map(grid);
        newGrid.set(cheat.serialize(), '.');

        const diff = calcLengthDiff(newGrid, cheat);
        if (diff && diff >= minCheats) {
            cheatMap.set(cheat.serialize(), diff);
        }
    });

    return [...cheatMap.values()].length;
}


type State = {
    curr: Coord,
    length: number,
    path: string
}

function calcLengthDiff(grid: Grid, cheat: Coord) {
    let start: Coord;
    let end: Coord;
    const hNeighbors = getHorizontalNeighbors(cheat);
    const vNeighbors = getVerticalNeighbors(cheat);
    if (hNeighbors.every(n => grid.get(n.serialize()) === '.')) {
        start = hNeighbors[0];
        end = hNeighbors[1];
    } else {
        start = vNeighbors[0];
        end = vNeighbors[1];
    }

    let oneEndReached = false;
    let firstPathLength = -1;

    const visited = new Set<string>();
    const queue: State[] = [{ curr: start, length: 0, path: start.serialize()}];
    while (queue.length > 0) {
        const { curr, length, path } = queue.shift()!;

        const visitedKey = `${curr.serialize()}`

        if (curr.equals(end)) {
            if (oneEndReached) {
                return Math.abs(firstPathLength - length);
            }
            firstPathLength = length
            oneEndReached = true;
            continue;
        } else {
            if (visited.has(visitedKey))
                continue;

            visited.add(visitedKey);
        }
        const neighbors = getNeighborCoords(curr).filter(c => grid.get(c.serialize()) === '.' && !visited.has(c.serialize()))!;
        neighbors.forEach(n => {
            queue.push({ curr: n, length: length + 1, path: path + n.serialize() });
        });
    }
}

function findCandidateCheats(grid: Grid) {
    const candidates: Coord[] = [];

    grid.forEach((tile, key) => {
        const coord = Coord.deserialize(key);
        if (tile === '#') {
            const vNeighbors = getVerticalNeighbors(coord);
            if (vNeighbors.every(n => grid.get(n.serialize()) === '.')) {
                candidates.push(coord);
            }
            const hNeighbors = getHorizontalNeighbors(coord);
            if (hNeighbors.every(n => grid.get(n.serialize()) === '.')) {
                candidates.push(coord);
            }
        }
    });

    return candidates;
}

function bfs(grid: Grid, start: Coord) {
    const queue: { coord: Coord, steps: number}[] = [];
    const distances: { [key: string]: number } = {};

    queue.push({ coord: start, steps: 0 });
    distances[start.serialize()] = 0;

    while (queue.length > 0) {
        const current = queue.shift();
        if (current === undefined) {
            break;
        }

        for (const neighbor of getNeighborCoords(current.coord)) {
            if (grid.get(neighbor.serialize()) === '.') {
                const newDistance = current.steps + 1;
                if (distances[neighbor.serialize()] === undefined || distances[neighbor.serialize()] > newDistance) {
                    queue.push({ coord: neighbor, steps: current.steps + 1 });
                    distances[neighbor.serialize()] = newDistance;
                }
            }
        }
    }

    return distances;
}