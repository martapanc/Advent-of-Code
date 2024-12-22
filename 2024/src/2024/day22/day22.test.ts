import {calcNthSecretNumber, calcSecretNumber, mix, part1, part2, prune} from "./day22";

describe('2024 Day 22', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(37327623);
        expect(await part1('input')).toEqual(14082561342);
    });

    test('Part 2', async () => {
        expect(await part2('testInput1')).toEqual(31);
        expect(await part2('input')).toEqual(29379307);
    });

    test('calcSecretNumber', () => {
        expect(calcSecretNumber(123)).toEqual(15887950);
        expect(calcSecretNumber(15887950)).toEqual(16495136);
        expect(calcSecretNumber(16495136)).toEqual(527345);
        expect(calcSecretNumber(527345)).toEqual(704524);
    })

    test('calcNthSecretNumber', () => {
        expect(calcNthSecretNumber(123, 10)).toEqual(5908254);
        expect(calcNthSecretNumber(1, 2000)).toEqual(8685429);
        expect(calcNthSecretNumber(10, 2000)).toEqual(4700978);
        expect(calcNthSecretNumber(100, 2000)).toEqual(15273692);
        expect(calcNthSecretNumber(2024, 2000)).toEqual(8667524);
    })

    test('mix & prune', () => {
        expect(mix(42, 15)).toEqual(37);

        expect(prune(100000000)).toEqual(16113920);
    })
});