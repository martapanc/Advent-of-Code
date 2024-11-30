import {part1, part2} from "./day01";

describe('2023 Day 1', () => {

    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(142);
        expect(await part1('input')).toEqual(55712);
    });

    test('Part 2', async () => {
        expect(await part2('testInput2')).toEqual(281);
        expect(await part2('input')).toEqual(55413);
    });
});
