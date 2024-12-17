import {part1, part2} from "./day16";

describe('2024 Day 16', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(7036);
        expect(await part1('testInput2')).toEqual(11048);
        expect(await part1('input')).toEqual(127520);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(31);
        expect(await part2('input')).toEqual(29379307);
    });
});