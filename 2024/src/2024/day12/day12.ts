import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Coord, getNeighborCoords, Grid, readLinesToGrid} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day12(inputFile, calcAreaAndPerimeter);
}

export async function part2(inputFile: string) {
    return await day12(inputFile);
}

async function day12(inputFile: string, calcFn?: (grid: Grid) => number) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcFn?.(readLinesToGrid(lines));
}

function calcAreaAndPerimeter(grid: Grid) {
    const visited = new Set<string>();

    function floodFill(start: Coord) {
        const stack: Coord[] = [start];
        const fieldId = grid.get(start.serialize());
        let area = 0;
        let perimeter = 0;

        while (stack.length > 0) {
            const current = stack.pop()!;
            const currentStr = current.serialize();
            if (visited.has(currentStr))
                continue;

            visited.add(currentStr);
            area++;

            for (const neighbor of getNeighborCoords(current)) {
                if (grid.get(neighbor.serialize()) === fieldId) {
                    if (!visited.has(neighbor.serialize())) {
                        stack.push(neighbor);
                    }
                } else {
                    perimeter++;
                }
            }
        }
        return { area, perimeter }
    }

    let totalCost = 0;

    for (const key of grid.keys()) {
        if (!visited.has(key)) {
            const { area, perimeter } = floodFill(Coord.deserialize(key))!;
            totalCost += area * perimeter;
        }
    }

    return totalCost;
}