import {generateBinaryStrings, isNumeric} from "@utils/numbers";

test('isNumeric', () => {
    expect(isNumeric('1')).toBe(true);
    expect(isNumeric('123')).toBe(true);
    expect(isNumeric('123456789')).toBe(true);

    expect(isNumeric('a1')).toBe(false);
    expect(isNumeric('aa')).toBe(false);
    expect(isNumeric('-')).toBe(false);
    expect(isNumeric('')).toBe(false);
});

test("generate binary strings", () => {
    expect(generateBinaryStrings(0)).toEqual([""]);
    expect(generateBinaryStrings(1)).toEqual(["0", "1"]);
    expect(generateBinaryStrings(2)).toEqual(["00", "10", "01", "11"]);
    expect(generateBinaryStrings(3)).toEqual(["000", "100", "010", "110", "001", "101", "011", "111"]);
    expect(generateBinaryStrings(4)).toEqual(["0000", "1000", "0100", "1100", "0010", "1010", "0110", "1110", "0001", "1001", "0101", "1101", "0011", "1011", "0111", "1111"]);
});