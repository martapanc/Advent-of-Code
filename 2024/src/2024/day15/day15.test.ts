import {part1, part2, shiftBox} from "./day15";
import {readLinesToGrid} from "@utils/grid";

describe('2024 Day 15', () => {
    beforeEach(() => {
        global.console = require('console');
    });

    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(2028);
        expect(await part1('testInput2')).toEqual(10092);
        expect(await part1('input')).toEqual(1499739);
    });

    test('Part 2', async () => {
        expect(await part2('testInput3')).toEqual(105 + 207 + 306);
        expect(await part2('testInput2')).toEqual(9021);
        expect(await part2('input')).toEqual(1533813); // Too High
    });
});