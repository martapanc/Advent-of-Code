import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day5(inputFile, calcChecksum);
}

export async function part2(inputFile: string) {
    return await day5(inputFile, calcChecksum, true);
}

type QueueRule = {
    a: number;
    b: number;
}

type Queue = number[];

async function day5(
    inputFile: string,
    calcFn?: (rules: QueueRule[], queues: Queue[], isPart2: boolean) => number,
    isPart2: boolean = false
) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    let queueRules: QueueRule[] = [];
    let queues: Queue[] = [];

    let processFirstChunk = true;
    for (let line of lines) {
        if (line === '.') {
            processFirstChunk = false;
        } else {
            if (processFirstChunk) {
                const rule: number[] = line.split("|").map(str => Number.parseInt(str));
                queueRules.push({ a: rule[0], b: rule[1] });
            } else {
                const queue: Queue = line.split(",").map(str => Number.parseInt(str));
                queues.push(queue);
            }
        }
    }

    return calcFn?.(queueRules, queues, isPart2);
}

function calcChecksum(rules: QueueRule[], queues: Queue[], isPart2: boolean) {
    let checksum = 0;

    queues.forEach(queue => {
        let isQueueValid = true;
        const rulesToCheck = rules.filter(rule => queue.includes(rule.a) && queue.includes(rule.b));

        for (const ruleToCheck of rulesToCheck) {
            const aIndex = queue.indexOf(ruleToCheck.a);
            const bIndex = queue.indexOf(ruleToCheck.b);
            if (aIndex > bIndex) {
                isQueueValid = false;
                break;
            }
        }

        // Part 1: only consider sorted queues
        if (isQueueValid && !isPart2) {
            checksum += queue[Math.floor(queue.length / 2)];
        }

        // Part 2: only consider unsorted queues
        if (!isQueueValid && isPart2) {
            const sortedQueue = topologicalSort(queue, rulesToCheck);
            checksum += sortedQueue[Math.floor(sortedQueue.length / 2)];
        }
    });

    return checksum;
}

export function topologicalSort(numbers: Queue, rules: QueueRule[]) {
    const adjList = new Map();
    const inDegree = new Map();

    numbers.forEach(num => {
        adjList.set(num, []);
        inDegree.set(num, 0);
    });

    rules.forEach(rule => {
        adjList.get(rule.a).push(rule.b);
        inDegree.set(rule.b, (inDegree.get(rule.b) ?? 0) + 1);
    });

    const queue: number[] = [];
    const result: number[] = [];

    inDegree.forEach((degree, node) => {
        if (degree === 0) {
            queue.push(node);
        }
    });

    while (queue.length > 0) {
        const node = queue.shift();
        if (node)
            result.push(node);

        adjList.get(node).forEach((neighbor: number) => {
            inDegree.set(neighbor, inDegree.get(neighbor) - 1);

            if (inDegree.get(neighbor) === 0) {
                queue.push(neighbor);
            }
        });
    }

    if (result.length === numbers.length) {
        return result;
    } else {
        throw new Error("The graph has a cycle, no valid ordering exists.");
    }
}
