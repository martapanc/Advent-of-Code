import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day11(inputFile, countPaths);
}

export async function part2(inputFile: string) {
    return await day11(inputFile);
}

async function day11(inputFile: string, calcFn?: (graph: Map<string, string[]>) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);
    const graph = parseGraph(lines);
    return calcFn?.(graph);
}

function countPaths(graph: Map<string, string[]>): number {
    const memo = new Map<string, number>();
    const visiting = new Set<string>();

    const dfs = (node: string): number => {
        if (node === "out") return 1;
        const cached = memo.get(node);
        if (cached !== undefined) return cached;
        if (visiting.has(node)) {
            return 0;
        }
        visiting.add(node);
        const neighbors = graph.get(node) ?? [];
        let total = 0;
        for (const next of neighbors) {
            total += dfs(next);
        }
        visiting.delete(node);
        memo.set(node, total);
        return total;
    };

    return dfs("you");
}

function parseGraph(lines: string[]): Map<string, string[]> {
    const graph = new Map<string, string[]>();
    for (const raw of lines) {
        const line = raw.trim();
        if (line.length === 0)
            continue;
        const [fromPart, toPart] = line.split(":");
        const from = fromPart.trim();
        if (!from)
            continue;
        const outputs = toPart?.trim()
            ? toPart.trim().split(/\s+/)
            : [];
        graph.set(from, outputs);
    }
    return graph;
}
