type Computer = { regA?: bigint, regB?: bigint, regC?: bigint, program: string };

export async function part1(computer: Computer) {
    return await day17(computer, runProgram);
}

export async function part2(computer: Computer) {
    return await day17(computer, calcMinRegA);
}

async function day17(computer: Computer, calcFn?: (computer: Computer) => string | bigint | undefined | null) {
    return calcFn?.(computer);
}

function runProgram(computer: Computer) {
    let regA = computer.regA ?? 0n;
    let regB = computer.regB ?? 0n;
    let regC = computer.regC ?? 0n;
    const program = computer.program.split(",").map(s => BigInt(s));

    let output = "";

    const getCombo = (operand: bigint): bigint => {
        switch (operand) {
            case 0n:
            case 1n:
            case 2n:
            case 3n:
                return operand;
            case 4n:
                return regA;
            case 5n:
                return regB;
            case 6n:
                return regC;
            case 7n:
                throw new Error("Invalid operand: 7");
            default:
                throw new Error(`Unknown operand: ${operand}`);
        }
    };

    let pointer = 0;
    while (pointer < program.length) {
        const opCode = program[pointer];
        const operand = program[pointer + 1];

        switch (opCode) {
            case 0n:
                regA = adv(regA, powerOfTwo(getCombo(operand)));
                break;
            case 1n:
                regB = bxl(regB, operand);
                break;
            case 2n:
                regB = bst(getCombo(operand));
                break;
            case 3n:
                if (regA !== 0n) {
                    pointer = Number(operand);
                    continue;
                }
                break;
            case 4n:
                regB = bxc(regB, regC);
                break;
            case 5n:
                output += out(getCombo(operand)) + ","
                break;
            case 6n:
                regB = bdv(regA, powerOfTwo(getCombo(operand)));
                break;
            case 7n:
                regC = cdv(regA, powerOfTwo(getCombo(operand)!));
                break;
            default:
                throw new Error(`unknown opcode ${opCode}`);
        }
        pointer += 2;
    }
    return output.slice(0, -1); // rm trailing comma
}

function calcMinRegA(computer: Computer) {
    const program = computer.program.split(',').map(num => BigInt(num));

    const findRegA = (value: bigint, current: number): bigint => {
        if (current < 0)
            return value;

        for (let i = value << 3n; i < (value << 3n) + 8n; i++) {
            const output = runProgram({ regA: i, regB: 0n, regC: 0n, program: computer.program })
                .split(',').map(s => BigInt(s));

            if (output[0] === program[current]) {
                const finalVal = findRegA(i, current - 1);
                if (finalVal !== -1n) return finalVal;
            }
        }

        return -1n;
    };

    return findRegA(0n, program.length - 1);
}

// Divide
const adv = (a: bigint, b: bigint) => a / b;
const bdv = (a: bigint, b: bigint) => adv(a, b);
const cdv = (a: bigint, b: bigint) => adv(a, b);

// XOR
const bxl = (a: bigint, b: bigint) => a ^ b;
// Modulo 8
const bst = (num: bigint) => num % 8n;
// XOR registers
const bxc = (regB: bigint, regC: bigint) => bxl(regB, regC);
// Output
const out = (num: bigint) => bst(num);

function powerOfTwo(exponent: bigint): bigint {
    return 1n << exponent;
}