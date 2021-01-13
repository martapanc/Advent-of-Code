package aoc2018.day06;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {

    @Test
    public void test_manhattan_distance() {
        assertEquals(4, Day6.getManhattanDistance(new Point(0,0), new Point(3,1)));
        assertEquals(7, Day6.getManhattanDistance(new Point(0,0), new Point(3,4)));
        assertEquals(7, Day6.getManhattanDistance(new Point(3,4), new Point(0,0)));
        assertEquals(5, Day6.getManhattanDistance(new Point(3,1), new Point(0,3)));
        assertEquals(0, Day6.getManhattanDistance(new Point(3,1), new Point(3,1)));
        assertEquals(1, Day6.getManhattanDistance(new Point(0,0), new Point(0,1)));
        assertEquals(2, Day6.getManhattanDistance(new Point(0,0), new Point(1,1)));
    }

    @Test
    public void test_get_closest_point(){
        ArrayList<Point> coordinateList = new ArrayList<>();
        coordinateList.add(new Point(3,1));
        coordinateList.add(new Point(3,4));

        ArrayList<Point> minimumList = new ArrayList<>();
        minimumList.add(new Point(3,1));

        assertEquals(minimumList, Day6.getClosestPoint(new Point(0,0), coordinateList));
    }

    @Test
    public void test_get_closest_point_2(){
        ArrayList<Point> coordinateList = new ArrayList<>();
        coordinateList.add(new Point(3,1));
        coordinateList.add(new Point(1,3));

        ArrayList<Point> minimumList = new ArrayList<>();
        minimumList.add(new Point(3,1));
        minimumList.add(new Point(1,3));

        assertEquals(minimumList, Day6.getClosestPoint(new Point(0,0), coordinateList));
    }
    @Test
    public void test_get_closest_point_name(){
        Map<String, Point> coordinateList = new HashMap<>();
        coordinateList.put("1", new Point(3,1));
        coordinateList.put("2", new Point(1,3));

        assertEquals("1", Day6.getClosestPointName(new Point(3,2), coordinateList));
        assertEquals("2", Day6.getClosestPointName(new Point(1,4), coordinateList));
        assertEquals(".", Day6.getClosestPointName(new Point(0,0), coordinateList));
        assertEquals("1", Day6.getClosestPointName(new Point(3,1), coordinateList));
        assertEquals("2", Day6.getClosestPointName(new Point(1,3), coordinateList));
    }

    @Test
    public void test_read_input_file(){
        Map map = Day6.readInputFile("src/main/java/aoc2018/day06/input/in2");
//        System.out.println(map);
        map = Day6.readInputFile("src/main/java/aoc2018/day06/input/in1");
//        System.out.println(map);
    }

    @Test
    public void test_draw_matrix(){
        Map map = Day6.readInputFile("src/main/java/aoc2018/day06/input/in1");
//        Day6.drawMatrix(map);
    }

    @Test
    public void test_get_largest_area(){
        Map map = Day6.readInputFile("src/main/java/aoc2018/day06/input/in1");
//        assertEquals(4475, Day6.drawMatrix(map));

        map = Day6.readInputFile("src/main/java/aoc2018/day06/input/in4");
//        assertEquals(17, Day6.drawMatrix(map));
    }

    @Test
    public void test_get_manhattan_distance_sum() {
        Map map = Day6.readInputFile("src/main/java/aoc2018/day06/input/in4");
        assertEquals(30, Day6.getManhattanDistanceSum(new Point(4,3), map));
    }

    @Test
    public void test_compute_area() {
        Map map = Day6.readInputFile("src/main/java/aoc2018/day06/input/in4");
        assertEquals(16, Day6.computeArea(map, 32));
        map = Day6.readInputFile("src/main/java/aoc2018/day06/input/in1");
        assertEquals(35237, Day6.computeArea(map, 10000));
    }

    @Test
    public void test_fib(){
        assertEquals(1, Day6.fib(0));
        assertEquals(1, Day6.fib(1));
        assertEquals(2, Day6.fib(2));
        assertEquals(3, Day6.fib(3));
        assertEquals(5, Day6.fib(4));
        assertEquals(8, Day6.fib(5));
        assertEquals(13, Day6.fib(6));
    }
}