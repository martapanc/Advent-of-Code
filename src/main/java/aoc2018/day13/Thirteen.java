package aoc2018.day13;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Thirteen {

    public static char[][] readInput(String inputFile, int horLength, int verLength) {

        char[][] input = new char[verLength][horLength];

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            int yCoor = 0;

            String line = reader.readLine();

            while (yCoor < verLength) {

                char[] arr = line.toCharArray();
                for (int i = 0; i < input[yCoor].length; i++) {
                    input[yCoor][i] = i < arr.length ? arr[i] : ' ';
                }
                line = reader.readLine();
                yCoor += 1;
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return input;
    }

    static List<Point> findInitialPositions(String inputFile, int horLength, int verLength) {

        List<Point> points = new ArrayList<>();
        char[][] matrix = readInput(inputFile, horLength, verLength);

        for (int y = 0; y < verLength; y++) {
            for (int x = 0; x < horLength; x++) {
                if (matrix[y][x] == '^' || matrix[y][x] == '>' || matrix[y][x] == 'v' || matrix[y][x] == '<') {
                    points.add(new Point(x, y));
                }
            }
        }

        return points;
    }

    public static void printMatrix(char[][] matrix) {
        for (char[] yChars : matrix) {
            for (char xChar : yChars)
                System.out.print(xChar);
            System.out.println();
        }
    }

    static Point findFirstCollisionPoint(char[][] matrix, List<Point> points) {
        // Create initial cursors
        List<Cursor> cursorList = getCursorList(matrix, points);

        // Continue moving cursors until any two Points (coordinate pairs) overlap
        while (areAllUnique(cursorList)) {

            // Sort cursor list by y and then by x (increasing)
            cursorList = cursorList.stream().sorted(Comparator
                    .comparing((Cursor c) -> c.currentPos.y)
                    .thenComparing((Cursor c) -> c.currentPos.x)
            ).collect(Collectors.toList());

            List<Cursor> newCursorList = new ArrayList<>(cursorList);

            // Loop through cursors
            for (Cursor c : cursorList) {
                Cursor newCur = getNextCursor(c, matrix);

                // If moving a cursor results in overlapping another cursor, the latter represents the crash point
                if (newCur.nextTurn == null)
                    return newCur.nextPos;
                newCursorList.remove(c);
                newCursorList.add(newCur);
            }
            cursorList = newCursorList;
        }
        return null;
    }

    static Point findLastRemainingCursor(char[][] matrix, List<Point> points) {
        // Create initial cursors
        List<Cursor> cursorList = getCursorList(matrix, points);

        // Continue moving cursors until they crash two by two, and only one remains on the track
        while (cursorList.size() > 1) {
            cursorList = cursorList.stream().sorted(Comparator
                    .comparing((Cursor c) -> c.currentPos.y)
                    .thenComparing((Cursor c) -> c.currentPos.x)
            ).collect(Collectors.toList());

            List<Cursor> newCursorList = new ArrayList<>(cursorList);
            List<Cursor> collidingCursors = new ArrayList<>();

            for (Cursor c : cursorList) {

                // Skip the computation of any next cursor that has already collided (present in collidingCursors)
                if (collidingCursors.stream().anyMatch(c::equals))
                    continue;

                // If moving a cursor results in overlapping another cursor, the original track cells are restored
                Cursor newCur = getNextCursor(c, matrix);
                if (newCur.nextTurn == null) {
                    Cursor nextC = newCursorList.stream()
                            .filter(k -> k.currentPos.equals(newCur.nextPos))
                            .collect(Collectors.toList()).get(0);
                    matrix[nextC.currentPos.y][nextC.currentPos.x] = nextC.trackCellType;

                    // Store the colliding cursors
                    collidingCursors.add(nextC);
                    collidingCursors.add(c);
                } else {
                    // Else, the cursors' position is stored in a list, which subsequently replaces the cursorList
                    newCursorList.remove(c);
                    newCursorList.add(newCur);
                }
            }

            // Check if colliding cursors are present in the cursorList
            cursorList = newCursorList;
            List<Cursor> list = new ArrayList<>();
            for (Cursor cc : cursorList) {
                if (collidingCursors.stream().map(Cursor::getCurrentPos).anyMatch(c -> c.equals(cc.currentPos))) {
                    list.add(cc);
                }
            }

            // Restore the track cells and remove any colliding cursors from cursorList
            for (Cursor c : list) {
                matrix[c.currentPos.y][c.currentPos.x] = c.trackCellType;
            }
            cursorList.removeAll(list);
        }

        // Return the only remaining cursor's coordinates
        return cursorList.get(0).currentPos;
    }

    private static List<Cursor> getCursorList(char[][] matrix, List<Point> points) {
        List<Cursor> cursorList = new ArrayList<>();
        for (Point p : points) {
            Cursor c1 = new Cursor(Direction.valueOf(matrix[p.y][p.x]), p, Turn.LEFT, '-');
            c1.setTrackCellType((c1.direction == Direction.NORTH || c1.direction == Direction.SOUTH ? '|' : '-'));

            cursorList.add(c1);
        }
        return cursorList;
    }

    static boolean areAllUnique(List<Cursor> list) {
        Set<Point> set = new HashSet<>();
        for (Cursor t : list)
            if (!set.add(t.currentPos))
                return false;
        return true;
    }

    private static Cursor getNextCursor(Cursor c, char[][] matrix) {

        Turn nextTurn = c.nextTurn;

        // Handle cursor turn if the current cell is a +
        if (c.trackCellType == '+') {
            switch (c.nextTurn) {
                case LEFT:
                    switch (c.direction.getDirChar()) {
                        case '^':
                            c.direction = Direction.valueOf('<');
                            break;
                        case '>':
                            c.direction = Direction.valueOf('^');
                            break;
                        case 'v':
                            c.direction = Direction.valueOf('>');
                            break;
                        case '<':
                            c.direction = Direction.valueOf('v');
                            break;
                    }
                    break;

                case RIGHT:
                    switch (c.direction.getDirChar()) {
                        case '^':
                            c.direction = Direction.valueOf('>');
                            break;
                        case '>':
                            c.direction = Direction.valueOf('v');
                            break;
                        case 'v':
                            c.direction = Direction.valueOf('<');
                            break;
                        case '<':
                            c.direction = Direction.valueOf('^');
                            break;
                    }
                    break;
            }

            nextTurn = Turn.valueOf((c.nextTurn.getTurnVal() + 1) % 3);
        }

        // Handle cursor turn if the current cell is a turn ◜◝◞◟
        switch (c.trackCellType) {
            case '◜':
                if (c.direction == Direction.NORTH)
                    c.direction = Direction.valueOf('>');
                else
                    c.direction = Direction.valueOf('v');
                break;
            case '◝':
                if (c.direction == Direction.NORTH)
                    c.direction = Direction.valueOf('<');
                else
                    c.direction = Direction.valueOf('v');
                break;
            case '◞':
                if (c.direction == Direction.SOUTH)
                    c.direction = Direction.valueOf('<');
                else
                    c.direction = Direction.valueOf('^');
                break;
            case '◟':
                if (c.direction == Direction.SOUTH)
                    c.direction = Direction.valueOf('>');
                else
                    c.direction = Direction.valueOf('^');
                break;
        }

        // Get coordinates of next cell
        switch (c.direction) {
            case NORTH:
                c.setNextPos(new Point(c.currentPos.x, c.currentPos.y - 1));
                break;
            case SOUTH:
                c.setNextPos(new Point(c.currentPos.x, c.currentPos.y + 1));
                break;
            case EAST:
                c.setNextPos(new Point(c.currentPos.x + 1, c.currentPos.y));
                break;
            case WEST:
                c.setNextPos(new Point(c.currentPos.x - 1, c.currentPos.y));
                break;
        }

        // Get type of next cell
        char nextCell = matrix[c.nextPos.y][c.nextPos.x];

        // Check if next cell contains another cursor; if so, replace its track cell and return the current cursor
        if (nextCell == '>' || nextCell == 'v' || nextCell == '<' || nextCell == '^') {
            matrix[c.currentPos.y][c.currentPos.x] = c.trackCellType;
            Cursor currC = new Cursor(c.direction, c.currentPos, null, c.trackCellType);
            currC.setNextPos(c.nextPos);
            return currC;
        }

        // Replace track cell, return next cursor
        matrix[c.currentPos.y][c.currentPos.x] = c.trackCellType;
        matrix[c.nextPos.y][c.nextPos.x] = c.direction.getDirChar();
        return new Cursor(c.direction, c.nextPos, nextTurn, nextCell);
    }
}
