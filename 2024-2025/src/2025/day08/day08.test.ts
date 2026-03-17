import {part1, part2} from "./day08";

describe('2025 Day 8', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1', true)).toEqual(40);
        expect(await part1('input')).toEqual(123234);
    });

    test('Part 2', async () => {
        // expect(await part2('testInput1')).toEqual(31);
        // expect(await part2('input')).toEqual(29379307);
    });
});