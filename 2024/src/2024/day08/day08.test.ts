import {findAntinodes, part1, part2} from "./day08";
import {Coord} from "@utils/grid";

describe('2024 Day 8', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(14);
        expect(await part1('input')).toEqual(426);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(34);
        expect(await part2('input')).toEqual(1359);
    });
});

test('find antinodes', () => {
    expect(findAntinodes(new Coord(5, 2), new Coord(7, 3))).toEqual([new Coord(3, 1), new Coord(9, 4)]);
    expect(findAntinodes(new Coord(7, 3), new Coord(5, 2))).toEqual([new Coord(9, 4), new Coord(3, 1)]);

    expect(findAntinodes(new Coord(8, 1), new Coord(5, 2))).toEqual([new Coord(11, 0), new Coord(2, 3)]);
    expect(findAntinodes(new Coord(5, 2), new Coord(8, 1))).toEqual([new Coord(2, 3), new Coord(11, 0)]);
});