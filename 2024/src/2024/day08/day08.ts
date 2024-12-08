import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord, Grid, readLinesToGrid} from "@utils/grid";
import {generatePairs} from "@utils/numbers";

export async function part1(inputFile: string) {
    return await day8(inputFile);
}

export async function part2(inputFile: string) {
    return await day8(inputFile, true);
}

async function day8(inputFile: string, isPart2 = false) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    return findUniqueAntinodes(readLinesToGrid(lines), isPart2 ? lines.length : undefined);
}

function findUniqueAntinodes(grid: Map<string, string>, max?: number) {
    let uniqueAntinodes = new Set<string>();

    const antennasMap = new Map<string, string[]>;
    for (let [coord, cell] of grid) {
        if (cell !== '.') {
            if (antennasMap.has(cell)) {
                antennasMap.get(cell)?.push(coord);
            } else {
                antennasMap.set(cell, [coord]);
            }

            if (max) { // Part 2: add antennas as antinodes
                uniqueAntinodes.add(coord);
            }
        }
    }

    for (let coords of antennasMap.values()) {
        for (let pair of generatePairs(coords)) {
            const antinodes = findAntinodes(Coord.deserialize(pair[0]), Coord.deserialize(pair[1]), max);
            for (let antinode of antinodes) {
                if (grid.has(antinode.serialize())) {
                    uniqueAntinodes.add(antinode.serialize());
                }
            }
        }
    }

    return uniqueAntinodes.size;
}

export function findAntinodes(antennaA: Coord, antennaB: Coord, max?: number) {
    const xDelta = antennaA.x - antennaB.x;
    const yDelta = antennaA.y - antennaB.y;

    let antinodes: Coord[] = [];

    let sourceA = antennaA;
    let sourceB = antennaB;

    let antinodeA = new Coord(sourceA.x + xDelta, sourceA.y + yDelta);
    let antinodeB = new Coord(sourceB.x - xDelta, sourceB.y - yDelta);

    let antinodeAWithinGrid = true;
    let antinodeBWithinGrid = true;

    if (max) {
        while (antinodeAWithinGrid || antinodeBWithinGrid) {
            antinodes.push(antinodeA);
            antinodes.push(antinodeB);

            sourceA = new Coord(antinodeA.x, antinodeA.y);
            sourceB = new Coord(antinodeB.x, antinodeB.y);

            antinodeA = new Coord(sourceA.x + xDelta, sourceA.y + yDelta);
            antinodeB = new Coord(sourceB.x - xDelta, sourceB.y - yDelta);

            if (!isWithinRange(antinodeA, max)) {
                antinodeAWithinGrid = false;
            }
            if (!isWithinRange(antinodeB, max)) {
                antinodeBWithinGrid = false;
            }
        }
    } else {
        antinodes.push(antinodeA);
        antinodes.push(antinodeB);
    }

    return antinodes;
}

function isWithinRange(coord: Coord, max: number) {
    const { x, y } = coord;

    const min = 0;
    return x >= min && x < max && y >= min && y < max;
}