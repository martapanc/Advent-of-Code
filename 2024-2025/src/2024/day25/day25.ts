import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord, Grid, readLinesToGrid} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day25(inputFile, findFittingLocksAndKeys);
}

async function day25(inputFile: string, calcFn?: (keys: Grid[], locks: Grid[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const keys: Grid[] = [];
    const locks: Grid[] = [];

    for (let i = 0; i < lines.length; i += 7) {
        const currBlock = lines.slice(i, i + 7);
        const { grid } = readLinesToGrid(currBlock);
        if (currBlock[0].startsWith(".")) {
            keys.push(grid);
        } else {
            locks.push(grid);
        }
    }

    return calcFn?.(keys, locks);
}

function findFittingLocksAndKeys(keyGrids: Grid[], lockGrids: Grid[]) {
    let count = 0;
    const keys: number[][] = [];
    const locks: number[][] = [];

    function calcHeightPerPin(key: Map<string, string>) {
        const array: number[] = [];
        for (let x = 0; x < 5; x++) {
            const height = [...key.entries()].filter(([cell, val]) => Coord.deserialize(cell).x === x && val === '#').length - 1;
            array.push(height);
        }
        return array;
    }

    for (const grid of keyGrids) {
        keys.push(calcHeightPerPin(grid));
    }

    for (const grid of lockGrids) {
        locks.push(calcHeightPerPin(grid));
    }

    for (const key of keys) {
        for (const lock of locks) {
            let fit = true;
            for (let i = 0; i < 5; i++) {
                if (key[i] + lock[i] > 5) {
                    fit = false;
                    break;
                }
            }
            if (fit)
                count++;
        }
    }

    return count;
}