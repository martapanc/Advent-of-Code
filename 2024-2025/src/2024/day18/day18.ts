import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord, getNeighborCoords, getNeighbors, Grid, printGrid} from "@utils/grid";
import {PriorityQueue} from "@utils/queue";

export async function part1(inputFile: string, side: number = 71, firstCount: number = 1024) {
    return await day18(inputFile, side, findPath, firstCount);
}

export async function part2(inputFile: string, side: number = 71, firstCount: number = 1024) {
    return await day18(inputFile, side, findFirstBlockingByte, firstCount);
}

async function day18(inputFile: string, side: number, calcFn: (lines: string[], end: Coord, side: number, firstCount: number) => number | string, firstCount: number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn(lines, new Coord(side - 1, side - 1), side, firstCount);
}

type State = {
    position: string,
    length: number,
    path?: Coord[]
}

function findPath(lines: string[], end: Coord, side: number, firstCount: number) {
    const grid: Grid = new Map<string, string>;
    for (let y = 0; y < side; y++) {
        for (let x = 0; x < side; x++) {
            const coord = `${x},${y}`;
            if (lines.slice(0, firstCount).some(l => l === coord)) {
                grid.set(`{${coord}}`, '#');
            } else {
                grid.set(`{${coord}}`, '.');
            }
        }
    }

    let currentPos = new Coord(0, 0);

    const queue = new PriorityQueue<State>((a, b) => a.length - b.length);
    queue.enqueue({ position: currentPos.serialize(), length: 0 });
    let visited = new Set<string>();

    while (queue.size() > 0) {
        const { position, length } = queue.dequeue()!;
        const curr = Coord.deserialize(position);

        if (curr.equals(end)) {
            return length;
        }

        if (visited.has(position)) continue;
        visited.add(position);

        for (let n of getNeighborCoords(curr)) {
            const neighbor = n.serialize();
            if (grid.get(neighbor) === '.') {
                queue.enqueue({ position: neighbor, length: length + 1 });
            }
        }
    }

    return -1;
}

function findFirstBlockingByte(lines: string[], end: Coord, side: number, firstCount: number) {
    let count = firstCount + 1;
    let grid: Grid = new Map<string, string>;

    while (count < lines.length) {
        for (let y = 0; y < side; y++) {
            for (let x = 0; x < side; x++) {
                const coord = `${x},${y}`;
                if (lines.slice(0, count).some(l => l === coord)) {
                    grid.set(`{${coord}}`, '#');
                } else {
                    grid.set(`{${coord}}`, '.');
                }
            }
        }

        const path = findPath(lines, end, side, count);
        if (path === -1) {
            return lines[count - 1];
        }
        count++;
    }

    return "-1";
}