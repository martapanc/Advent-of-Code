import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day24(inputFile, findOutput);
}

export async function part2(inputFile: string) {
    return await day24(inputFile);
}

type Op = 'AND' | 'OR' | 'XOR';
type Gate = {
    wire1: string;
    op: Op,
    wire2: string;
}

async function day24(inputFile: string, calcFn?: (wires: Map<string, number>, gates: Map<string, Gate>) => number) {
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
            gates.set(split[1], { wire1: gate[0], op: gate[1] as Op, wire2: gate[2]});
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

    const zWires = new Map<number, number>();
    for (const [key, value] of solvedGates) {
        if (key.startsWith("z")) {
            const index = Number.parseInt(key.replace("z", ""));
            zWires.set(index, value);
        }
    }

    const result = Array.from(zWires)
        .sort((a, b) => b[0] - a[0])
        .map(([_, value]) => value)
        .join("");

    return Number.parseInt(result, 2);
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