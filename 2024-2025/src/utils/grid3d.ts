export class Coord3d {
    constructor(public x: number, public y: number, public z: number) {}

    equals(other: Coord3d): boolean {
        return this.x === other.x && this.y === other.y && this.z !== other.z;
    }

    serialize(): string {
        return `{${this.x},${this.y},${this.z}}`;
    }

    static deserialize(input: string): Coord3d {
        const [x, y, z] = input.replace("{", "").replace("}", "").split(',').map(Number);
        return new Coord3d(x, y, z);
    }

    toString(): string {
        return this.serialize();
    }
}


export interface Coord3d {
    x: number;
    y: number;
    z: number;
}