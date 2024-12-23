import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day23(inputFile, findComputerTriplets);
}

export async function part2(inputFile: string) {
    return await day23(inputFile, findLargestParty);
}

async function day23(inputFile: string, calcFn?: (lines: string[]) => number | string) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(lines);
}

function findComputerTriplets(lines: string[]) {
    const adjacencyList = getAdjacencyList(lines);

    return getTrianglesStartingWithT(adjacencyList);
}

// Bron–Kerbosch Algorithm:
// It operates on three sets:
//   - R (Current Clique): Nodes included in the current clique.
//   - P (Candidates): Nodes that can be added to the current clique.
//   - X (Excluded): Nodes already considered and excluded from the current clique.
// At each step:
//   - If P and X are empty, R is a maximal clique.
//   - A pivot is selected to reduce the search space and optimize performance.
// Recursive calls refine these sets to explore possible cliques.

function findLargestParty(lines: string[]) {
    const adjacencyList = getAdjacencyList(lines);

    const findLargestParties = (adjacencyList: { [key: string]: Set<string> }) => {
        const parties: string[][] = [];

        const bronKerbosch = (r: Set<string>, p: Set<string>, x: Set<string>) => {
            if (p.size === 0 && x.size === 0) {
                parties.push([...r]);
                return;
            }

            const pivot = p.size > 0 ? [...p][0] : null;
            const pivotNeighbors = pivot ? adjacencyList[pivot] : new Set();

            for (const node of [...p].filter((n) => !pivotNeighbors.has(n))) {
                bronKerbosch(
                    new Set([...r, node]),
                    new Set([...p].filter((n) => adjacencyList[node].has(n))),
                    new Set([...x].filter((n) => adjacencyList[node].has(n))),
                );
                p.delete(node);
                x.add(node);
            }
        }

        bronKerbosch(new Set(), new Set(Object.keys(adjacencyList)), new Set());
        return parties;
    }

    const findLargestParty = (parties: string[][]) => {
        return parties.reduce((max, party) => (party.length > max.length ? party : max), []);
    }

    return findLargestParty(findLargestParties(adjacencyList)).sort().join(",");
}

function getAdjacencyList(lines: string[]) {
    const adjacencyList: { [key: string]: Set<string> } = {};
    for (const connection of lines) {
        const [a, b] = connection.split("-");
        if (!adjacencyList[a])
            adjacencyList[a] = new Set();
        if (!adjacencyList[b])
            adjacencyList[b] = new Set();

        adjacencyList[a].add(b);
        adjacencyList[b].add(a);
    }
    return adjacencyList;
}

function getTrianglesStartingWithT(adjacencyList: { [p: string]: Set<string> }) {
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