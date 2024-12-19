import path from "node:path";
import {readInputLineByLine} from "@utils/io";

type Computer = { regA: number, regB: number, regC: number, program: string };

export async function part1(computer: Computer) {
    return await day17(computer, calcOutput);
}

export async function part2(computer: Computer) {
    return await day17(computer, calcMinValue);
}

async function day17(computer: Computer, calcFn?: (computer: Computer) => string | number | undefined) {
    return calcFn?.(computer);
}

function calcOutput(computer: Computer) {
    let { regA, regB, regC } = computer;
    const program = computer.program.split(",").map(s => Number.parseInt(s));

    let output = "";

    function getCombo(operand: number) {
        if (operand >= 0 && operand <= 3) {
            return operand;
        }
        if (operand === 4) return regA;
        if (operand === 5) return regB;
        if (operand === 6) return regC;
        if (operand === 7) throw new Error("Invalid operand 7");
    }

    let i = 0;
    while (i < program.length) {
        const opCode = program[i];
        const operand = program[i + 1];

        switch (opCode) {
            case 0:
                regA = adv(regA, Math.pow(2, getCombo(operand)!));
                break;
            case 1:
                regB = bxl(regB, operand);
                break;
            case 2:
                regB = bst(getCombo(operand)!);
                break;
            case 3:
                if (regA !== 0) {
                    i = operand;
                    continue;
                }
                break;
            case 4:
                regB = bxc(regB, regC);
                break;
            case 5:
                output += out(getCombo(operand)!) + ","
                break;
            case 6:
                regB = bdv(regA, Math.pow(2, getCombo(operand)!));
                break;
            case 7:
                regC = cdv(regA, Math.pow(2, getCombo(operand)!));
                break;
        }
        i += 2;
    }
    return output.substring(0, output.length - 1);
}

function calcMinValue(computer: Computer) {
    let i = BigInt(246291410000000);

    while (i < 1000000000000000) {
        const output = calcOutput({ regA: i, regB: 0, regC: 0, program: computer.program });
        const outputSplit = output.split(",");
        const programSplit = computer.program.split(",");
        if (outputSplit.length === programSplit.length) {
            if (outputSplit[15] == programSplit[15])
                if (outputSplit[14] == programSplit[14])
                    if (outputSplit[13] == programSplit[13])
                        if (outputSplit[12] == programSplit[12])
                            if (outputSplit[11] == programSplit[11])
                                if (outputSplit[10] == programSplit[10])
                                    if (outputSplit[9] == programSplit[9])
                                        // if (outputSplit[8] == programSplit[8])
                                            // if (outputSplit[7] == programSplit[7])
                            console.log(i)
        }
        if (output === computer.program) {
            return i;
        }
        i += 1000000000;
    }
}

const adv = (first: number, second: number) => Math.trunc(first / second);
const bdv = (first: number, second: number) => adv(first, second);
const cdv = (first: number, second: number) => adv(first, second);

const bxl = (first: number, second: number) => first ^ second;

const bst = (num: number) => num % 8;

const bxc = (regB: number, regC: number) => bxl(regB, regC);

const out = (num: number) => bst(num);