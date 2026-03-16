import {part1, part2} from "./day04";

describe('2025 Day 4', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(13);
        expect(await part1('input')).toEqual(1372);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(43);
        expect(await part2('input')).toEqual(7922);
    });
});