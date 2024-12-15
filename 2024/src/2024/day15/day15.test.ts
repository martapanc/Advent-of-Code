import {part1, part2, shiftBox} from "./day15";
import {readLinesToGrid} from "@utils/grid";

describe('2024 Day 15', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(2028);
        expect(await part1('testInput2')).toEqual(10092);
        expect(await part1('input')).toEqual(1499739);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(31);
        expect(await part2('input')).toEqual(29379307);
    });
});