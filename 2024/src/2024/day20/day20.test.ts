import {part1, part2} from "./day20";

describe('2024 Day 20', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(11);
        expect(await part1('input')).toEqual(3246517);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(31);
        expect(await part2('input')).toEqual(29379307);
    });
});