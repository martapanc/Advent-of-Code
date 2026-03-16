import {chunkIntoNParts, chunkString, divisors, part1, part2} from "./day02";

describe('2025 Day 2', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(1227775554);
        expect(await part1('input')).toEqual(19574776074);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(4174379265);
        expect(await part2('input')).toEqual(25912654282);
    });

    test('divisors', () => {
        expect(divisors(3)).toEqual([]);
        expect(divisors(6)).toEqual([2, 3]);
        expect(divisors(8)).toEqual([2, 4]);
        expect(divisors(12)).toEqual([2, 3, 4, 6]);
        expect(divisors(1441440).length).toEqual(286);
    });

    test('chunkString', () => {
        expect(chunkString('123123', 3)).toEqual(['123', '123']);
        expect(chunkString('abc', 1)).toEqual(['a', 'b', 'c']);
        expect(chunkString('abcd', 3)).toEqual(['abc', 'd']);

        expect(chunkIntoNParts('abcdef', 2)).toEqual(['abc', 'def']);
        expect(chunkIntoNParts('123123123', 3)).toEqual(['123', '123', '123']);
    });
});