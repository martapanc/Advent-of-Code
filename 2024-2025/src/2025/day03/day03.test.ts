import {part1, part2} from "./day03";

describe('2025 Day 3', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(357);
        expect(await part1('input')).toEqual(17535);
    });

    test('Part 2', async () => {
        // expect(await part2('testInput1')).toEqual(31);
        // expect(await part2('input')).toEqual(29379307);
    });
});