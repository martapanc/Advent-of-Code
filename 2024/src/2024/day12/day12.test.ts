import {part1, part2} from "./day12";

describe('2024 Day 12', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(140);
        expect(await part1('testInput2')).toEqual(772);
        expect(await part1('testInput3')).toEqual(1930);
        expect(await part1('input')).toEqual(1424006);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(80);
        expect(await part2('testInput3')).toEqual(1206);
        expect(await part2('input')).toEqual(29379307);
    });
});