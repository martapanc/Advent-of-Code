import {isNumeric} from "@utils/numbers";

test('isNumeric', () => {
    expect(isNumeric('1')).toBe(true);
    expect(isNumeric('123')).toBe(true);
    expect(isNumeric('123456789')).toBe(true);

    expect(isNumeric('a1')).toBe(false);
    expect(isNumeric('aa')).toBe(false);
    expect(isNumeric('-')).toBe(false);
    expect(isNumeric('')).toBe(false);
});