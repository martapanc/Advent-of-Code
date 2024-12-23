import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day23(inputFile, findComputerTriplets);
}

export async function part2(inputFile: string) {
    return await day23(inputFile);
}

async function day23(inputFile: string, calcFn?: (lines: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(lines);
}

function findComputerTriplets(lines: string[]) {
    const adjacencyList: { [key: string]: Set<string>} = {};
    for (const connection of lines) {
        const [a, b] = connection.split("-");
        if (!adjacencyList[a])
            adjacencyList[a] = new Set();
        if (!adjacencyList[b])
            adjacencyList[b] = new Set();

        adjacencyList[a].add(b);
        adjacencyList[b].add(a);
    }

    const triangles: Set<string> = new Set();
    let trianglesStartingWithT = 0;
    for (const [node, neighbors] of Object.entries(adjacencyList)) {
        const neighborsArray = Array.from(neighbors);

        for (let i = 0; i < neighborsArray.length; i++) {
            for (let j = i + 1; j < neighborsArray.length; j++) {
                const neighborA = neighborsArray[i];
                const neighborB = neighborsArray[j];

                if (adjacencyList[neighborA].has(neighborB)) {
                    const triangle = [node, neighborA, neighborB].sort().join(",");
                    triangles.add(triangle);
                }
            }
        }
    }

    [...triangles].forEach((triangle: string) => {
        const split = triangle.split(",")
        if (split.some(s => s.startsWith("t"))) {
            trianglesStartingWithT++;
        }
    })

    return trianglesStartingWithT;
}