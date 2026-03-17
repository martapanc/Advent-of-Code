import {findArea, part1, part2} from "./day09";
import {Coord} from "@utils/grid";

describe('2025 Day 9', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(50);
        expect(await part1('input')).toEqual(4759420470);
    });

    test('Part 2', async () => {
        // expect(await part2('testInput1')).toEqual(31);
        // expect(await part2('input')).toEqual(29379307);
    });

    test('findArea', async () => {
        expect(findArea(Coord.deserialize('{11,1}'), Coord.deserialize('{2,5}'))).toEqual(50);
        expect(findArea(new Coord(2, 5), new Coord(11, 1))).toEqual(50);
        expect(findArea(new Coord(7, 3), new Coord(2, 3))).toEqual(6);
        expect(findArea(new Coord(7, 1), new Coord(11, 7))).toEqual(35);
    })
});