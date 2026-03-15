import {Cardinal, Coord, Direction, findCoordsWithinManhattanDistance, getNeighborCoords, rotate} from "@utils/grid";

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
    expect(rotate(Cardinal.NORTH, Direction.RIGHT)).toEqual(Cardinal.EAST);
    expect(rotate(Cardinal.EAST, Direction.RIGHT)).toEqual(Cardinal.SOUTH);
    expect(rotate(Cardinal.SOUTH, Direction.RIGHT)).toEqual(Cardinal.WEST);
    expect(rotate(Cardinal.WEST, Direction.RIGHT)).toEqual(Cardinal.NORTH);

    expect(rotate(Cardinal.NORTH, Direction.LEFT)).toEqual(Cardinal.WEST);
    expect(rotate(Cardinal.EAST, Direction.LEFT)).toEqual(Cardinal.NORTH);
    expect(rotate(Cardinal.SOUTH, Direction.LEFT)).toEqual(Cardinal.EAST);
    expect(rotate(Cardinal.WEST, Direction.LEFT)).toEqual(Cardinal.SOUTH);
});

test('find coords within Manhattan distance', () => {
    expect(findCoordsWithinManhattanDistance(new Coord(0, 0), 1).map(c => c.serialize()))
        .toEqual(['{-1,0}', '{0,-1}', '{0,1}', '{1,0}']);
    expect(findCoordsWithinManhattanDistance(new Coord(0, 0), 2).map(c => c.serialize()))
        .toEqual(["{-2,0}", "{-1,-1}", "{-1,0}", "{-1,1}", "{0,-2}", "{0,-1}", "{0,1}", "{0,2}", "{1,-1}", "{1,0}", "{1,1}", "{2,0}"]);
});