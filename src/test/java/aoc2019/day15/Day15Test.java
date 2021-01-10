package aoc2019.day15;


import org.junit.jupiter.api.Test;

import static aoc2019.day09.Day9Kt.readInput;
import static aoc2019.day15.Day15.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day15/input1";

    @Test
    public void testReadInput() {
        System.out.println(readInput(INPUT1));
    }

    @Test
    public void testProcessInput() {
        processInput(readInput(INPUT1));
    }

    @Test
    public void testGetRelativeWestOfCurrentDirection() {
        assertEquals(Direction.WEST, getRelativeWestOfCurrentDirection(Direction.NORTH));
        assertEquals(Direction.NORTH, getRelativeWestOfCurrentDirection(Direction.EAST));
        assertEquals(Direction.EAST, getRelativeWestOfCurrentDirection(Direction.SOUTH));
        assertEquals(Direction.SOUTH, getRelativeWestOfCurrentDirection(Direction.WEST));
    }

    @Test
    public void testFindStepsToOxigenMachine() {
        assertEquals(242, findStepsToOxigenMachine(processInput(readInput(INPUT1))));
    }
}
