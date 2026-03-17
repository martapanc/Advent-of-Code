import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, Grid, move, readLinesToGrid} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day7(inputFile, findBeanSplits);
}

export async function part2(inputFile: string) {
    return await day7(inputFile, findBeanSplitTimelines);
}

async function day7(inputFile: string, calcFn?: (grid: Grid, initialPos: Coord) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    const { grid, initialPos } = readLinesToGrid(lines, 'S');
    return calcFn?.(grid, initialPos!);
}

function findBeanSplits(grid: Grid, initialPos: Coord): number {
    const visited = new Set<string>();
    const queue: Coord[] = [move(initialPos, Cardinal.SOUTH)];
    let splits = 0;

    while (queue.length > 0) {
        let current = queue.shift()!;

        while (true) {
            const key = current.serialize();
            const cellValue = grid.get(key);

            if (cellValue === undefined || visited.has(key)) break;

            visited.add(key);

            if (cellValue === '^') {
                splits++;
                const left = move(current, Cardinal.WEST);
                const right = move(current, Cardinal.EAST);
                if (grid.has(left.serialize()))
                    queue.push(left);
                if (grid.has(right.serialize()))
                    queue.push(right);
                break;
            }

            current = move(current, Cardinal.SOUTH);
        }
    }

    return splits;
}

function findBeanSplitTimelines(grid: Grid, initialPos: Coord): number {
    const memo = new Map<string, number>();

    function timelines(coord: Coord): number {
        const key = coord.serialize();
        if (memo.has(key)) return memo.get(key)!;

        const cellValue = grid.get(key);
        if (cellValue === undefined) return 1;

        const result = cellValue === '^'
            ? timelines(move(coord, Cardinal.WEST)) + timelines(move(coord, Cardinal.EAST))
            : timelines(move(coord, Cardinal.SOUTH));

        memo.set(key, result);
        return result;
    }

    return timelines(move(initialPos, Cardinal.SOUTH));
}
