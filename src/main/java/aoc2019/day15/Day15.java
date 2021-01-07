package aoc2019.day15;

import aoc2019.day09.Output;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.util.List;
import java.util.*;

import static aoc2019.day09.Day9.processParameterMode;


public class Day15 {

    static Map<Point, Cell> processInput(ArrayList<Long> numbers) {
        int index = 0;
        int relativeBase = 0;

        Map<Point, Cell> map = new HashMap<>();
        Point currentPoint = new Point(0, 0);
        map.put(currentPoint, new Cell(CellType.PATH));
        StringBuilder outputBuilder = new StringBuilder();

        int[] dirs = new int[]{3, 1, 4, 2}; //west, north, east, south
        long k = 110000;
        Direction direction = Direction.WEST;

        loop:
        while (k-- > 0) {
            int opCode = Math.toIntExact(numbers.get(index));

            Output output = processParameterMode(numbers, index, opCode, direction.getDirectionId(), relativeBase);
            outputBuilder.append(output.getCode());

            if (output.getRelativeBase() != 0) {
                relativeBase = output.getRelativeBase();
            }

            if (outputBuilder.length() == 1) {
                Point nextPoint = getNextCellCoordinates(currentPoint, direction);

                switch (outputBuilder.toString()) {
                    case "0":
                        int rotatingId = (ArrayUtils.indexOf(dirs, direction.getDirectionId()) + 1) % 4; // index of current direction id in dirs + 1
                        direction = Direction.getValueOf(dirs[rotatingId]);
                        if (map.get(nextPoint) == null) {
                            map.put(nextPoint, new Cell(CellType.WALL));
                        }
                        break;
                    case "2":
                    case "1":
                        direction = getRelativeWestOfCurrentDirection(direction);
                        if (map.get(nextPoint) == null) {
                            map.put(nextPoint, new Cell(outputBuilder.toString().equals("1") ? CellType.PATH : CellType.OXIGEN_THING));
                        }
                        currentPoint = new Point(nextPoint.x, nextPoint.y);
                        break;
                    default:
                        System.out.println("Unexpected output");
                        break loop;
                }
                outputBuilder = new StringBuilder();
            }
            index += output.getIndex();
        }
        printCellMap(map);

        return map;
    }

    static int findStepsToOxigenMachine(Map<Point, Cell> maze) {
        Point currentPoint = new Point(0, 0);
        Set<Point> visited = new HashSet<>();
        Point fork = null;
        int count = 0;
        int countAtFork = -1;

        while (!maze.get(currentPoint).getType().equals(CellType.OXIGEN_THING)) {
            visited.add(currentPoint);
            List<Point> adjacentPoints = getAdjacentPoints(currentPoint);
            List<Point> possiblePaths = new ArrayList<>();
            for (Point ap : adjacentPoints) {
                if (!maze.get(ap).getType().equals(CellType.WALL) && !visited.contains(ap)) {
                    possiblePaths.add(ap);
                }
            }

            switch (possiblePaths.size()) {
                case 1:
                    count++;
                    currentPoint = possiblePaths.get(0);
                    break;
                case 2:
                    count++;
                    fork = currentPoint;
                    countAtFork = count;
                    currentPoint = possiblePaths.get(0);
                    break;
                case 0:
                    currentPoint = fork;
                    count = countAtFork;
            }
        }
        return count - 1;
    }

    static int floodWithOxigen(Map<Point, Cell> maze) {
        Point oxigenMachine = new Point(-16, 14);
        Set<Point> edge = new HashSet<>();
        Set<Point> visited = new HashSet<>();
        edge.add(oxigenMachine);

        List<Point> possiblePaths = new ArrayList<>();
        int count = 0;
        do {
            count++;
            List<Point> adjacentPoints = getAdjacentPoints(oxigenMachine);
            for (Point ap : adjacentPoints) {
                if (!maze.get(ap).getType().equals(CellType.WALL) && !edge.contains(ap)) {
                    possiblePaths.add(ap);
                }
            }
            edge = new HashSet<>();
        } while (edge.size() != maze.size());

        return count;
    }

    static List<Point> getAdjacentPoints(Point p) {
        List<Point> adjacentPoints = new ArrayList<>();
        adjacentPoints.add(new Point(p.x - 1, p.y));
        adjacentPoints.add(new Point(p.x, p.y + 1));
        adjacentPoints.add(new Point(p.x + 1, p.y));
        adjacentPoints.add(new Point(p.x, p.y - 1));
        return adjacentPoints;
    }

    static Direction getRelativeWestOfCurrentDirection(Direction currentDirection) {
        switch (currentDirection) {
            case NORTH:
                return Direction.WEST;
            case EAST:
                return Direction.NORTH;
            case SOUTH:
                return Direction.EAST;
            default:
                return Direction.SOUTH;
        }
    }

    private static Point getNextCellCoordinates(Point currentCell, Direction direction) {
        switch (direction) {
            case NORTH:
                return new Point(currentCell.x, currentCell.y + 1);
            case EAST:
                return new Point(currentCell.x + 1, currentCell.y);
            case SOUTH:
                return new Point(currentCell.x, currentCell.y - 1);
            case WEST:
                return new Point(currentCell.x - 1, currentCell.y);
        }

        return null;
    }

    static void printCellMap(Map<Point, Cell> cellMap) {
        int minX = cellMap.keySet().stream().mapToInt(p -> p.x).min().orElse(-1);
        int maxX = cellMap.keySet().stream().mapToInt(p -> p.x).max().orElse(-1);
        int minY = cellMap.keySet().stream().mapToInt(p -> p.y).min().orElse(-1);
        int maxY = cellMap.keySet().stream().mapToInt(p -> p.y).max().orElse(-1);

        for (int y = maxY; y >= minY; y--) {
            for (int x = minX; x <= maxX; x++) {
                Point key = new Point(x, y);
                if (cellMap.containsKey(key)) {
                    if (new Point(0, 0).equals(key)) {
                        System.out.print(0);
                    } else {
                        System.out.print(cellMap.get(key).getType().getSymbol());
                    }
                } else {
                    System.out.print("░");
                }
            }
            System.out.println();
        }
    }
}

