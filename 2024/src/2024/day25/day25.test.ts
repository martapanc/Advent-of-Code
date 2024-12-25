import {part1} from "./day25";

describe('2024 Day 25', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(3);
        expect(await part1('input')).toEqual(2691);
    });
});