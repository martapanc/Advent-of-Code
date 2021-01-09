package aoc2019.day17;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static aoc2019.day09.Day9Kt.readInput;
import static aoc2019.day17.Day17.*;
import static org.junit.jupiter.api.Assertions.*;

class Day17Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day17/input1";
    private static final ArrayList<Long> READ_INPUT = readInput(INPUT1);
    private static final List<Point> INTERSECTIONS = getIntersections(processInput(READ_INPUT));

    @Test
    public void testReadInput() {
        System.out.println(READ_INPUT);
    }

    @Test
    public void testProcessInput() {
        processInput(READ_INPUT);
    }

    @Test
    public void testPrintMap() {
        printMap(processInput(READ_INPUT));
    }

    @Test
    public void testGetIntersections() {
        System.out.println(INTERSECTIONS);
    }

    @Test
    public void testMultiplyCoordinates() {
        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(2, 2));
        pointList.add(new Point(4, 2));
        pointList.add(new Point(4, 6));
        pointList.add(new Point(4, 10));
        assertEquals(76, multiplyCoordinates(pointList));
        assertEquals(7720, multiplyCoordinates(INTERSECTIONS));
    }
}