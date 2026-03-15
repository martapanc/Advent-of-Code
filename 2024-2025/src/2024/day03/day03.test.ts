import {getEnabledMulExpressions, getMulExpressions, part1, part2} from "./day03";

describe('2024 Day 3', () => {
    test('Part 1', async () => {
        expect(await part1('testInput1')).toEqual(161);
        expect(await part1('input_oneline')).toEqual(166357705);
    });

    test('Part 2', async () => {
        expect(await part2('input_oneline')).toEqual(88811886);
    });
});

test('get mul Expressions', () => {
    expect(getMulExpressions("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)")).toEqual(['mul(2,4)', 'mul(5,5)']);
    expect(getMulExpressions("blah_mul(124,258)&sa8913")).toEqual(['mul(124,258)']);
    expect(getMulExpressions("mul(1124,258)")).toBeNull();
});

test('get enabled mul Expressions', () => {
    expect(getEnabledMulExpressions("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")).toEqual(["mul(2,4)", "mul(8,5)"])
});