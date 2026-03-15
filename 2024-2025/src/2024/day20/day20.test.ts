import {part1, part2} from "./day20";

describe('2024 Day 20', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1', 20)).toEqual(5);
        expect(await part1('testInput1', 12)).toEqual(8);
        expect(await part1('testInput1', 10)).toEqual(10);
        expect(await part1('testInput1', 8)).toEqual(14);

        expect(await part1('input', 100)).toEqual(1311);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1', 76)).toEqual(3);
        expect(await part2('testInput1', 74)).toEqual(7);
        expect(await part2('testInput1', 72)).toEqual(29);
        expect(await part2('testInput1', 70)).toEqual(41);

        expect(await part2('input', 100)).toEqual(961364);
    });

    test('calc racetrack length', () => {
    })
});