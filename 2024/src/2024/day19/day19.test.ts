import {isDesignValid, part1, part2} from "./day19";

describe('2024 Day 19', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(6);
        expect(await part1('input')).toEqual(304);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(16);
        expect(await part2('input')).toEqual(705756472327497);
    });

    test('isDesignValid', () => {
        const patterns = ["r", "wr", "b", "g", "bwu", "rb", "gb", "br"];
        expect(isDesignValid("brwrr", patterns)).toBeTruthy();
        expect(isDesignValid("bggr", patterns)).toBeTruthy();
        expect(isDesignValid("gbbr", patterns)).toBeTruthy();
        expect(isDesignValid("rrbgbr", patterns)).toBeTruthy();
        expect(isDesignValid("ubwu", patterns)).toBeFalsy();
        expect(isDesignValid("bwurrg", patterns)).toBeTruthy();
        expect(isDesignValid("brgr", patterns)).toBeTruthy();
        expect(isDesignValid("bbrgwb", patterns)).toBeFalsy();
    });
});

