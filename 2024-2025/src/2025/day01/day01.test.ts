import {part1, part2} from "./day01";

describe('2024 Day 1', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(3);
        expect(await part1('input')).toEqual(1105);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(6);
        expect(await part2('input')).toEqual(6599);
    });
});
