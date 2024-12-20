import {isDesignValid, part1, part2} from "./day19";

describe('2024 Day 19', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(6);
        expect(await part1('input')).toEqual(304);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(16);
        expect(await part2('input')).toEqual(642);
    });

    test('isDesignValid', () => {
        const patterns = ["r", "wr", "b", "g", "bwu", "rb", "gb", "br"];
        expect(isDesignValid("brwrr", patterns).isValid).toBeTruthy();
        expect(isDesignValid("bggr", patterns).isValid).toBeTruthy();
        expect(isDesignValid("gbbr", patterns).isValid).toBeTruthy();
        expect(isDesignValid("rrbgbr", patterns).isValid).toBeTruthy();
        expect(isDesignValid("ubwu", patterns).isValid).toBeFalsy();
        expect(isDesignValid("bwurrg", patterns).isValid).toBeTruthy();
        expect(isDesignValid("brgr", patterns).isValid).toBeTruthy();
        expect(isDesignValid("bbrgwb", patterns).isValid).toBeFalsy();
    });
});

