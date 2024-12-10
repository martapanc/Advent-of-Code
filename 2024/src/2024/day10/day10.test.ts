import {part1, part2} from "./day10";

describe('2024 Day 10', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(36);
        expect(await part1('input')).toEqual(796);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(81);
        expect(await part2('input')).toEqual(1942);
    });
});