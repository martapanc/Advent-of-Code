import {part1, part2} from "./day10";

describe('2025 Day 10', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(7);
        expect(await part1('input')).toEqual(428);
    });

    test('Part 2', async () => {
        // expect(await part2('testInput1')).toEqual(31);
        // expect(await part2('input')).toEqual(29379307);
    });
});