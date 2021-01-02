package aoc2018.day01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test {
    public static final String INPUT1 = "src/main/kotlin/aoc2018/day1/input1";
    public static final String INPUT2 = "src/main/kotlin/aoc2018/day1/input2";

    @Test
    public void test_addNumberTest() {
        assertEquals(3, Day1.frequencyCalculator("+1, +1, +1"));
        assertEquals(0, Day1.frequencyCalculator("+1, +1, -2"));
        assertEquals(536, Day1.frequencyCalculator(Day1.readInput(INPUT1)));
    }

    @Test
    public void test_readInput() {
        assertEquals("+19, -15, +6, +6, +17, -18, +7, +14, ", Day1.readInput(INPUT2));
    }

    @Test
    public void findFirstRepeatedFrequency() {
        assertEquals(10, Day1.findFirstRepeatedFrequency("+3, +3, +4, -2, -4"));
        assertEquals(5, Day1.findFirstRepeatedFrequency("-6, +3, +8, +5, -6"));
        assertEquals(0, Day1.findFirstRepeatedFrequency("+1, -1"));
        assertEquals(14, Day1.findFirstRepeatedFrequency("+7, +7, -2, -7, -4"));
        assertEquals(75108, Day1.findFirstRepeatedFrequency(Day1.readInput(INPUT1)));
    }
}
