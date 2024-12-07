import {generateBinaryStrings, generateTernaryStrings, isNumeric} from "@utils/numbers";

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
    expect(generateBinaryStrings(4)).toEqual([
        "0000", "1000", "0100", "1100", "0010", "1010", "0110", "1110",
        "0001", "1001", "0101", "1101", "0011", "1011", "0111", "1111"
    ]);
});

test("generate binary strings", () => {
    expect(generateTernaryStrings(0)).toEqual([""]);
    expect(generateTernaryStrings(1)).toEqual(["0", "1", "2"]);
    expect(generateTernaryStrings(2)).toEqual(["00", "10", "20", "01", "11", "21", "02", "12", "22"]);
    expect(generateTernaryStrings(3)).toEqual([
        "000", "100", "200", "010", "110", "210", "020", "120", "220",
        "001", "101", "201", "011", "111", "211", "021", "121", "221",
        "002", "102", "202", "012", "112", "212", "022", "122", "222"
    ]);
});