import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord, getNeighborCoords, getNeighbors, Grid, printGrid} from "@utils/grid";
import {PriorityQueue} from "@utils/queue";

export async function part1(inputFile: string, side: number = 71, firstCount: number = 1024) {
    return await day18(inputFile, side, firstCount, findPath);
}

export async function part2(inputFile: string, side: number = 71) {
    return await day18(inputFile, side);
}

async function day18(inputFile: string, side: number, firstCount?: number, calcFn?: (grid: Grid, end: Coord) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

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
    return calcFn?.(grid, new Coord(side - 1, side - 1));
}

type State = {
    position: string,
    length: number,
    path?: Coord[]
}

function findPath(grid: Grid, end: Coord) {
    let currentPos = new Coord(0, 0);

    printGrid(grid, end, 71, 71);

    const queue = new PriorityQueue<State>((a, b) => a.length - b.length);
    queue.enqueue({ position: currentPos.serialize(), length: 0 });
    let visited = new Set<string>();

    while (queue.size() > 0) {
        const { position, length } = queue.dequeue()!;
        const curr = Coord.deserialize(position);

        if (curr.equals(end)) {
            return length;
        }

        if (visited.has(position))
            continue;
        visited.add(position);

        for (let n of getNeighborCoords(curr)) {
            const neighbor = n.serialize();
            if (!visited.has(neighbor) && grid.get(neighbor) === '.') {
                queue.enqueue({
                    position: neighbor,
                    length: length + 1,
                });
            }
        }
    }

    return -1;
}