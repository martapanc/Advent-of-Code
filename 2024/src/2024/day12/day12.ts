import path from "node:path";
import {readInputLineByLine} from "@utils/io";
import {Cardinal, Coord, getNeighborCoords, getNeighborCoordsWithDirections, Grid, readLinesToGrid} from "@utils/grid";

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

        const sides = new Set<string>();
        const connectedSides: Set<string>[] = [];

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

                    const sideKey = createSideKey(current, direction);
                    if (!sides.has(sideKey)) {
                        sides.add(sideKey);
                    }
                }
            }
        }
        return { area, perimeter, sides: sides.size }
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

function createSideKey(coord: Coord, direction: Cardinal): string {
    switch (direction) {
        case Cardinal.NORTH:
            return `{${coord.x},${coord.y}}-N`;
        case Cardinal.EAST:
            return `{${coord.x},${coord.y}}-E`;
        case Cardinal.SOUTH:
            return `{${coord.x},${coord.y}}-S`;
        case Cardinal.WEST:
            return `{${coord.x},${coord.y}}-W`;
    }
}

// Merge boundary segments into connected sides
function mergeIntoConnectedSides(
    connectedSides: Set<string>[],
    sideKey: string,
    coord: Coord,
    direction: Cardinal
) {
    // Find a matching connected side
    let merged = false;
    for (const sideGroup of connectedSides) {
        if (isConnectedToGroup(sideGroup, coord, direction)) {
            sideGroup.add(sideKey);
            merged = true;
            break;
        }
    }

    // If no group matched, create a new one
    if (!merged) {
        const newGroup = new Set<string>();
        newGroup.add(sideKey);
        connectedSides.push(newGroup);
    }
}

// Determine if a boundary connects to any in a group
function isConnectedToGroup(
    sideGroup: Set<string>,
    coord: Coord,
    direction: Cardinal
): boolean {
    for (const existingSideKey of sideGroup) {
        const [existingCoord, existingDirection] = parseSideKey(existingSideKey);
        if (
            existingDirection === direction && // Same orientation
            areAdjacent(coord, existingCoord, direction) // Contiguous
        ) {
            return true;
        }
    }
    return false;
}

// Check if two coordinates are adjacent in a specific direction
function areAdjacent(coord1: Coord, coord2: Coord, direction: Cardinal): boolean {
    switch (direction) {
        case Cardinal.NORTH:
            return coord1.x === coord2.x && coord1.y === coord2.y + 1;
        case Cardinal.SOUTH:
            return coord1.x === coord2.x && coord1.y === coord2.y - 1;
        case Cardinal.EAST:
            return coord1.x === coord2.x - 1 && coord1.y === coord2.y;
        case Cardinal.WEST:
            return coord1.x === coord2.x + 1 && coord1.y === coord2.y;
    }
}

function parseSideKey(sideKey: string): [Coord, Cardinal] {
    const [coordPart, directionPart] = sideKey.split("-");
    const coord = Coord.deserialize(coordPart);

    return [coord, parseCardinal(directionPart)!];
}

function parseCardinal(str: string) {
    switch (str) {
        case "E":
            return Cardinal.EAST;
        case "N":
            return Cardinal.NORTH;
        case "W":
            return Cardinal.WEST;
        case "S":
            return Cardinal.SOUTH;
    }
}