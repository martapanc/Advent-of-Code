import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day19(inputFile, findValidDesigns);
}

export async function part2(inputFile: string) {
    return await day19(inputFile);
}

async function day19(inputFile: string, calcFn?: (patterns: string[], designs: string[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const patterns: string[] = lines[0].split(", ");
    const designs: string[] = [];
    lines.slice(1).map(line => {
        designs.push(line);
    })
    return calcFn?.(patterns, designs);
}


function findValidDesigns(patterns: string[], designs: string[]) {
    let validCount = 0;

    for (const design of designs) {
        if (isDesignValid(design, patterns)) {
            validCount++;
        }
    }
    return validCount;
}

type State = {
    patternList: string[],
    index: number
}


export function isDesignValid(design: string, allPatterns: string[]) {
    let longestPattern = allPatterns.reduce((maxLength, str) => Math.max(maxLength, str.length), 0);

    const queue: State[] = [{ patternList: [], index: 0}];
    const visited = new Set<number>();

    while (queue.length > 0) {
        const { patternList, index } = queue.pop()!;

        if (index === design.length) {
            return true;
        }

        if (visited.has(index)) {
            continue;
        }
        visited.add(index);

        const substrings = new Set<string>();
        for (let i = 1; i <= longestPattern; i++) {
            const substring = design.slice(index, index + i);
            if (allPatterns.includes(substring)) {
                substrings.add(substring);
            }
        }

        [...substrings].forEach(substring => {
            queue.push({ index: index + substring.length, patternList: [...patternList, substring ]})
        });
    }

    return false;
}