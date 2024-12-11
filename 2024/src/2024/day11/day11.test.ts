import {blink, part1, part2} from "./day11";

const input = "41078 18 7 0 4785508 535256 8154 447";
const testInput1 = "0 1 10 99 999";
const testInput2 = "125 17";

describe('2024 Day 11', () => {
    test('Part 1', async () => {
        expect(await part1(testInput2)).toEqual(55312);
        expect(await part1(input)).toEqual(217443);
    });

    test('Part 2', async () => {
        expect(await part2(input)).toEqual(217443);
    });
});

test('blink', () => {
    expect(blink(testInput1).join(" ")).toEqual("1 2024 1 0 9 9 2021976")
    expect(blink(testInput2).join(" ")).toEqual("253000 1 7")
    expect(blink(blink(testInput2)).join(" ")).toEqual("253 0 2024 14168")
    expect(blink(blink(blink(testInput2))).join(" ")).toEqual("512072 1 20 24 28676032")
});