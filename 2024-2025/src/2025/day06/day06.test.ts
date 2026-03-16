import {part1, part2} from "./day06";

describe('2025 Day 6', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(4277556);
        expect(await part1('input')).toEqual(5877594983578);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(3263827);
        expect(await part2('input')).toEqual(11159825706149);
    });
});