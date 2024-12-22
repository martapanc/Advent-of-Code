import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day21(inputFile, calcComplexities);
}

export async function part2(inputFile: string) {
    return await day21(inputFile, calcComplexities2);
}

async function day21(inputFile: string, calcFn?: (lines: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(lines);
}

function calcComplexities(lines: string[]) {
    let complexityCount = 0;

    for (const line of lines) {
        let minLength = Infinity;
        const num = Number.parseInt(line.replace('A', ''));

        const level3List: string[] = [];
        const level1 = encodeFirstLevel(line);
        for (const l1 of level1) {
            const level2 = encodeSecondLevel(l1);

            for (const l2 of level2) {
                const level3 = encodeSecondLevel(l2);

                for (const l3 of level3) {
                    if (l3.length < minLength) {
                        level3List.push(l3);
                        minLength = l3.length;
                    }
                }
            }
        }

        level3List.sort((a, b) => a.length - b.length);
        complexityCount += num * level3List[0].length;
    }
    return complexityCount;
}

function calcComplexities2(lines: string[]) {
    const memo: { [key: string]: number } = {};

    return lines.reduce((sum, code) => {
        const numerical = parseInt((code.split('').filter(character => character.match(/\d/)).join('')));
        return sum + numerical * getKeyPresses(KEYPAD, code, 25, memo);
    }, 0);
}

const BFS_DIRECTIONS = {
    '^': new Coord(0, -1),
    '>': new Coord(1, 0),
    'v': new Coord(0, 1),
    '<': new Coord(-1, 0)
};

const KEYPAD: { [key: string]: Coord } = {
    7: new Coord(0, 0),
    8: new Coord(1, 0),
    9: new Coord(2, 0),
    4: new Coord(0, 1),
    5: new Coord(1, 1),
    6: new Coord(2, 1),
    1: new Coord(0, 2),
    2: new Coord(1, 2),
    3: new Coord(2, 2),
    X: new Coord(0, 3),
    0: new Coord(1, 3),
    A: new Coord(2, 3)
};

const DIRECTIONS: { [key: string]: Coord } = {
    X:   new Coord(0, 0),
    '^': new Coord(1, 0),
    A:   new Coord(2, 0),
    '<': new Coord(0, 1),
    'v': new Coord(1, 1),
    '>': new Coord(2, 1)
};

const getCommand = (input: { [key: string]: Coord }, start: string, end: string) => {
    const queue = [{ ...input[start], path: '' }];
    const distances: { [key: string]: number } = {};

    if (start === end) return ['A'];

    let allPaths: string[] = [];
    while (queue.length) {
        const current = queue.shift();
        if (current === undefined) break;

        if (current.x === input[end].x && current.y === input[end].y) allPaths.push(current.path + 'A');
        if (distances[`${current.x},${current.y}`] !== undefined && distances[`${current.x},${current.y}`] < current.path.length) continue;

        Object.entries(BFS_DIRECTIONS).forEach(([direction, vector]) => {
            const position = { x: current.x + vector.x, y: current.y + vector.y };

            if (input.X.x === position.x && input.X.y === position.y) return;

            const button = Object.values(input).find(button => button.x === position.x && button.y === position.y);
            if (button !== undefined) {
                const newPath = current.path + direction;
                if (distances[`${position.x},${position.y}`] === undefined || distances[`${position.x},${position.y}`] >= newPath.length) {
                    queue.push({ ...position, path: newPath });
                    distances[`${position.x},${position.y}`] = newPath.length;
                }
            }
        });
    }

    return allPaths.sort((a, b) => a.length - b.length);
}

const getKeyPresses = (input: { [key: string]: Coord }, code: string, robot: number, memo: { [key: string]: number }): number => {
    const key = `${code},${robot}`;
    if (memo[key] !== undefined) return memo[key];

    let current = 'A';
    let length = 0;
    for (let i = 0; i < code.length; i++) {
        const moves = getCommand(input, current, code[i]);
        if (robot === 0) length += moves[0].length;
        else length += Math.min(...moves.map(move => getKeyPresses(DIRECTIONS, move, robot - 1, memo)));
        current = code[i];
    }

    memo[key] = length;
    return length;
}

// +---+---+---+
// | 7 | 8 | 9 |    0,0  1,0  2,0
// +---+---+---+
// | 4 | 5 | 6 |    0,1  1,1  2,1
// +---+---+---+
// | 1 | 2 | 3 |    0,2  1,2  2,2
// +---+---+---+
//     | 0 | A |         1,3  2,3
//     +---+---+
export function encodeFirstLevel(input: string) {
    const keyboardMap: { [key: string]: Coord} = {
        '7': new Coord(0, 0),
        '8': new Coord(1, 0),
        '9': new Coord(2, 0),
        '4': new Coord(0, 1),
        '5': new Coord(1, 1),
        '6': new Coord(2, 1),
        '1': new Coord(0, 2),
        '2': new Coord(1, 2),
        '3': new Coord(2, 2),
        '0': new Coord(1, 3),
        'A': new Coord(2, 3)
    }

    const outputList: { [key: string]: string[] }[] = [];
    let curr = keyboardMap['A'];

    for (const inputKey of input.split('')) {
        const target = keyboardMap[inputKey];
        const { movements } = findShortestPaths(curr, target, new Coord(0, 3));

        outputList.push({[inputKey]: movements});
        curr = target;
    }

    return generateCombinations(outputList, input);
}

//     +---+---+
//     | ^ | A |         1,0  2,0
// +---+---+---+
// | < | v | > |    0,1  1,1  2,1
// +---+---+---+
export function encodeSecondLevel(input: string) {
    const keyboardMap: { [key: string]: Coord } = {
        '^': new Coord(1, 0),
        'A': new Coord(2, 0),
        '<': new Coord(0, 1),
        'v': new Coord(1, 1),
        '>': new Coord(2, 1)
    }

    const outputList: { [key: string]: string[] }[] = [];
    let curr = keyboardMap['A'];

    for (const inputKey of input.split('')) {
        const target = keyboardMap[inputKey];
        const { movements } = findShortestPaths(curr, target, new Coord(0, 0));

        outputList.push({[inputKey]: movements});
        curr = target;
    }

    return generateCombinations(outputList, input);
}

function findShortestPaths(start: Coord, end: Coord, gap: Coord) {
    const paths: Coord[][] = [];
    const movements: string[] = [];

    function backtrack(current: Coord, path: Coord[], mov: string) {
        if (current.equals(end) && !path.some(c => c.serialize() === gap.serialize())) {
            paths.push([...path]);
            movements.push(mov + 'A');
            return;
        }

        if (current.x < end.x) {
            backtrack(
                new Coord(current.x + 1, current.y),
                [...path, new Coord(current.x + 1, current.y)],
                mov + '>'
            );
        } else if (current.x > end.x) {
            backtrack(
                new Coord(current.x - 1, current.y),
                [...path, new Coord(current.x - 1, current.y)],
                mov + '<'
            );
        }

        if (current.y < end.y) {
            backtrack(
                new Coord(current.x, current.y + 1),
                [...path, new Coord(current.x, current.y + 1)],
                mov + 'v'
            );
        } else if (current.y > end.y) {
            backtrack(
                new Coord(current.x, current.y - 1),
                [...path, new Coord(current.x, current.y - 1)],
                mov + '^'
            );
        }
    }

    backtrack(start, [start], '');

    return { paths, movements };
}

function generateCombinations(list: { [key: string]: string[] }[], keys: string): string[] {
    const result: string[] = [];

    function backtrack(index: number, currentCombination: string) {
        if (index === keys.length) {
            result.push(currentCombination);
            return;
        }

        const key = keys[index];
        const patterns = list[index][key];

        for (const pattern of patterns) {
            backtrack(index + 1, currentCombination + pattern);
        }
    }

    backtrack(0, "");

    return result;
}