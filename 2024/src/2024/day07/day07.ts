import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {generateBinaryStrings, generateTernaryStrings} from "@utils/numbers";

export async function part1(inputFile: string) {
    return await day7(inputFile, generateBinaryStrings);
}

export async function part2(inputFile: string) {
    return await day7(inputFile, generateTernaryStrings);
}

async function day7(inputFile: string, calcFn: (length: number) => string[]) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const split = lines.map(line => line.split(": "));
    const equations = new Map<number, number[]>();
    split.forEach(row => {
        equations.set(Number.parseInt(row[0]), row[1].split(" ").map(str => Number.parseInt(str)));
    })

    return calcValidEquations(equations, calcFn);
}

function calcValidEquations(
    equations: Map<number, number[]>,
    generationFn: (length: number) => string[]
): number {
    let validEquationChecksum = 0;

    eqLoop: for (let [res, ops] of equations) {
        const combos = generationFn(ops.length - 1);
        for (const combo of combos) {
             let partialRes = 0;
             combo.split("").forEach((opId, index) => {
                 if (index === 0) {
                     switch (opId) {
                         case '0':
                             partialRes = ops[index] + ops[index + 1];
                             break;
                         case '1':
                             partialRes = ops[index] * ops[index + 1];
                             break;
                         case '2':
                             partialRes = Number.parseInt(`${ops[index]}${ops[index + 1]}`);
                             break;
                     }
                 } else {
                     switch (opId) {
                         case '0':
                             partialRes = partialRes + ops[index + 1];
                             break;
                         case '1':
                             partialRes = partialRes * ops[index + 1];
                             break;
                         case '2':
                             partialRes = Number.parseInt(`${partialRes}${ops[index + 1]}`);
                     }
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