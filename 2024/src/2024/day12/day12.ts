import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, getNeighborCoordsWithDirections, Grid, readLinesToGrid} from "@utils/grid";

export const part1 = async (inputFile: string) => await day12(inputFile);
export const part2 = async (inputFile: string) => await day12(inputFile, true);

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

        return { area, perimeter, sides: mergeToSides(sideSegments) }
    }

    let totalCost = 0;
    for (const key of grid.keys())
        if (!visited.has(key)) {
            const { area, perimeter, sides } = floodFill(Coord.deserialize(key))!;
            if (isPart2) {
                totalCost += area * sides;
            } else {
                totalCost += area * perimeter;
            }
        }

    return totalCost;
}

function mergeToSides(sideSegments: Map<Cardinal, string[]>) {
    let sideCount = 0;

    function countConsecutiveSegments(splitBy: Coord[][], axis: 'x' | 'y') {
        for (let coords of splitBy) {
            const sorted = coords.sort((a, b) => a[axis] - b[axis]);
            let consecutiveCounts = 1;
            for (let i = 0; i < sorted.length - 1; i++) {
                if (sorted[i][axis] + 1 !== sorted[i + 1][axis]) {
                    consecutiveCounts++
                }
            }
            sideCount += consecutiveCounts;
        }
    }

    for (let direction of sideSegments.keys()) {
        const coords = sideSegments.get(direction)!.map(c => Coord.deserialize(c));
        if (direction === Cardinal.NORTH || direction === Cardinal.SOUTH) {
            countConsecutiveSegments([...splitByY(coords).values()], 'x');
        } else {
            countConsecutiveSegments([...splitByX(coords).values()], 'y');
        }
    }
    return sideCount;
}

const splitByX = (coords: Coord[]) => splitByCoord(coords, 'x');
const splitByY = (coords: Coord[]) => splitByCoord(coords, 'y');

function splitByCoord(coords: Coord[], axis: 'x' | 'y'): Map<number, Coord[]> {
    const result = new Map<number, Coord[]>();

    coords.forEach(coord => {
        const key = axis === 'x' ? coord.x : coord.y;
        if (!result.has(key))
            result.set(key, []);
        result.get(key)!.push(coord);
    });

    return result;
}