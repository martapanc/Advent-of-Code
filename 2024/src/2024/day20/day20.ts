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
    return await day20(inputFile, minCheats, countCheatsSavingAtLeast);
}

export async function part2(inputFile: string, minCheats: number) {
    return await day20(inputFile, minCheats);
}

async function day20(inputFile: string, minCheats: number, calcFn?: (grid: Grid, start: Coord, end: Coord, minCheats: number) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const { grid, initialPos, endPos } = readLinesToGrid(lines, 'S', 'E');

    return calcFn?.(grid, initialPos!, endPos!, minCheats);
}

function countCheatsSavingAtLeast(grid: Grid, start: Coord, end: Coord, minCheats: number) {
    const length = calcRacetrackLength(grid, start, end);
    const cheatMap = new Map<string, number>();

    findCandidateCheats(grid).forEach(cheat => {
        const newGrid = new Map(grid);
        newGrid.set(cheat.serialize(), '.');
        const newLength = calcRacetrackLength(newGrid, start, end, length - minCheats);
        if (newLength != -1) {
            cheatMap.set(cheat.serialize(), length - newLength);
        }
    });

    let count = 0;
    for (const value of cheatMap.values()) {
        if (value >= minCheats) {
            count++;
        }
    }

    return count;
}

type State = {
    curr: Coord,
    length: number
}

function calcRacetrackLength(grid: Grid, start: Coord, end: Coord, maxLength?: number) {
    let curr = new Coord(start.x, start.y);
    const visited = new Set<string>();

    const queue: State[] = [{ curr, length: 0}];

    while (queue.length > 0) {
        const { curr, length } = queue.shift()!;

        if (maxLength && length > maxLength) {
            return -1
        }

        if (curr.equals(end)) {
            return length;
        }

        if (visited.has(curr.serialize()))
            continue;

        visited.add(curr.serialize());

        const neighbors = getNeighborCoords(curr).filter(c => grid.get(c.serialize()) === '.' && !visited.has(c.serialize()))!;
        neighbors.forEach(n => {
            queue.push({ curr: n, length: length + 1 });
        });
    }

    return -1;
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