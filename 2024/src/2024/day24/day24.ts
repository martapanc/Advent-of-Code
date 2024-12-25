import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day24(inputFile, findOutput);
}

export async function part2(inputFile: string) {
    return await day24(inputFile, findWiresToSwap);
}

type Op = 'AND' | 'OR' | 'XOR';
type Gate = {
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

    const sortedObject = Object.fromEntries(
        Array.from(solvedGates).sort(([keyA], [keyB]) => keyA.localeCompare(keyB)) // Sort keys alphabetically
    );
    console.log(JSON.stringify(sortedObject));

    // Generate Graphviz DOT format
    let dot = "digraph LogicGraph {\n";
    dot += '  rankdir=LR;\n';
    dot += '  node [shape=ellipse];\n';

    // Add input wires (x and y)
    for (const [key, value] of wires) {
        dot += `  ${key} [label="${key}: ${value}", shape=box, style=filled, color=lightblue];\n`;
    }

    // Add gates
    for (const [key, gate] of gates) {
        const { wire1, wire2, op } = gate;
        const result = solvedGates.has(key)
            ? `\\nResult: ${solvedGates.get(key)}`
            : "";
        dot += `  ${key} [label="${op}\\n${key}${result}", shape=circle, style=filled, color=lightgreen];\n`;
        dot += `  ${wire1} -> ${key};\n`;
        dot += `  ${wire2} -> ${key};\n`;
    }

    // Add output wires (z)
    for (const [key, value] of solvedGates) {
        if (key.startsWith("z")) {
            dot += `  ${key} [label="${key}: ${value}", shape=box, style=filled, color=lightyellow];\n`;
        }
    }

    dot += "}\n";

    console.log(dot);

    return findNumberFromWires('z', solvedGates);
}

function findWiresToSwap(wires: Map<string, number>, gates: Map<string, Gate>) {

    const x = findNumberFromWires('x', wires);
    console.log({x});
    const y = findNumberFromWires('y', wires);
    console.log({y});
    return "";
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

    console.log({result});

    return Number.parseInt(result, 2);
}