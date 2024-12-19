import {part1, part2} from "./day18";

describe('2024 Day 18', () => {
    beforeEach(() => {
        global.console = require('console');
    });

    test('Part 1', async () => {
        expect(await part1('testInput1', 7, 12)).toEqual(22);
        expect(await part1('input')).toEqual(348);
    });

    test('Part 2', async () => {
        // expect(await part2('testInput1', 6)).toEqual(31);
        // expect(await part2('input')).toEqual(29379307);
    });
});