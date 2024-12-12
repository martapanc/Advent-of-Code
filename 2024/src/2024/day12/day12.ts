import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, getNeighborCoordsWithDirections, Grid, readLinesToGrid} from "@utils/grid";

export async function part1(inputFile: string) {
    return await day12(inputFile);
}

export async function part2(inputFile: string) {
    return await day12(inputFile, true);
}

async function day12(inputFile: string, isPart2 = false) {
    const inputPath = path.join(__dirname, inputFile);
    const lines = await readInputLineByLine(inputPath);

    return calcAreaPerimeterAndSides(readLinesToGrid(lines), isPart2);
}

function calcAreaPerimeterAndSides(grid: Grid, isPart2: boolean) {
    const visited = new Set<string>();

    function floodFill(start: Coord) {
        const stack: Coord[] = [start];
        const fieldId = grid.get(start.serialize());
        let area = 0;
        let perimeter = 0;

        const sideSegments: Map<Cardinal, string[]> = new Map();

        while (stack.length > 0) {
            const current = stack.pop()!;
            const currentStr = current.serialize();
            if (visited.has(currentStr))
                continue;

            visited.add(currentStr);
            area++;

            for (const [direction, neighbor] of getNeighborCoordsWithDirections(current)) {
                if (grid.get(neighbor.serialize()) === fieldId) {
                    if (!visited.has(neighbor.serialize())) {
                        stack.push(neighbor);
                    }
                } else {
                    perimeter++;

                    sideSegments.get(direction) ? sideSegments.get(direction)?.push(current.serialize()) : sideSegments.set(direction, [current.serialize()]);
                }
            }
        }

        const sides = mergeToSides(sideSegments);
        return { area, perimeter, sides }
    }

    let totalCost = 0;

    for (const key of grid.keys()) {
        if (!visited.has(key)) {
            const { area, perimeter, sides } = floodFill(Coord.deserialize(key))!;
            if (isPart2) {
                totalCost += area * sides;
            } else {
                totalCost += area * perimeter;
            }
        }
    }

    return totalCost;
}

function mergeToSides(sideSegments: Map<Cardinal, string[]>) {
    let sideCount = 0;
    for (let direction of sideSegments.keys()) {
        const coords = sideSegments.get(direction)!.map(c => Coord.deserialize(c));
        if (direction === Cardinal.NORTH || direction === Cardinal.SOUTH) {
            const byY = [...splitByY(coords).values()];

            for (let splitCoords of byY) {
                const sorted = splitCoords.sort((a, b) => a.x - b.x);
                let consecutiveCounts = 1;
                for (let i = 0; i < sorted.length - 1; i++) {
                    if (sorted[i].x + 1 !== sorted[i + 1].x) {
                        consecutiveCounts++
                    }
                }

                sideCount += consecutiveCounts;
            }

        } else {
            const byX = [...splitByX(coords).values()];

            for (let splitCoords of byX) {
                const sorted = splitCoords.sort((a, b) => a.y - b.y);
                let consecutiveCounts = 1;
                for (let i = 0; i < sorted.length - 1; i++) {
                    if (sorted[i].y + 1 !== sorted[i + 1].y) {
                        consecutiveCounts++
                    }
                }
                sideCount += consecutiveCounts;
            }
        }
    }
    return sideCount;
}

function splitByX(coords: Coord[]): Map<number, Coord[]> {
    const result = new Map<number, Coord[]>();

    for (const coord of coords) {
        if (!result.has(coord.x)) {
            result.set(coord.x, []);
        }
        result.get(coord.x)!.push(coord);
    }

    return result;
}

function splitByY(coords: Coord[]): Map<number, Coord[]> {
    const result = new Map<number, Coord[]>();

    for (const coord of coords) {
        if (!result.has(coord.y)) {
            result.set(coord.y, []);
        }
        result.get(coord.y)!.push(coord);
    }

    return result;
}
