import {getLevelDeltas, part1, part2} from "./day02";

describe('2024 Day 2', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(2);
        expect(await part1('input')).toEqual(3246517);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(31);
        expect(await part2('input')).toEqual(29379307);
    });
});

test('Get level deltas', () => {
    expect(getLevelDeltas([7, 6, 4, 2, 1])).toEqual([1, 2, 2, 1]);
    expect(getLevelDeltas([1, 2, 7, 8, 9])).toEqual([-1, -5, -1, -1]);
    expect(getLevelDeltas([1, 3, 2, 4, 5])).toEqual([-2, 1, -2, -1]);
});
