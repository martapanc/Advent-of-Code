import {expandDiskMap, part1, part2} from "./day09";

describe('2024 Day 9', () => {
    test('Part 1', async () => {
        expect(await part1('testInput2')).toEqual(60);
        expect(await part1('testInput1')).toEqual(1928);
        expect(await part1('input')).toEqual(87084237216); //Too low
    });

    test('Part 2', async () => {
        // expect(await part2('testInput1')).toEqual(31);
        // expect(await part2('input')).toEqual(29379307);
    });
});

test('organize', () => {
    // 0..111....22222
    // 022111222......
    expect(expandDiskMap("12345")).toEqual([{0: 1}, {'.': 2}, {1: 3}, {'.': 4}, {2: 5}]);

    // 00...111...2...333.44.5555.6666.777.888899
    // 0099811188827773336446555566..............
    expect(expandDiskMap("2333133121414131402")).toEqual([
        {0: 2}, {'.': 3}, {1: 3}, {'.': 3}, {2: 1}, {'.': 3}, {3: 3}, {'.': 1}, {4: 2}, {'.': 1},
        {5: 4}, {'.': 1}, {6: 4}, {'.': 1}, {7: 3}, {'.': 1}, {8: 4}, {9: 2}
    ]);
});