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

function getDiagonalCoords(source: Coord) {
    const coords = [];
    coords.push(new Coord(source.x + 1, source.y - 1)); // NE
    coords.push(new Coord(source.x + 1, source.y + 1)); // SE
    coords.push(new Coord(source.x - 1, source.y - 1)); // NW
    coords.push(new Coord(source.x - 1, source.y + 1)); // SW

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