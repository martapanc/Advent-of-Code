import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day24(inputFile, findOutput);
}

export async function part2(inputFile: string) {
    return await day24(inputFile, findWiresToSwap);
}

type Op = 'AND' | 'OR' | 'XOR';
export type Gate = {
    wire1: string;
    op: Op,
    wire2: string;
}

async function day24(inputFile: string, calcFn?: (wires: Map<string, number>, gates: Map<string, Gate>) => number | string) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    const wires = new Map<string, number>();
    const gates = new Map<string, Gate>();

    lines.forEach(line => {
        if (line.includes(":")) {
            const split = line.split(": ");
            wires.set(split[0], Number.parseInt(split[1]));
        } else {
            const split = line.split(" -> ");
            const gate = split[0].split(" ");
            let wire1 = gate[0];
            let wire2 = gate[2];

            // Alphabetical order
            if (wire1 > wire2) {
                [wire1, wire2] = [wire2, wire1];
            }

            gates.set(split[1], { wire1, op: gate[1] as Op, wire2});
        }
    })

    return calcFn?.(wires, gates);
}

function findOutput(wires: Map<string, number>, gates: Map<string, Gate>) {
    const solvedGates = new Map<string, number>();
    const unsolvedGates = new Map(gates);

    for (const [key, gate] of gates) {
        const { wire1, wire2 } = gate;
        if ((wire1.startsWith("x") || wire1.startsWith("y")) && (wire2.startsWith("x") || wire2.startsWith("y"))) {
            unsolvedGates.delete(key);
            solvedGates.set(key, calc(gate, wires));
        }
    }

    while (unsolvedGates.size > 0) {
        for (const [key, gate] of unsolvedGates) {
            if (solvedGates.has(gate.wire1) && solvedGates.has(gate.wire2)) {
                unsolvedGates.delete(key);
                solvedGates.set(key, calc(gate, solvedGates));
            }
        }
    }

    return findNumberFromWires('z', solvedGates);
}

function findWiresToSwap(_: Map<string, number>, gates: Map<string, Gate>) {
    const BIT_LENGTH = 45;
    const incorrect: string[] = [];
    for (let i = 0; i < BIT_LENGTH; i++) {
        const id = i.toString().padStart(2, '0');
        const xor1 = [...gates.entries()].find(([_, g]) => ((g.wire1 === `x${id}` && g.wire2 === `y${id}`) || (g.wire1 === `y${id}` && g.wire2 === `x${id}`)) && g.op === 'XOR');
        const and1 = [...gates.entries()].find(([_, g]) => ((g.wire1 === `x${id}` && g.wire2 === `y${id}`) || (g.wire1 === `y${id}` && g.wire2 === `x${id}`)) && g.op === 'AND');
        const z = [...gates.entries()].find(([key]) => key === `z${id}`);

        if (xor1 === undefined || and1 === undefined || z === undefined) continue;

        const [xorKey] = xor1;
        const [andKey] = and1;
        const [zKey, zGate] = z;

        // All z nodes must be connected to a XOR
        if (zGate.op !== 'XOR') {
            incorrect.push(zKey);
        }

        // All AND gates must go to an OR (excluding the first case, which starts the carry flag)
        const or = [...gates.entries()].find(([_, g]) => g.wire1 === andKey || g.wire2 === andKey);
        if (or !== undefined) {
            const [_, orGate] = or;
            if (orGate.op !== 'OR' && i > 0) {
                incorrect.push(andKey);
            }
        }

        // The first XOR must go to XOR or AND
        const next = [...gates.entries()].find(([_, g]) => g.wire1 === xorKey || g.wire2 === xorKey);
        if (next !== undefined) {
            const [_, nextGate] = next;
            if (nextGate.op === 'OR') {
                incorrect.push(xorKey);
            }
        }
    }

    // All XOR nodes must be connected to an x, y, or z node
    const wrongGates = [...gates.entries()].filter(([key, g]) =>
        !g.wire1[0].match(/[xy]/g) && !g.wire2[0].match(/[xy]/g) &&
        !key[0].match(/z/g) && g.op === 'XOR'
    ).map(([key, _]) => key);

    incorrect.push(...wrongGates);

    return incorrect.sort().join(",");
}

function and(a: number, b: number) {
    if (a === 1 && b === 1)
        return 1;
    return 0;
}

function or(a: number, b: number) {
    if (a === 1 || b === 1)
        return 1;
    return 0;
}

function xor(a: number, b: number) {
    if (a !== b)
        return 1;
    return 0;
}

function calc(gate: Gate, wires: Map<string, number>) {
    const { wire1, wire2, op } = gate;
    const a = wires.get(wire1)!;
    const b = wires.get(wire2)!;
    let res: number;

    switch (op) {
        case "AND":
            res = and(a, b);
            break;
        case "OR":
            res = or(a, b);
            break;
        case "XOR":
            res = xor(a, b);
            break;
        default:
            throw new Error(`Invalid op code ${op}`);
    }

    return res;
}

function findNumberFromWires(id: string, wires: Map<string, number>) {
    const zWires = new Map<number, number>();
    for (const [key, value] of wires) {
        if (key.startsWith(id)) {
            const index = Number.parseInt(key.replace(id, ""));
            zWires.set(index, value);
        }
    }

    const result = Array.from(zWires)
        .sort((a, b) => b[0] - a[0])
        .map(([_, value]) => value)
        .join("");

    return Number.parseInt(result, 2);
}