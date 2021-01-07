package aoc2019.day02;

import org.junit.jupiter.api.Test;

import static aoc2019.day02.Day2.readInput;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day02/input1";
    private static final String INPUT2 = "src/main/java/aoc2019/day02/input2";
    private static final String INPUT3 = "src/main/java/aoc2019/day02/input3";
    private static final String INPUT4 = "src/main/java/aoc2019/day02/input4";
    private static final String INPUT5 = "src/main/java/aoc2019/day02/input5";
    private static final String INPUT1_2 = "src/main/java/aoc2019/day02/input1_2";

    @Test
    public void testReadInput() {
        System.out.println(readInput(INPUT1));
    }

    @Test
    public void processInput() {
        assertEquals(3500, Day2.processInput(readInput(INPUT2)));
        assertEquals(2, Day2.processInput(readInput(INPUT3)));
        assertEquals(2, Day2.processInput(readInput(INPUT4)));
        assertEquals(30, Day2.processInput(readInput(INPUT5)));
        assertEquals(5866714, Day2.processInput(readInput(INPUT1)));
    }

    @Test
    public void findPair() {
        assertEquals(5208, Day2.findPair(readInput(INPUT1_2)));
    }

}