import {part1, part2} from "./day05";

describe('2024 Day 5', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(143);
        expect(await part1('input')).toEqual(4790);
    });

    test('Part 2', async () => {
        // expect(await part2('testInput1')).toEqual(31);
        // expect(await part2('input')).toEqual(29379307);
    });
});