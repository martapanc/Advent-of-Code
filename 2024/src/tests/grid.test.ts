import {Cardinal, Coord, Direction, getNeighborCoords, rotate} from "@utils/grid";

test('get neighbor coords', () => {
    expect(getNeighborCoords(new Coord(0, 0))).toEqual(expect.arrayContaining([new Coord(0, 1), new Coord(0, -1), new Coord(-1, 0), new Coord(1, 0)]));
});

test('get neighbor coords including diagonals', () => {
    expect(getNeighborCoords(new Coord(0, 0), true)).toEqual(expect.arrayContaining([
        new Coord(0, 1), new Coord(0, -1), new Coord(-1, 0), new Coord(1, 0),
        new Coord(1, 1), new Coord(-1, -1), new Coord(-1, 1), new Coord(1, -1),
    ]));
});

test('rotate', () => {
    expect(rotate(Cardinal.NORTH, Direction.RIGHT)).toEqual("EAST");
    expect(rotate(Cardinal.EAST, Direction.RIGHT)).toEqual("SOUTH");
    expect(rotate(Cardinal.SOUTH, Direction.RIGHT)).toEqual("WEST");
    expect(rotate(Cardinal.WEST, Direction.RIGHT)).toEqual("NORTH");

    expect(rotate(Cardinal.NORTH, Direction.LEFT)).toEqual("WEST");
    expect(rotate(Cardinal.EAST, Direction.LEFT)).toEqual("NORTH");
    expect(rotate(Cardinal.SOUTH, Direction.LEFT)).toEqual("EAST");
    expect(rotate(Cardinal.WEST, Direction.LEFT)).toEqual("SOUTH");
});