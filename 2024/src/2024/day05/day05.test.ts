import {part1, part2, topologicalSort} from "./day05";

describe('2024 Day 5', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(143);
        expect(await part1('input')).toEqual(4790);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(123);
        expect(await part2('input')).toEqual(6319);
    });
});

test('Topological sort', () => {
    expect(topologicalSort([61,13,29], [{a: 61, b: 13}, {a: 61, b: 29}, {a: 29, b: 13}])).toEqual([61, 29, 13]);
    expect(topologicalSort([29, 61, 13], [{a: 61, b: 13}, {a: 61, b: 29}, {a: 29, b: 13}])).toEqual([61, 29, 13]);
});