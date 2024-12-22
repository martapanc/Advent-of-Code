import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day21(inputFile);
}

export async function part2(inputFile: string) {
    return await day21(inputFile);
}

async function day21(inputFile: string, calcFn?: (lines: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    for (const line of lines) {
        encodeFirstLevel(line);
    }

    return calcFn?.(lines);
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

    const outputList: { [key: string]: string[] } = {};
    let curr = keyboardMap['A'];

    for (const inputKey of input.split('')) {
        const target = keyboardMap[inputKey];
        const { movements } = findShortestPaths(curr, target);

        outputList[inputKey] = movements;
        curr = target;
    }

    return generateCombinations(outputList, input);
}

//     +---+---+
//     | ^ | A |         1,0  2,0
// +---+---+---+
// | < | v | > |    0,1  1,1  2,1
// +---+---+---+
function encodeSecondLevel(input: string) {

}

function findShortestPaths(start: Coord, end: Coord) {
    const paths: Coord[][] = [];
    const movements: string[] = [];

    function backtrack(current: Coord, path: Coord[], mov: string) {
        if (current.equals(end)) {
            paths.push([...path]);
            movements.push(mov);
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

function generateCombinations(map: { [key: string]: string[] }, keys: string): string[] {
    const result: string[] = [];

    function backtrack(index: number, currentCombination: string) {
        if (index === keys.length) {
            result.push(currentCombination);
            return;
        }

        for (const pattern of map[keys[index]]) {
            backtrack(index + 1, currentCombination + pattern + 'A');
        }
    }

    backtrack(0, "");

    return result;
}