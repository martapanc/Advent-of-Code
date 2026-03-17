import {part1, part2} from "./day07";

describe('2025 Day 7', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(21);
        expect(await part1('input')).toEqual(1598);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(40);
        expect(await part2('input')).toEqual(4509723641302);
    });
});