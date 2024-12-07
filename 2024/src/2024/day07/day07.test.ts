import {part1, part2} from "./day07";

describe('2024 Day 7', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(3749);
        expect(await part1('input')).toEqual(4998764814652);
    });

    test('Part 2', async () => {
        // expect(await part2('testInput1')).toEqual(31);
        // expect(await part2('input')).toEqual(29379307);
    });
});