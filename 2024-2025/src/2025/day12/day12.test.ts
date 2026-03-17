import {part1} from "./day12";

describe('2025 Day 12', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(3);
        expect(await part1('input')).toEqual(408);
    });
});