import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, Grid, move, readLinesToGrid} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day15(inputFile, moveRobot);
}

export async function part2(inputFile: string) {
    return await day15(inputFile, moveRobot2, true);
}

async function day15(inputFile: string, calcFn?: (grid: Grid, initialPos: Coord, directions: Cardinal[]) => number, isPart2?: boolean) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const sectionBreak = lines.findIndex(line => line === ".");

    const { grid, initialPos } = isPart2 ?
        readLinesToGridAndExpand(lines.slice(0, sectionBreak), '@') :
        readLinesToGrid(lines.slice(0, sectionBreak), "@");

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

function moveRobot2(inputGrid: Grid, initialPos: Coord, directions: Cardinal[]) {
    const grid = new Map(inputGrid);
    let sourcePos = new Coord(initialPos.x, initialPos.y);

    for (const dir of directions) {
        const newPos = move(sourcePos, dir);
        if (grid.get(newPos.serialize()) === '.') {
            sourcePos = new Coord(newPos.x, newPos.y);
        } else if (grid.get(newPos.serialize()) === '[' || grid.get(newPos.serialize()) === ']') {
            const res = shiftBox2(grid, sourcePos, dir)!;
            if (res.serialize() === newPos.serialize()) {
                sourcePos = new Coord(newPos.x, newPos.y);
            }
        }
    }

    let result = 0;
    for (const [key, value] of grid) {
        if (value === "[") {
            const { x, y } = Coord.deserialize(key);
            result += x + 100 * y;
        }
    }
    return result;
}

function findBoxCoords(pos: Coord, grid: Grid) {
    if (grid.get(pos.serialize()) === '[') {
        return {l: pos, r: new Coord(pos.x + 1, pos.y)};
    } else {
        return {l: new Coord(pos.x - 1, pos.y), r: pos}
    }
}

export function shiftBox2(grid: Grid, box: Coord, dir: Cardinal) {
    const boxCoords = findBoxCoords(box, grid);
    const newLeft = move(boxCoords.l, dir);
    const newRight = move(boxCoords.r, dir);

    // Can wide box be moved?
    if (grid.get(newLeft.serialize()) === '.' && grid.get(newRight.serialize()) === '.') {
        grid.set(boxCoords.l.serialize(), '.');
        grid.set(boxCoords.r.serialize(), '.');
        grid.set(newLeft.serialize(), '[');
        grid.set(newRight.serialize(), ']');
    }

    return newRight;
}

function readLinesToGridAndExpand(lines: string[], initialPosId?: string) {
    const grid: Grid = new Map();
    let initialPos: Coord | undefined = undefined;

    for (let y = 0; y < lines.length; y++) {
        const line = lines[y].split('');
        const expandedLine: string[] = [];

        for (const c of line) {
            switch (c) {
                case '#':
                case '.':
                    expandedLine.push(c, c);
                    break;
                case 'O':
                    expandedLine.push('[', ']');
                    break;
                case '@':
                    expandedLine.push('@', '.');
                    break;
            }
        }

        for (let x = 0; x < expandedLine.length; x++) {
            if (initialPosId && expandedLine[x] === initialPosId) {
                initialPos = new Coord(x, y);
                grid.set(new Coord(x, y).serialize(), '.');
            } else {
                grid.set(new Coord(x, y).serialize(), expandedLine[x]);
            }
        }
    }

    if (initialPos) {
        return { grid, initialPos }
    }
    return { grid };
}