import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord, getDiagonalNeighbors, getNeighbors, Grid} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day4(inputFile, solve_XMAS_Crosswords);
}

export async function part2(inputFile: string) {
    return await day4(inputFile, solve_X_MAS_Crosswords);
}

async function day4(inputFile: string, calcFn?: (grid: Grid) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const grid: Grid = new Map;
    for (let y = 0; y < lines.length; y++) {
        const line = lines[y].split('');
        for (let x = 0; x < line.length; x++) {
            grid.set(new Coord(x, y).serialize(), line[x]);
        }
    }
    return calcFn?.(grid);
}

function solve_XMAS_Crosswords(grid: Grid): number {
    let xmasCount = 0;

    grid.forEach((value, cell) => {
        if (value === 'X') {
            const neighbors = getNeighbors(Coord.deserialize(cell), grid, true);
            if (neighbors.some(n => n === 'M')) {
                const all = getAllCandidates(Coord.deserialize(cell), grid);

                const localXmasCount: number = all.filter(s => s === 'XMAS').length;
                xmasCount += localXmasCount;
            }
        }
    });

    return xmasCount;
}

function getAllCandidates(cell: Coord, grid: Grid) {
    const candidateDeltas = [
        ['{0,0}', '{1,0}', '{2,0}', '{3,0}'],
        ['{0,0}', '{-1,0}', '{-2,0}', '{-3,0}'],
        ['{0,0}', '{0,1}', '{0,2}', '{0,3}'],
        ['{0,0}', '{0,-1}', '{0,-2}', '{0,-3}'],
        ['{0,0}', '{1,1}', '{2,2}', '{3,3}'],
        ['{0,0}', '{-1,-1}', '{-2,-2}', '{-3,-3}'],
        ['{0,0}', '{1,-1}', '{2,-2}', '{3,-3}'],
        ['{0,0}', '{-1,1}', '{-2,2}', '{-3,3}'],
    ];

    const candidates: string[] = [];
    candidateDeltas.forEach(deltas => {
        const coords: Coord[] = deltas.map(delta => {
            const deltaCoord = Coord.deserialize(delta);
            return new Coord(cell.x + deltaCoord.x, cell.y + deltaCoord.y);
        })

        const values = coords
            .filter(c => grid.has(c.serialize()))
            .map(c => grid.get(c.serialize()));

        if (values.length === 4) {
            candidates.push(values.join(''));
        }
    });

    return candidates;
}

function solve_X_MAS_Crosswords(grid: Grid): number {
    let xmasCount = 0;

    grid.forEach((value, cell) => {
        if (value === 'A') {
            const neighbors = getDiagonalNeighbors(Coord.deserialize(cell), grid);
            const str = neighbors.join('');
            if (str === "MMSS" || str === "SSMM" || str === 'SMMS' || str === 'MSSM') {
                xmasCount++;
            }
        }
    });

    return xmasCount;
}