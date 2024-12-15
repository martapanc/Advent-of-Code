import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord, getNeighborCoords, getNeighbors, Grid, readLinesToGrid} from "@utils/grid";
import * as module from "node:module";

export async function part1(inputFile: string) {
    return await day10(inputFile, calcTrailheadScores);
}

export async function part2(inputFile: string) {
    return await day10(inputFile, calcTrailheadRatings);
}

async function day10(inputFile: string, calcFn?: (grid: Grid) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const grid = readLinesToGrid(lines).grid;

    return calcFn?.(grid);
}

function calcTrailheadScores(grid: Grid) {
    let totalScore = 0;

    function bfsTrailhead(startCoord: Coord): number {
        const queue: Coord[] = [startCoord];
        const visited = new Set<string>;
        const reachableNines = new Set<string>;

        visited.add(startCoord.serialize());

        while (queue.length > 0) {
            const current = queue.shift()!;
            const currHeight = Number.parseInt(grid.get(current.serialize())!);

            for (const neighbor of getNeighborCoords(current)) {
                const neighborKey = neighbor.serialize();
                if (!grid.has(neighborKey) || visited.has(neighborKey)) {
                    continue;
                }

                const neighborHeight = Number.parseInt(grid.get(neighborKey)!);
                if (neighborHeight === currHeight + 1) {
                    visited.add(neighborKey);
                    queue.push(neighbor);

                    if (neighborHeight === 9) {
                        reachableNines.add(neighborKey);
                    }
                }
            }
        }

        return reachableNines.size;
    }


    for (const [key, value] of grid) {
        if (value === '0') {
            const startCoord = Coord.deserialize(key);
            totalScore += bfsTrailhead(startCoord);
        }
    }

    return totalScore;
}

function calcTrailheadRatings(grid: Grid) {
    let totalRatings = 0;

    function dfsTrailhead(coord: Coord, currentHeight: number): number {
        if (currentHeight === 9) {
            return 1;
        }

        let paths = 0;
        for (const neighbor of getNeighborCoords(coord)) {
            const neighborKey = neighbor.serialize()!;
            if (!grid.has(neighborKey)) {
                continue;
            }

            const neighborHeight = Number.parseInt(grid.get(neighborKey)!);
            if (neighborHeight === currentHeight + 1) {
                paths += dfsTrailhead(neighbor, neighborHeight);
            }
        }

        return paths;
    }


    for (const [key, value] of grid) {
        if (value === '0') {
            const startCoord = Coord.deserialize(key);
            totalRatings += dfsTrailhead(startCoord, 0);
        }
    }

    return totalRatings;
}