import {Coord, getNeighborCoords, getNeighbors} from "@utils/grid";

test('get neighbor coords', () => {
    expect(getNeighborCoords(new Coord(0, 0))).toEqual(expect.arrayContaining([new Coord(0, 1), new Coord(0, -1), new Coord(-1, 0), new Coord(1, 0)]));
});

test('get neighbor coords including diagonals', () => {
    expect(getNeighborCoords(new Coord(0, 0), true)).toEqual(expect.arrayContaining([
        new Coord(0, 1), new Coord(0, -1), new Coord(-1, 0), new Coord(1, 0),
        new Coord(1, 1), new Coord(-1, -1), new Coord(-1, 1), new Coord(1, -1),
    ]));
});