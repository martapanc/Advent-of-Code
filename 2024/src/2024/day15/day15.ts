import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, Grid, move, readLinesToGrid} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day15(inputFile, moveRobot);
}

export async function part2(inputFile: string) {
    return await day15(inputFile);
}

async function day15(inputFile: string, calcFn?: (grid: Grid, initialPos: Coord, directions: Cardinal[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const sectionBreak = lines.findIndex(line => line === ".");
    const { grid, initialPos } = readLinesToGrid(lines.slice(0, sectionBreak), "@");
    const directions: Cardinal[] = [];

    lines.forEach(line => {
        const items = line.split('');
        items.forEach(item => {
            switch (item) {
                case "^":
                    directions.push(Cardinal.NORTH);
                    break;
                case ">":
                    directions.push(Cardinal.EAST);
                    break;
                case "v":
                    directions.push(Cardinal.SOUTH);
                    break;
                case "<":
                    directions.push(Cardinal.WEST);
            }
        });
    });
    return calcFn?.(grid, initialPos!, directions);
}

function moveRobot(inputGrid: Grid, initialPos: Coord, directions: Cardinal[]) {
    const grid = new Map(inputGrid);
    let sourcePos = new Coord(initialPos.x, initialPos.y);

    for (const dir of directions) {
        const newPos = move(sourcePos, dir);
        if (grid.get(newPos.serialize()) === '.') {
            sourcePos = new Coord(newPos.x, newPos.y);
        } else if (grid.get(newPos.serialize()) === 'O') {
            const res = shiftBox(grid, sourcePos, dir)!;
            if (res.serialize() === newPos.serialize()) {
                sourcePos = new Coord(newPos.x, newPos.y);
            }
        }
    }

    let result = 0;
    for (const [key, value] of grid) {
        if (value === "O") {
            const { x, y } = Coord.deserialize(key);
            result += x + 100 * y;
        }
    }
    return result;
}

export function shiftBox(grid: Grid, box: Coord, dir: Cardinal) {
    const candidatePos = move(box, dir);
    if (grid.get(candidatePos.serialize()) === '#') {
        return box;
    }

    if (grid.get(candidatePos.serialize()) === '.') {
        grid.set(candidatePos.serialize(), 'O');
        grid.set(box.serialize(), '.');
        return candidatePos;
    }

    if (grid.get(candidatePos.serialize()) === 'O') {
        const res: Coord = shiftBox(grid, candidatePos, dir)!;
        if (res.serialize() !== candidatePos.serialize()) {
            if (grid.get(box.serialize()) === 'O') {
                grid.set(candidatePos.serialize(), 'O');
                grid.set(box.serialize(), '.');
            }
            return candidatePos;
        } else {
            return box;
        }
    }
}