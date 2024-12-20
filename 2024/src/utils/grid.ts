export class Coord {
    constructor(public x: number, public y: number) {}

    equals(other: Coord): boolean {
        return this.x === other.x && this.y === other.y;
    }

    serialize(): string {
        return `{${this.x},${this.y}}`;
    }

    static deserialize(input: string): Coord {
        const [x, y] = input.replace("{", "").replace("}", "").split(',').map(Number);
        return new Coord(x, y);
    }

    toString(): string {
        return this.serialize();
    }
}

export interface Coord {
    x: number;
    y: number;
}

export type Grid = Map<string, string>;

export function readLinesToGrid(lines: string[], initialPosId?: string, endPosId?: string) {
    const grid: Grid = new Map();
    let initialPos: Coord | undefined = undefined;
    let endPos: Coord | undefined = undefined;

    for (let y = 0; y < lines.length; y++) {
        const line = lines[y].split('');
        for (let x = 0; x < line.length; x++) {
            if (initialPosId && line[x] === initialPosId) {
                initialPos = new Coord(x, y);
                grid.set(new Coord(x, y).serialize(), '.');
            } else if (endPosId && line[x] === endPosId) {
                endPos = new Coord(x, y);
                grid.set(new Coord(x, y).serialize(), '.');
            } else {
                grid.set(new Coord(x, y).serialize(), line[x]);
            }
        }
    }

    if (initialPos && endPos) {
        return { grid, initialPos, endPos }
    }
    if (initialPos) {
        return { grid, initialPos }
    }
    return { grid };
}

export function getNeighbors(source: Coord, grid: Grid, includeDiagonals: boolean = false) {
    const neighborCoords = getNeighborCoords(source, includeDiagonals);
    const neighbors: string[] = [];
    neighborCoords.forEach(coord => {
        const coordKey = coord.serialize();
        if (grid.has(coordKey)) {
            neighbors.push(grid.get(coordKey)!);
        }
    });
    return neighbors
}

export function getNeighborCoords(source: Coord, includeDiagonals: boolean = false) {
    const neighbors = [
        new Coord(source.x, source.y - 1), // N
        new Coord(source.x + 1, source.y), // E
        new Coord(source.x, source.y + 1), // S
        new Coord(source.x - 1, source.y), // W
    ];
    if (includeDiagonals) {
        neighbors.push(...getDiagonalCoords(source));
    }

    return neighbors;
}

export function getHorizontalNeighbors(source: Coord) {
    return [
        new Coord(source.x + 1, source.y), // E
        new Coord(source.x - 1, source.y), // W
    ];
}

export function getVerticalNeighbors(source: Coord) {
    return [
        new Coord(source.x, source.y - 1), // N
        new Coord(source.x, source.y + 1), // S
    ];
}

export function getNeighborCoordsWithDirections(coord: Coord): [Cardinal, Coord][] {
    return [
        [Cardinal.NORTH, new Coord(coord.x, coord.y - 1)],
        [Cardinal.EAST, new Coord(coord.x + 1, coord.y)],
        [Cardinal.SOUTH, new Coord(coord.x, coord.y + 1)],
        [Cardinal.WEST, new Coord(coord.x - 1, coord.y)],
    ];
}

function getDiagonalCoords(source: Coord) {
    const coords = []; // Clockwise
    coords.push(new Coord(source.x + 1, source.y - 1)); // NE
    coords.push(new Coord(source.x + 1, source.y + 1)); // SE
    coords.push(new Coord(source.x - 1, source.y + 1)); // SW
    coords.push(new Coord(source.x - 1, source.y - 1)); // NW

    return coords;
}

export function getDiagonalNeighbors(source: Coord, grid: Grid) {
    const diagonalCoords: Coord[] = getDiagonalCoords(source);
    const diagonalNeighbors: string[] = [];
    diagonalCoords.forEach(coord => {
        const coordKey = coord.serialize();
        if (grid.has(coordKey)) {
            diagonalNeighbors.push(grid.get(coordKey)!);
        }
    });
    return diagonalNeighbors
}

export enum Cardinal {
    NORTH = 0,
    EAST = 1,
    SOUTH = 2,
    WEST = 3
}

export enum Direction {
    LEFT,
    RIGHT
}

export function move(source: Coord, direction: Cardinal | string) {
    switch (direction) {
        case Cardinal.NORTH:
            return new Coord(source.x, source.y - 1);
        case Cardinal.EAST:
            return new Coord(source.x + 1, source.y);
        case Cardinal.SOUTH:
            return new Coord(source.x, source.y + 1);
        case Cardinal.WEST:
            return new Coord(source.x - 1, source.y);
        default:
            throw new Error('No such cardinal');
    }
}

export function rotate(source: Cardinal, rotationSense: Direction): Cardinal {
    switch (rotationSense) {
        case Direction.RIGHT:
            return (source + 1) % 4 as Cardinal;
        case Direction.LEFT:
            return (source - 1 + 4) % 4 as Cardinal;
    }
}

export function printGrid(grid: Map<string, string>, pos: Coord, width: number, height: number) {
    let str = '';
    for (let y = 0; y < height; y++) {
        let row = '';
        for (let x = 0; x < width; x++) {
            const cell = new Coord(x, y).serialize() === pos.serialize() ? '@' : grid.get(`{${x},${y}}`) || ' ';
            row += cell;
        }
        str += row + '\n';
        console.log(row);
    }
    console.log('\n');
    return str;
}

export function findCoordsWithinManhattanDistance(source: Coord, distance: number) {
    const coordinates: Coord[] = [];

    for (let x = source.x - distance; x <= source.x + distance; x++) {
        for (let y = source.y - distance; y <= source.y + distance; y++) {
            if (Math.abs(x - source.x) + Math.abs(y - source.y) <= distance) {
                const current = new Coord(x, y);
                if (!current.equals(source)) {
                    coordinates.push(current);
                }
            }
        }
    }

    return coordinates;
}