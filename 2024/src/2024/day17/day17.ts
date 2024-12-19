type Computer = { regA: bigint, regB: bigint, regC: bigint, program: string };

export async function part1(computer: Computer) {
    return await day17(computer, calcOutput);
}

export async function part2(computer: Computer) {
    return await day17(computer, calcMinValue);
}

async function day17(computer: Computer, calcFn?: (computer: Computer) => string | bigint | undefined | null) {
    return calcFn?.(computer);
}

function calcOutput(computer: Computer) {
    let { regA, regB, regC } = computer;
    const program = computer.program.split(",").map(s => BigInt(s));

    let output = "";

    function getCombo(operand: bigint) {
        if (operand >= 0n && operand <= 3n) {
            return operand;
        }
        if (operand === 4n) return regA;
        if (operand === 5n) return regB;
        if (operand === 6n) return regC;
        if (operand === 7n) throw new Error("Invalid operand 7");
    }

    let i = 0;
    while (i < program.length) {
        const opCode = program[i];
        const operand = program[i + 1];

        switch (opCode) {
            case 0n:
                regA = BigInt(adv(regA, BigInt(Math.pow(2, Number(getCombo(operand)!)))));
                break;
            case 1n:
                regB = bxl(regB, operand);
                break;
            case 2n:
                regB = bst(getCombo(operand)!);
                break;
            case 3n:
                if (regA !== 0n) {
                    i = Number(operand);
                    continue;
                }
                break;
            case 4n:
                regB = bxc(regB, regC);
                break;
            case 5n:
                output += out(getCombo(operand)!) + ","
                break;
            case 6n:
                regB = BigInt(bdv(regA, BigInt(Math.pow(2, Number(getCombo(operand)!)))));
                break;
            case 7n:
                regC = BigInt(cdv(regA, BigInt(Math.pow(2, Number(getCombo(operand)!)))));
                break;
        }
        i += 2;
    }
    return output.substring(0, output.length - 1);
}

function calcMinValue(computer: Computer) {
    const program = computer.program.split(',').map(num => BigInt(num));

    const searchA = (value: bigint, current: number): bigint => {
        if (current < 0)
            return value;

        for (let i = value << 3n; i < (value << 3n) + 8n; i++) {
            const output = calcOutput({ regA: i, regB: 0n, regC: 0n, program: computer.program })
                .split(',').map(s => BigInt(s));

            if (output[0] === program[current]) {
                const finalVal = searchA(i, current - 1);
                if (finalVal !== -1n) return finalVal;
            }
        }

        return -1n;
    };

    return searchA(0n, program.length - 1);
}

const adv = (first: bigint, second: bigint): number => Math.trunc(Number(first / second));
const bdv = (first: bigint, second: bigint) => adv(first, second);
const cdv = (first: bigint, second: bigint) => adv(first, second);

const bxl = (first: bigint, second: bigint) => first ^ second;

const bst = (num: bigint) => num % 8n;

const bxc = (regB: bigint, regC: bigint) => bxl(regB, regC);

const out = (num: bigint) => bst(num);