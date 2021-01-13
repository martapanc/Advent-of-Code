package aoc2018.day06;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static aoc2018.day04.Day4Kt.getKeyFromValue;

public class Day6 {

    public static int getManhattanDistance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public static ArrayList<Point> getClosestPoint(Point point, Collection<Point> coordinateList) {

        Map<Point, Integer> map = new HashMap<>();
        for (Point p : coordinateList)
            map.put(p, getManhattanDistance(point, p));

        Integer min = Collections.min(map.values());
        ArrayList<Point> minimumList = new ArrayList<>();

        for (Map.Entry<Point, Integer> entry : map.entrySet())
            if (Objects.equals(entry.getValue(), min))
                minimumList.add(entry.getKey());

        return minimumList;
    }

    public static String getClosestPointName(Point point, Map<String, Point> coordinateList) {
        ArrayList<Point> minimumList = getClosestPoint(point, coordinateList.values());
        if (minimumList.size() == 1)
            return getKeyFromValue(coordinateList, minimumList.get(0));
        else
            return ".";
    }

    public static Map<String, Point> readInputFile(String filepath) {
        BufferedReader reader;
        Map<String, Point> map = new HashMap<>();
        String[] data;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            while (line != null) {
                data = line.split(",");
                map.put(data[0], new Point(Integer.parseInt(data[1]), Integer.parseInt(data[2])));
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static int drawMatrix(Map<String, Point> coordinateList) {
        Point maxCoordinates = getMaxCoordinates(coordinateList);
        String[][] matrix = new String[maxCoordinates.x][maxCoordinates.y];

        for (int i = 0; i < maxCoordinates.x; i++) {
            for (int j = 0; j < maxCoordinates.y; j++) {
                matrix[i][j] = getClosestPointName(new Point(i, j), coordinateList);
            }
        }

        Map<String, Integer> countMap = new HashMap<>();

        for (int i = 0; i < maxCoordinates.x; i++) {
            for (int j = 0; j < maxCoordinates.y; j++) {
                int prevSum;
                if (countMap.get(matrix[i][j]) == null) {
                    prevSum = 0;
                } else {
                    prevSum = countMap.get(matrix[i][j]);
                }
                countMap.put(matrix[i][j], ++prevSum);
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        return getLargestArea(maxCoordinates, matrix, countMap);
    }

    private static int getLargestArea(Point maxCoordinates, String[][] matrix, Map<String, Integer> countMap) {
        Set<String> infiniteAreaIds = new HashSet<>();

        for (int i = 0; i < maxCoordinates.x; i++) {
            infiniteAreaIds.add(matrix[i][0]);
            infiniteAreaIds.add(matrix[i][maxCoordinates.y-1]);
        }
        for (int i = 0; i < maxCoordinates.y; i++) {
            infiniteAreaIds.add(matrix[0][i]);
            infiniteAreaIds.add(matrix[maxCoordinates.x-1][i]);
        }

        for (String id : infiniteAreaIds)
            countMap.remove(id);

        return Collections.max(countMap.values());
    }

    public static Point getMaxCoordinates(Map<String, Point> coordinateList) {

        int maxX = 0, maxY = 0;
        for (Point p : coordinateList.values()) {
            if (maxX < p.x) maxX = p.x;
            if (maxY < p.y) maxY = p.y;
        }
        return new Point(maxX + maxX/5, maxY + maxY/5);
    }

    public static int getManhattanDistanceSum(Point point, Map<String, Point> coordinateMap) {
        int distanceSum = 0;
        for (Point p : coordinateMap.values()) {
            distanceSum += getManhattanDistance(p, point);
        }
        return distanceSum;
    }

    public static int computeArea(Map<String, Point> coordinateMap, int maxDistance) {
        int area = 0;
        Point maxCoordinates = getMaxCoordinates(coordinateMap);
        String[][] matrix = new String[maxCoordinates.x][maxCoordinates.y];

        for (int i = 0; i < maxCoordinates.x; i++) {
            for (int j = 0; j < maxCoordinates.y; j++) {
                if (getManhattanDistanceSum(new Point(i, j), coordinateMap) < maxDistance) {
                    matrix[i][j] = "#";
                    area += 1;
                } else {
                    matrix[i][j] = ".";
                }
//                System.out.print(matrix[i][j]);
            }
//            System.out.println();
        }
        return area;
    }

    public static int fib(int n) {
        if (n == 0)
            return 1;
        if (n == 1)
            return 1;
        return fib(n-1) + fib(n-2);
    }
}
