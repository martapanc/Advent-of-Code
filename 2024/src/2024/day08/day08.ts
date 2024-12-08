import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord, Grid, readLinesToGrid} from "@utils/grid";
import {generatePairs} from "@utils/numbers";

export async function part1(inputFile: string) {
    return await day8(inputFile, findUniqueAntinodes);
}

export async function part2(inputFile: string) {
    return await day8(inputFile);
}

async function day8(inputFile: string, calcFn?: (grid: Map<string, string>) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    return calcFn?.(readLinesToGrid(lines));
}

function findUniqueAntinodes(grid: Map<string, string>) {
    let uniqueAntinodes = new Set<string>();

    const antennasMap = new Map<string, string[]>;
    for (let [coord, cell] of grid) {
        if (cell !== '.') {
            if (antennasMap.has(cell)) {
                antennasMap.get(cell)?.push(coord);
            } else {
                antennasMap.set(cell, [coord]);
            }
        }
    }

    for (let coords of antennasMap.values()) {
        for (let pair of generatePairs(coords)) {
            const antinodes = findAntinodes(Coord.deserialize(pair[0]), Coord.deserialize(pair[1]));
            for (let antinode of antinodes) {
                if (grid.has(antinode.serialize())) {
                    uniqueAntinodes.add(antinode.serialize());
                }
            }
        }
    }

    return uniqueAntinodes.size;
}

export function findAntinodes(antennaA: Coord, antennaB: Coord) {
    const xDelta = antennaA.x - antennaB.x;
    const yDelta = antennaA.y - antennaB.y;

    const antinodeA = new Coord(antennaA.x + xDelta, antennaA.y + yDelta);
    const antinodeB = new Coord(antennaB.x - xDelta, antennaB.y - yDelta);

    return [antinodeA, antinodeB];
}