import path from "node:path";
import {readInputLineByLine} from "@utils/io";

export async function part1(inputFile: string) {
    return await day5(inputFile, findFreshIngredients);
}

export async function part2(inputFile: string) {
    return await day5(inputFile, findTotalFreshIngredientCount);
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

function findTotalFreshIngredientCount(idRanges: IdRange[], _: number[]): number {
    let reduceRanges = true;
    let idRangesCopy = idRanges.slice();

    while (reduceRanges) {
        reduceRanges = false;
        for (let i = 0; i < idRanges.length; i++){
            const a = idRanges[i];
            for (let j = i + 1; j < idRanges.length; j++) {
                const b = idRanges[j];

                if (a.from >= b.from && a.from <= b.to) {
                    reduceRanges = true;
                    idRangesCopy.splice(j, 1);
                    idRangesCopy.splice(i, 1);
                    if (a.to >= b.to) {
                        idRangesCopy.push({ from: b.from, to: a.to });
                    }
                    if (b.to >= a.to) {
                        idRangesCopy.push({ from: b.from, to: b.to });
                    }
                    break;
                }

                if (a.to >= b.from && a.to <= b.to) {
                    reduceRanges = true;
                    idRangesCopy.splice(j, 1);
                    idRangesCopy.splice(i, 1);

                    if (a.from >= b.from) {
                        idRangesCopy.push({ from: b.from, to: b.to });
                    }
                    if (b.from >= a.from) {
                        idRangesCopy.push({ from: a.from, to: b.to });
                    }
                    break;
                }
            }
            if (reduceRanges) {
                break;
            }
        }

        idRanges = idRangesCopy.slice();
    }

    let freshCount = 0;
    idRanges.forEach(idRange => {
        freshCount += (idRange.to - idRange.from) + 1;
    })

    return freshCount;
}