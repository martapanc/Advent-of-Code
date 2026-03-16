import {part1, part2} from "./day05";

describe('2025 Day 5', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(3);
        expect(await part1('input')).toEqual(733);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(14);
        expect(await part2('input')).toEqual(1111);
    });
});