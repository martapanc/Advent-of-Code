import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord} from "@utils/grid";
import {Matrix, solve} from 'ml-matrix';

export async function part1(inputFile: string) {
    return await day13(inputFile, playAllMachines);
}

export async function part2(inputFile: string) {
    return await day13(inputFile);
}

type ClawMachine = {
    buttonA: Coord;
    buttonB: Coord;
    prize: Coord;
}

async function day13(inputFile: string, calcFn?: (clawMachines: ClawMachine[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const clawMachines: ClawMachine[] = [];

    const parseNums = (line: string) => [...line.match(/\d+/g)!].map(s => Number.parseInt(s));

    lines.forEach((line, index) => {
        if (line.includes("Button A")) {
            const a = parseNums(line);
            const buttonA = new Coord(a[0], a[1]);
            const b = parseNums(lines[index + 1]);
            const buttonB = new Coord(b[0], b[1]);
            const p = parseNums(lines[index + 2]);
            const prize = new Coord(p[0], p[1]);
            clawMachines.push({ buttonA, buttonB, prize })
        }
    });

    return calcFn?.(clawMachines);
}

function playAllMachines(clawMachines: ClawMachine[]): number {
    let totalTokens = 0;

    clawMachines.forEach(clawMachine => {
        const { buttonA, buttonB, prize } = clawMachine;

        const coeffMatrix = new Matrix([
            [buttonA.x, buttonB.x],
            [buttonA.y, buttonB.y]
        ]);

        const resultsVector = Matrix.columnVector([prize.x, prize.y]);

        const x = solve(coeffMatrix, resultsVector);
        // const error = Matrix.sub(resultsVector, coeffMatrix.mmul(x));

        const solution = x.to1DArray().map(v => roundCloseToInteger(v));
        if (solution[0] && solution[1]) {
            totalTokens += solution[0] * 3 + solution[1] * 1
        }
    });

    return totalTokens;
}

function roundCloseToInteger(num: number, tolerance = 1e-9) {
    const nearestInt = Math.round(num);
    if (Math.abs(num - nearestInt) <= tolerance) {
        return nearestInt;
    }
    return undefined;
}