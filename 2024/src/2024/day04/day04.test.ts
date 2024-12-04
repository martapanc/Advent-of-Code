import {part1, part2} from "./day04";

describe('2024 Day 4', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(18);
        expect(await part1('input')).toEqual(2524);
    });

    test('Part 2', async () => {
        // expect(await part2('testInput1')).toEqual(31);
        // expect(await part2('input')).toEqual(29379307);
    });
});