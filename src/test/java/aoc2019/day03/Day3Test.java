package aoc2019.day03;

import org.junit.jupiter.api.Test;

import static aoc2019.day03.Day3.getShortestDistances;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day03/input1";
    private static final String INPUT2 = "src/main/java/aoc2019/day03/input2";
    private static final String INPUT3 = "src/main/java/aoc2019/day03/input3";
    private static final String INPUT4 = "src/main/java/aoc2019/day03/input4";

    @Test
    public void testReadInput() {
        System.out.println(Day3.readInput(INPUT1));
    }

    @Test
    public void testGetShortestDistances() {
        assertEquals(30, getShortestDistances(Day3.readInput(INPUT2)));
        assertEquals(410, getShortestDistances(Day3.readInput(INPUT3)));
        assertEquals(610, getShortestDistances(Day3.readInput(INPUT4)));
//        assertEquals(37390, getShortestDistances(Day3.readInput(INPUT1)));
    }

    @Test
    public void testGetPathCoordinates() {
        assertEquals(6, Day3.getPathCoordinates(Day3.readInput(INPUT2)));
        assertEquals(135, Day3.getPathCoordinates(Day3.readInput(INPUT3)));
        assertEquals(159, Day3.getPathCoordinates(Day3.readInput(INPUT4)));
//        assertEquals(1264, Day3.getPathCoordinates(Day3.readInput(INPUT1)));
    }
}
