package aoc2019.day09;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static aoc2019.day09.Day9.processInput;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day09/input/input1";
    private static final String INPUT2 = "src/main/java/aoc2019/day09/input/input2";
    private static final String INPUT3 = "src/main/java/aoc2019/day09/input/input3";
    private static final String INPUT4 = "src/main/java/aoc2019/day09/input/input4";

    private static final String INPUT1_five = "src/main/kotlin/aoc2019/day05/input/input1";
    private static final String INPUT2_five = "src/main/kotlin/aoc2019/day05/input/input2";
    private static final String INPUT2_2_five = "src/main/kotlin/aoc2019/day05/input/input2_2";
    private static final String INPUT3_five = "src/main/kotlin/aoc2019/day05/input/input3";
    private static final String INPUT3_2_five = "src/main/kotlin/aoc2019/day05/input/input3_2";
    private static final String INPUT4_five = "src/main/kotlin/aoc2019/day05/input/input4";
    private static final String INPUT5_five = "src/main/kotlin/aoc2019/day05/input/input5";
    private static final String INPUT6_five = "src/main/kotlin/aoc2019/day05/input/input6";

    @Test
    public void testReadInput() {
        ArrayList<Long> list = Day9.readInput(INPUT1);
        assertEquals(16, list.size());
        System.out.println(list);
    }

    @Test
    public void testProcessInput() {
//        assertEquals(1, processInput(Day9.readInput(INPUT1), 1));
        assertEquals(1219070632396864L, processInput(Day9.readInput(INPUT2), 1));
        assertEquals(1125899906842624L, processInput(Day9.readInput(INPUT3), 1));
        assertEquals(2377080455L, processInput(Day9.readInput(INPUT4), 1));
        assertEquals(74917, processInput(Day9.readInput(INPUT4), 2));
    }

    // Check that the code still work for previous versions
    @Test
    public void testProcessInput_Regression() {
        assertEquals(7988899, processInput(Day9.readInput(INPUT1_five), 1));
        assertEquals(13758663, processInput(Day9.readInput(INPUT1_five), 5));

        assertEquals(0, processInput(Day9.readInput(INPUT2_five), 7));
        assertEquals(1, processInput(Day9.readInput(INPUT2_five), 8));

        assertEquals(0, processInput(Day9.readInput(INPUT2_2_five), 8));
        assertEquals(1, processInput(Day9.readInput(INPUT2_2_five), 7));

        assertEquals(0, processInput(Day9.readInput(INPUT3_five), 7));
        assertEquals(1, processInput(Day9.readInput(INPUT3_five), 8));

        assertEquals(0, processInput(Day9.readInput(INPUT3_2_five), 8));
        assertEquals(1, processInput(Day9.readInput(INPUT3_2_five), 7));

        assertEquals(0, processInput(Day9.readInput(INPUT4_five), 0));
        assertEquals(1, processInput(Day9.readInput(INPUT4_five), 2));

        assertEquals(0, processInput(Day9.readInput(INPUT5_five), 0));
        assertEquals(1, processInput(Day9.readInput(INPUT5_five), 2));

        assertEquals(999, processInput(Day9.readInput(INPUT6_five), 7));
        assertEquals(1000, processInput(Day9.readInput(INPUT6_five), 8));
        assertEquals(1001, processInput(Day9.readInput(INPUT6_five), 9));
    }
}