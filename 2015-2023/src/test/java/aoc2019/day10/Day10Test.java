package aoc2019.day10;


import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static aoc2019.day10.Day10.*;
import static org.junit.jupiter.api.Assertions.*;

public class Day10Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day10/input/input1";
    private static final String INPUT2 = "src/main/java/aoc2019/day10/input/input2";
    private static final String INPUT3 = "src/main/java/aoc2019/day10/input/input3";
    private static final String INPUT4 = "src/main/java/aoc2019/day10/input/input4";
    private static final String INPUT5 = "src/main/java/aoc2019/day10/input/input5";
    private static final String INPUT6 = "src/main/java/aoc2019/day10/input/input6";
    private static final String INPUT7 = "src/main/java/aoc2019/day10/input/input7";
    private static final String INPUT8 = "src/main/java/aoc2019/day10/input/input8";

    @Test
    public void testReadInput() {
        List<SpacePoint> list = readInput(INPUT1);
        assertEquals(25, list.size());
        System.out.println(list);
    }

    @Test
    public void testListAsteroids() {
        List<SpacePoint> list = readInput(INPUT1);
        List<SpacePoint> asteroids = listAsteroids(list);
        assertEquals(25, list.size());
        assertEquals(10, asteroids.size());
        System.out.println(list);
        System.out.println(asteroids);
    }

    @Test
    public void getLineFromTwoPoints() {
        assertEquals(new Line(9999, 4, true, false), Line.getLineFromTwoPoints(new Point(4, 1), new Point(4, 15)));
        assertEquals(new Line(0, 3, false, true), Line.getLineFromTwoPoints(new Point(0, 3), new Point(2, 3)));

        assertEquals(new Line(2, 0), Line.getLineFromTwoPoints(new Point(0, 0), new Point(1, 2)));
        assertEquals(new Line(2, 1), Line.getLineFromTwoPoints(new Point(0, 1), new Point(1, 3)));
        assertEquals(new Line(-1.45, 34.45), Line.getLineFromTwoPoints(new Point(23, 1), new Point(12, 17)));
        assertEquals(new Line(0.18, 0.64), Line.getLineFromTwoPoints(new Point(2, 1), new Point(13, 3)));

        assertNull(Line.getLineFromTwoPoints(new Point(0, 3), new Point(0, 3)));
    }

    @Test
    public void testDoesPointBelongToLine() {
        assertTrue(Line.doesPointBelongToLine(new Point(0, 0), new Line(2, 0)));
        assertTrue(Line.doesPointBelongToLine(new Point(3, 0), new Line(0, 3, true, false)));
        assertTrue(Line.doesPointBelongToLine(new Point(1, 4), new Line(0, 4, false, true)));

        assertFalse(Line.doesPointBelongToLine(new Point(0, 0), new Line(2, 1)));
        assertFalse(Line.doesPointBelongToLine(new Point(4, 1), new Line(0, 3, true, false)));
        assertFalse(Line.doesPointBelongToLine(new Point(1, 3), new Line(0, 4, false, true)));
    }

    @Test
    public void testFindAsteroids() {
        Map<SpacePoint, List<Line>> asteroids = findAsteroids(listAsteroids(readInput(INPUT1)));
        System.out.println(asteroids);
        assertEquals(8, findLocationsOfBestAsteroid(asteroids));

        assertEquals(33, findLocationsOfBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT2)))));
        assertEquals(16, findLocationsOfBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT7)))));
        assertEquals(35, findLocationsOfBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT3)))));
        assertEquals(41, findLocationsOfBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT4)))));
        assertEquals(210, findLocationsOfBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT5)))));
        assertEquals(230, findLocationsOfBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT6)))));
    }

    @Test
    public void testFindBestAsteroids() {
        assertEquals(new SpacePoint(new Point(3,4), SpaceItem.ASTEROID), findBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT1)))));
        assertEquals(new SpacePoint(new Point(5,8), SpaceItem.ASTEROID), findBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT2)))));
        assertEquals(new SpacePoint(new Point(1,2), SpaceItem.ASTEROID), findBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT3)))));
        assertEquals(new SpacePoint(new Point(6,3), SpaceItem.ASTEROID), findBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT4)))));
        assertEquals(new SpacePoint(new Point(11,13), SpaceItem.ASTEROID), findBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT5)))));
        assertEquals(new SpacePoint(new Point(19,11), SpaceItem.ASTEROID), findBestAsteroid(findAsteroids(listAsteroids(readInput(INPUT6)))));
    }

    @Test
    public void testGetAngularCoefficientMap() {
        System.out.println(getAngularCoefficientMap(new Point(19,11), listAsteroids(readInput(INPUT6))));
        System.out.println(getAngularCoefficientMap(new Point(11,13), listAsteroids(readInput(INPUT5))));
        System.out.println(getAngularCoefficientMap(new Point(8,3), listAsteroids(readInput(INPUT8))));
        System.out.println(getAngularCoefficientMap(new Point(3,2), listAsteroids(readInput(INPUT1))));
        System.out.println(getAngularCoefficientMap(new Point(3,4), listAsteroids(readInput(INPUT1))));
        System.out.println(getAngularCoefficientMap(new Point(3,3), listAsteroids(readInput(INPUT2))));
    }
}
