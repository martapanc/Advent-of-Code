package aoc2019.day01;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day01/input1";
    private static final String INPUT2 = "src/main/java/aoc2019/day01/input2";

    @Test
    public void testCalculateFuelForModel() {
        assertEquals(2, Day1.calculateFuelForModel(12));
        assertEquals(2, Day1.calculateFuelForModel(14));
        assertEquals(654, Day1.calculateFuelForModel(1969));
        assertEquals(33583, Day1.calculateFuelForModel(100756));
    }

    @Test
    public void testCalculateTotalRecursiveFuel() {
        assertEquals(2, Day1.calculateRecursiveFuel(14));
        assertEquals(966, Day1.calculateRecursiveFuel(1969));
        assertEquals(50346, Day1.calculateRecursiveFuel(100756));
    }

    @Test
    public void testCalculateTotalFuel() {
        assertEquals(34241, Day1.calculateTotalFuel(INPUT2, false));
        assertEquals(3147032, Day1.calculateTotalFuel(INPUT1, false));
        assertEquals(4717699, Day1.calculateTotalFuel(INPUT1, true));
    }
}
