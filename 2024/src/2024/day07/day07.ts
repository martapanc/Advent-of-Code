import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {generateBinaryStrings} from "@utils/numbers";

export async function part1(inputFile: string) {
    return await day7(inputFile, calcValidEquations);
}

export async function part2(inputFile: string) {
    return await day7(inputFile);
}

async function day7(inputFile: string, calcFn?: (equations: Map<number, number[]>) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const split = lines.map(line => line.split(": "));
    const equations = new Map<number, number[]>();
    split.forEach(row => {
        equations.set(Number.parseInt(row[0]), row[1].split(" ").map(str => Number.parseInt(str)));
    })

    return calcFn?.(equations);
}

function calcValidEquations(equations: Map<number, number[]>): number {
    let validEquationChecksum = 0;

    eqLoop: for (let [res, ops] of equations) {
        const combos = generateBinaryStrings(ops.length - 1);
        for (const combo of combos) {
             let partialRes = 0;
             combo.split("").forEach((opId, index) => {
                 if (index === 0) {
                     partialRes = opId === '0' ? (ops[index] + ops[index + 1]) : (ops[index] * ops[index + 1]);
                 } else {
                     partialRes = opId === '0' ? (partialRes + ops[index + 1]) : (partialRes * ops[index + 1]);
                 }
             });

             if (partialRes === res) {
                 validEquationChecksum += res;
                 continue eqLoop;
             }
        }
    }

    return validEquationChecksum;
}