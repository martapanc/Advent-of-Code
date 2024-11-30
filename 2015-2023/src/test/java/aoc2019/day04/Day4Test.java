package aoc2019.day04;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Day4Test {

    private static final int MIN = 172930;
    private static final int MAX = 683082;

    @Test
    public void testFindPasswordCombinations() {
        assertEquals(1675, Day4.findPasswordCombinations(MIN, MAX));
        assertEquals(1142, Day4.findPasswordCombinationsPart2(MIN, MAX));
    }

    @Test
    public void testIsValidPassword() {
        assertTrue(Day4.isValidPassword("123455"));
        assertTrue(Day4.isValidPassword("111111"));
        assertTrue(Day4.isValidPassword("122344"));

        assertFalse(Day4.isValidPassword("223450"));
        assertFalse(Day4.isValidPassword("123456"));
    }

    @Test
    public void testIsValidPasswordPart2() {
        assertTrue(Day4.isValidPasswordPart2("123455"));
        assertTrue(Day4.isValidPasswordPart2("122344"));
        assertTrue(Day4.isValidPasswordPart2("112233"));
        assertTrue(Day4.isValidPasswordPart2("112222"));
        assertTrue(Day4.isValidPasswordPart2("111122"));

        assertFalse(Day4.isValidPasswordPart2("123444"));
        assertFalse(Day4.isValidPasswordPart2("111111"));
        assertFalse(Day4.isValidPasswordPart2("223450"));
        assertFalse(Day4.isValidPasswordPart2("123456"));
    }
}
