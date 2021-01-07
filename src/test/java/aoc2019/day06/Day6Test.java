package aoc2019.day06;


import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day06/input1";
    private static final String INPUT2 = "src/main/java/aoc2019/day06/input2";
    private static final String INPUT3 = "src/main/java/aoc2019/day06/input3";

    @Test
    public void testReadInput() {
        System.out.println(Day6.readInput(INPUT1));
        System.out.println(Day6.readInput(INPUT2));
    }

    @Test
    public void testGetOrbitMap() {
        System.out.println(Day6.getOrbitMap(Day6.readInput(INPUT1)));
    }

    @Test
    public void testGetCompleteOrbitMap() {
        Map<String, Set<String>> completeOrbitMap = Day6.getCompleteOrbitMap(Day6.readInput(INPUT1));
        System.out.println(completeOrbitMap);
        assertEquals(42, Day6.getListSizeSum(completeOrbitMap));

        Map<String, Set<String>> completeOrbitMap2 = Day6.getCompleteOrbitMap(Day6.readInput(INPUT2));
        System.out.println(completeOrbitMap2);
        assertEquals(119831, Day6.getListSizeSum(completeOrbitMap2));
    }

    @Test
    public void calculateJumps() {
        assertEquals(4, Day6.calculateJumps(Day6.getCompleteOrbitMap(Day6.readInput(INPUT3)), INPUT3));
        assertEquals(322, Day6.calculateJumps(Day6.getCompleteOrbitMap(Day6.readInput(INPUT2)), INPUT2));
    }
}