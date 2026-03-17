import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord3d} from "@utils/grid3d";

export async function part1(inputFile: string, isTestInput: boolean = false) {
    return await day8(inputFile, findCircuits, isTestInput);
}

export async function part2(inputFile: string) {
    return await day8(inputFile, findCircuitsPart2);
}

async function day8(inputFile: string, calcFn?: (boxes: Coord3d[], isTestInput: boolean) => number, isTestInput: boolean = false) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    const boxes = lines.map((line) => {
        const split = line.split(",").map(Number);
        return new Coord3d(split[0], split[1], split[2]);
    });
    return calcFn?.(boxes, isTestInput);
}

function dist(a: Coord3d, b: Coord3d): number {
    return Math.sqrt((a.x - b.x) ** 2 + (a.y - b.y) ** 2 + (a.z - b.z) ** 2);
}

function findCircuits(boxes: Coord3d[], isTestInput: boolean): number {
    const n = boxes.length;
    const k = isTestInput ? Math.floor(n / 2) : n;

    // Collect all pairs sorted by distance
    const pairs: [number, number, number][] = [];
    for (let i = 0; i < n; i++) {
        for (let j = i + 1; j < n; j++) {
            pairs.push([dist(boxes[i], boxes[j]), i, j]);
        }
    }
    pairs.sort((a, b) => a[0] - b[0]);

    // Union-Find
    const parent = Array.from({length: n}, (_, i) => i);
    const size = new Array(n).fill(1);

    function find(x: number): number {
        while (parent[x] !== x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    function union(a: number, b: number) {
        a = find(a); b = find(b);
        if (a === b) return;
        if (size[a] < size[b]) [a, b] = [b, a];
        parent[b] = a;
        size[a] += size[b];
    }

    for (let i = 0; i < k; i++) {
        union(pairs[i][1], pairs[i][2]);
    }

    // Collect circuit sizes via root nodes
    const sizes: number[] = [];
    for (let i = 0; i < n; i++) {
        if (find(i) === i) sizes.push(size[i]);
    }
    sizes.sort((a, b) => b - a);

    return sizes[0] * sizes[1] * sizes[2];
}

function findCircuitsPart2(boxes: Coord3d[], _isTestInput: boolean): number {
    const n = boxes.length;

    const pairs: [number, number, number][] = [];
    for (let i = 0; i < n; i++) {
        for (let j = i + 1; j < n; j++) {
            pairs.push([dist(boxes[i], boxes[j]), i, j]);
        }
    }
    pairs.sort((a, b) => a[0] - b[0]);

    const parent = Array.from({length: n}, (_, i) => i);
    const size = new Array(n).fill(1);

    function find(x: number): number {
        while (parent[x] !== x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    let components = n;
    for (const [, i, j] of pairs) {
        const ri = find(i), rj = find(j);
        if (ri === rj) continue;
        const [big, small] = size[ri] >= size[rj] ? [ri, rj] : [rj, ri];
        parent[small] = big;
        size[big] += size[small];
        components--;
        if (components === 1) {
            return boxes[i].x * boxes[j].x;
        }
    }

    return -1;
}