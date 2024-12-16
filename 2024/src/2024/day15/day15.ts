import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, Grid, move, printGrid, readLinesToGrid} from "@utils/grid";

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

    printGrid(grid, sourcePos, 14, 7);

    for (const dir of directions) {
        const newPos = move(sourcePos, dir);
        if (grid.get(newPos.serialize()) === '.') {
            sourcePos = new Coord(newPos.x, newPos.y);
        } else if (grid.get(newPos.serialize()) === '[' || grid.get(newPos.serialize()) === ']') {
            const moved = shiftBoxes(grid, newPos, dir)!;
            if (moved) {
                sourcePos = new Coord(newPos.x, newPos.y);
            }
        }
        console.log(Cardinal[dir]);
        printGrid(grid, sourcePos, 14, 7);
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

export function shiftBoxes(grid: Grid, box: Coord, dir: Cardinal) {
    const candidatePos = move(box, dir);

    if (dir === Cardinal.WEST || dir === Cardinal.EAST) {
        const { canMove, chain } = findHorizontalChain(box, grid, dir);

        if (!canMove) {
            return false;
        }

        if (dir === Cardinal.WEST) {
            for (let i = 0; i < chain.length; i++) {
                const newLeft = move(chain[i].l, dir);
                const newRight = move(chain[i].r, dir);

                grid.set(newLeft.serialize(), '[');
                grid.set(newRight.serialize(), ']');
                grid.set(chain[i].r.serialize(), '.');
            }
        } else {
            for (let i = chain.length - 1; i >= 0; i--) {
                const newRight = move(chain[i].r, dir);
                const newLeft = move(chain[i].l, dir);

                grid.set(newRight.serialize(), ']');
                grid.set(newLeft.serialize(), '[');
                grid.set(chain[i].l.serialize(), '.');
            }
        }
    } else {
        const { canMove, chain } = findVerticalChain(box, grid, dir);

        if (!canMove) {
            return false;
        }

        if (dir === Cardinal.NORTH) {
            for (let i = 0; i < chain.length; i++) {
                const newLeft = move(chain[i].l, dir);
                const newRight = move(chain[i].r, dir);

                grid.set(newLeft.serialize(), '[');
                grid.set(newRight.serialize(), ']');
                grid.set(chain[i].l.serialize(), '.');
                grid.set(chain[i].r.serialize(), '.');
            }
        } else {
            for (let i = chain.length - 1; i >= 0; i--) {
                const newRight = move(chain[i].r, dir);
                const newLeft = move(chain[i].l, dir);

                grid.set(newRight.serialize(), ']');
                grid.set(newLeft.serialize(), '[');
                grid.set(chain[i].l.serialize(), '.');
                grid.set(chain[i].r.serialize(), '.');
            }
        }
    }

    return true;
}

type Box = { l: Coord, r: Coord };

function findHorizontalChain(pos: Coord, grid: Grid, dir: Cardinal.EAST | Cardinal.WEST): { canMove: boolean, chain: Box[]} {
    let box = findBoxCoords(pos, grid);
    let chain: Box[] = [box];
    while (true) {
        let candidatePos;
        if (dir === Cardinal.EAST) {
            candidatePos = new Coord(box.l.x + 2, pos.y);
        } else {
            candidatePos = new Coord(box.r.x - 2, pos.y);
        }
        if (grid.get(candidatePos.serialize()) === '#') {
            return { chain, canMove: false }
        }
        if (grid.get(candidatePos.serialize()) === '.') {
            return { chain, canMove: true };
        } else {
            box = findBoxCoords(candidatePos, grid);
            if (dir === Cardinal.WEST) {
                chain.splice(0, 0, box); // Place at the start
            } else {
                chain.push(box);
            }
        }
    }
}

function findVerticalChain(pos: Coord, grid: Grid, dir: Cardinal.NORTH | Cardinal.SOUTH) {
    let box = findBoxCoords(pos, grid);
    let chain: Box[] = [box];
    let stack: Box[] = [box];

    while (stack.length > 0) {
        const curr = stack.pop()!;
        const candidatePos: Coord[] = [];

        if (dir === Cardinal.NORTH) {
            candidatePos.splice(0, 0, new Coord(curr.l.x, curr.l.y - 1));
            candidatePos.push(new Coord(curr.r.x, curr.r.y - 1));
        } else {
            candidatePos.splice(0, 0, new Coord(curr.l.x, curr.l.y + 1));
            candidatePos.push(new Coord(curr.r.x, curr.r.y + 1));
        }

        if (candidatePos.some(p => grid.get(p.serialize()) === '#')) {
            return { chain, canMove: false }
        }

        for (const p of candidatePos) {
            const cell = grid.get(p.serialize());
            if (cell === '[' || cell === ']') {
                const newBoxCoords = findBoxCoords(p, grid);
                if (dir === Cardinal.NORTH) {
                    chain.splice(0, 0, newBoxCoords);
                    stack.splice(0, 0, newBoxCoords);
                } else {
                    chain.push(newBoxCoords);
                    stack.push(newBoxCoords);
                }
            }
        }
    }

    return { chain, canMove: true }
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