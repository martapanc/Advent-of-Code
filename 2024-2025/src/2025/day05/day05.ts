import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day5(inputFile, findFreshIngredients);
}

export async function part2(inputFile: string) {
    return await day5(inputFile);
}

type IdRange = { from: number; to: number };

async function day5(inputFile: string, calcFn?: (idRanges: IdRange[], ingredientIds: number[]) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    const idRanges: IdRange[] = [];
    const ingredientIds: number[] = [];
    for (const line of lines) {
        if (line === '')
            continue;
        if (line.indexOf('-') !== -1) {
            const split = line.split('-').map(Number);
            idRanges.push({from: split[0], to: split[1]});
        } else {
            ingredientIds.push(Number(line));
        }
    }

    return calcFn?.(idRanges, ingredientIds);
}

function findFreshIngredients(idRanges: IdRange[], ingredientIds: number[]): number {
    let freshCount = 0;
    for (const id of ingredientIds)
        for (const idRange of idRanges) {
            if (id < idRange.from || id > idRange.to)
                continue;
            else {
                freshCount++
                break;
            }
        }
    return freshCount;
}