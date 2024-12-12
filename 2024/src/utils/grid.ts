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

export function readLinesToGrid(lines: string[]) {
    const grid: Grid = new Map();
    for (let y = 0; y < lines.length; y++) {
        const line = lines[y].split('');
        for (let x = 0; x < line.length; x++) {
            grid.set(new Coord(x, y).serialize(), line[x]);
        }
    }

    return grid;
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
        new Coord(source.x, source.y + 1), // S
        new Coord(source.x + 1, source.y), // E
        new Coord(source.x - 1, source.y), // W
    ];
    if (includeDiagonals) {
        neighbors.push(...getDiagonalCoords(source));
    }

    return neighbors;
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