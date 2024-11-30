package aoc2018.day13;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day13Test {

    private final String input1 = "src/main/java/aoc2018/day13/input/input1";
    private final String input2 = "src/main/java/aoc2018/day13/input/input2";
    private final String input3 = "src/main/java/aoc2018/day13/input/input3";
    private final String input4 = "src/main/java/aoc2018/day13/input/input4";
    private final String input5 = "src/main/java/aoc2018/day13/input/input5";

    private final int ONE_X = 13;
    private final int ONE_Y = 6;
    private final int TWO_XY = 150;
    private final int FOUR_XY = 7;

    @Test
    void readInput() {
        Day13.readInput(input1, ONE_X, ONE_Y);
        Day13.readInput(input2, TWO_XY, TWO_XY);
        Day13.readInput(input3, ONE_X, ONE_Y);
        Day13.readInput(input4, FOUR_XY, FOUR_XY);

        Day13.printMatrix(Day13.readInput(input1, ONE_X, ONE_Y));
        Day13.printMatrix(Day13.readInput(input2, TWO_XY, TWO_XY));
        Day13.printMatrix(Day13.readInput(input3, ONE_X, ONE_Y));
        Day13.printMatrix(Day13.readInput(input4, FOUR_XY, FOUR_XY));
    }

    @Test
    void findInitialPositions() {
        List<Point> l1 = new ArrayList<>();
        l1.add(new Point(2, 0));
        l1.add(new Point(9, 3));
        assertEquals(l1, Day13.findInitialPositions(input1, ONE_X, ONE_Y));

        List<Point> l2 = new ArrayList<>();
        l2.add(new Point(43, 14));
        l2.add(new Point(47, 16));
        l2.add(new Point(101, 21));
        l2.add(new Point(134, 24));
        l2.add(new Point(50, 34));
        l2.add(new Point(148, 39));
        l2.add(new Point(25, 53));
        l2.add(new Point(117, 62));
        l2.add(new Point(67, 65));
        l2.add(new Point(12, 72));
        l2.add(new Point(98, 95));
        l2.add(new Point(127, 97));
        l2.add(new Point(15, 108));
        l2.add(new Point(54, 113));
        l2.add(new Point(47, 117));
        l2.add(new Point(98, 119));
        l2.add(new Point(36, 135));
        assertEquals(l2, Day13.findInitialPositions(input2, TWO_XY, TWO_XY));

        List<Point> l3 = new ArrayList<>();
        l3.add(new Point(1, 0));
        l3.add(new Point(3, 0));
        l3.add(new Point(3, 2));
        l3.add(new Point(6, 3));
        l3.add(new Point(1, 4));
        l3.add(new Point(3, 4));
        l3.add(new Point(6, 5));
        l3.add(new Point(3, 6));
        l3.add(new Point(5, 6));
        assertEquals(l3, Day13.findInitialPositions(input4, FOUR_XY, FOUR_XY));
    }

    @Test
    void findFirstCollision() {
        assertEquals(new Point(7, 3),
                Day13.findFirstCollisionPoint(
                        Day13.readInput(input1, ONE_X, ONE_Y),
                        Day13.findInitialPositions(input1, ONE_X, ONE_Y)));
        assertEquals(new Point(11, 4),
                Day13.findFirstCollisionPoint(
                        Day13.readInput(input3, ONE_X, ONE_Y),
                        Day13.findInitialPositions(input3, ONE_X, ONE_Y)));
        assertEquals(new Point(2, 0),
                Day13.findFirstCollisionPoint(
                        Day13.readInput(input4, FOUR_XY, FOUR_XY),
                        Day13.findInitialPositions(input4, FOUR_XY, FOUR_XY)));
    }

    @Test
    void findFirstCollision2() {
        assertEquals(new Point(41, 17),
                Day13.findFirstCollisionPoint(
                        Day13.readInput(input2, TWO_XY, TWO_XY),
                        Day13.findInitialPositions(input2, TWO_XY, TWO_XY)));
    }

    @Test
    void test_findLastRemainingCursor() {
        assertEquals(new Point(134, 117),
                Day13.findLastRemainingCursor(
                        Day13.readInput(input2, TWO_XY, TWO_XY),
                        Day13.findInitialPositions(input2, TWO_XY, TWO_XY)));
        assertEquals(new Point(6, 5),
                Day13.findLastRemainingCursor(
                        Day13.readInput(input5, 30, 10),
                        Day13.findInitialPositions(input5, 30, 10)));
        assertEquals(new Point(6, 4),
                Day13.findLastRemainingCursor(
                        Day13.readInput(input4, FOUR_XY, FOUR_XY),
                        Day13.findInitialPositions(input4, FOUR_XY, FOUR_XY)));
        assertEquals(new Point(4, 1),
                Day13.findLastRemainingCursor(
                        Day13.readInput(input3, ONE_X, ONE_Y),
                        Day13.findInitialPositions(input3, ONE_X, ONE_Y)));
    }

    @Test
    void areAllUnique() {
        List<Cursor> cursorList = new ArrayList<>();
        cursorList.add(new Cursor(Direction.NORTH, new Point(1, 2), Turn.LEFT, '-'));
        cursorList.add(new Cursor(Direction.NORTH, new Point(2, 3), Turn.LEFT, '-'));
        assertTrue(Day13.areAllUnique(cursorList));

        List<Cursor> cursorList2 = new ArrayList<>();
        cursorList2.add(new Cursor(Direction.NORTH, new Point(2, 3), Turn.LEFT, '-'));
        cursorList2.add(new Cursor(Direction.WEST, new Point(2, 3), Turn.STRAIGHT, '-'));
        assertFalse(Day13.areAllUnique(cursorList2));
    }
}