import {part1, part2} from "./day11";

describe('2025 Day 11', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(5);
        expect(await part1('input')).toEqual(497);
    });

    test('Part 2', async () => {
        expect(await part2('testInput2')).toEqual(2);
        expect(await part2('input')).toEqual(358564784931864);
    });
});