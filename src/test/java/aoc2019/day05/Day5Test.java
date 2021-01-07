package aoc2019.day05;

import aoc2019.day02.Day2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day05/input/input1";
    private static final String INPUT2 = "src/main/java/aoc2019/day05/input/input2";
    private static final String INPUT2_2 = "src/main/java/aoc2019/day05/input/input2_2";
    private static final String INPUT3 = "src/main/java/aoc2019/day05/input/input3";
    private static final String INPUT3_2 = "src/main/java/aoc2019/day05/input/input3_2";
    private static final String INPUT4 = "src/main/java/aoc2019/day05/input/input4";
    private static final String INPUT5 = "src/main/java/aoc2019/day05/input/input5";
    private static final String INPUT6 = "src/main/java/aoc2019/day05/input/input6";

    @Test
    public void testReadInput() {
        // Reusing the read method written for Day2, as it's identical
        ArrayList<Integer> list = Day2.readInput(INPUT1);
        assertEquals(678, list.size());
        System.out.println(list);
    }

    @Test
    public void testProcessInput() {
        assertEquals(7988899, Day5.processInput(Day2.readInput(INPUT1), 1));
        assertEquals(13758663, Day5.processInput(Day2.readInput(INPUT1), 5));

        assertEquals(0, Day5.processInput(Day2.readInput(INPUT2), 7));
        assertEquals(1, Day5.processInput(Day2.readInput(INPUT2), 8));

        assertEquals(0, Day5.processInput(Day2.readInput(INPUT2_2), 8));
        assertEquals(1, Day5.processInput(Day2.readInput(INPUT2_2), 7));

        assertEquals(0, Day5.processInput(Day2.readInput(INPUT3), 7));
        assertEquals(1, Day5.processInput(Day2.readInput(INPUT3), 8));

        assertEquals(0, Day5.processInput(Day2.readInput(INPUT3_2), 8));
        assertEquals(1, Day5.processInput(Day2.readInput(INPUT3_2), 7));

        assertEquals(0, Day5.processInput(Day2.readInput(INPUT4), 0));
        assertEquals(1, Day5.processInput(Day2.readInput(INPUT4), 2));

        assertEquals(0, Day5.processInput(Day2.readInput(INPUT5), 0));
        assertEquals(1, Day5.processInput(Day2.readInput(INPUT5), 2));

        assertEquals(999, Day5.processInput(Day2.readInput(INPUT6), 7));
        assertEquals(1000, Day5.processInput(Day2.readInput(INPUT6), 8));
        assertEquals(1001, Day5.processInput(Day2.readInput(INPUT6), 9));
    }
}
