import {part1} from "./day01";

describe('2024 Day 1', () => {

    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(11);
        expect(await part1('input')).toEqual(3246517);
    });

    test('Part 2', async () => {
        // expect(await part2('testInput2')).toEqual(281);
        // expect(await part2('input')).toEqual(55413);
    });
});

