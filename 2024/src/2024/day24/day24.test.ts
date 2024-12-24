import {part1, part2} from "./day24";

describe('2024 Day 24', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(4);
        expect(await part1('testInput2')).toEqual(2024);
        expect(await part1('input')).toEqual(56939028423824);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(31);
        expect(await part2('input')).toEqual(29379307);
    });
});