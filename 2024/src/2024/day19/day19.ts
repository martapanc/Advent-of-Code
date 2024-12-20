import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day19(inputFile, true);
}

export async function part2(inputFile: string) {
    return await day19(inputFile, false);
}

async function day19(inputFile: string, isPart1: boolean) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const patterns: string[] = lines[0].split(", ");
    const designs: string[] = [];
    lines.slice(1).map(line => {
        designs.push(line);
    })
    return findValidDesigns(patterns, designs, isPart1);
}


function findValidDesigns(patterns: string[], designs: string[], isPart1: boolean) {
    let validCount = 0;
    let arrangementCount = 0

    for (const design of designs) {
        const { isValid, validArrangements } = isDesignValid(design, patterns);

        if (isValid) {
            validCount++;
        }
        arrangementCount += validArrangements;
    }

    return isPart1 ? validCount : arrangementCount;
}

type State = {
    patternList: string[],
    index: number
}


export function isDesignValid(design: string, allPatterns: string[]) {
    let longestPattern = allPatterns.reduce((maxLength, str) => Math.max(maxLength, str.length), 0);

    const queue: State[] = [{ patternList: [], index: 0}];
    const visited = new Set<number>();

    let isValid = false;
    let validArrangements = 0;

    while (queue.length > 0) {
        const { patternList, index } = queue.pop()!;

        if (index === design.length) {
            isValid = true;
            validArrangements++;
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


    return { validArrangements, isValid };
}