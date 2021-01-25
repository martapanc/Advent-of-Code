package aoc2018.day15;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static aoc2018.day13.Day13.printMatrix;

public class Utils {

    public static List<Unit> getInitialUnitPositions(char[][] matrix){
        List<String> elves = elfNameInitialiser();
        List<String> goblins = goblinNameInitialiser();
        int elfCount = 0;
        int goblinCount = 0;

        List<Unit> unitList = new ArrayList<>();
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                if (matrix[y][x] == 'G') {
                    unitList.add(new Goblin(new Point(x, y), goblins.get(goblinCount)));
                    goblinCount += 1;
                }
                if (matrix[y][x] == 'E') {
                    unitList.add(new Elf(new Point(x, y), elves.get(elfCount)));
                    elfCount += 1;
                }
            }
        }
//        System.out.println("*** Initial positions ***");
//        Thirteen.printMatrix(matrix);
//        System.out.println(unitList);
        return unitList;
    }

    public static List<String> elfNameInitialiser() {
        List<String> elfList = Arrays.asList("Eustachio", "Eusebio", "Ermenegildo", "Ermengarda", "Ernesto", "Edgardo", "Ermete",
                "Eleuterio", "Egidio", "Elmerico", "Ermanno", "Eufronio", "Evandro", "Ezechiele", "Edmondo", "Eliodoro", "Erasmo",
                "Ercolino", "Efesto", "Eraclide", "Ennio", "Eufrasia", "Egizia", "Enea", "Enzo", "Elmo", "Elpidio", "Esaù");
//        Collections.shuffle(elfList);
        return elfList;
    }

    public static List<String> goblinNameInitialiser() {
        List<String> goblinList = Arrays.asList("Gioacchino", "Girolamo", "Geronimo", "Gandolfo", "Giacobbe", "Gollum", "Gennaro", "Giuditta",
                "Gianbattista", "Gianvincenzo", "Guendalina", "Goffredo", "Giustiniano", "Gregorio", "Gustavo", "Gianfilippo", "Gedeone",
                "Guidalberto", "Giove", "Giosuè", "Giasone", "Giampietro", "Graziella", "Gertrude", "Genoveffa", "Germano", "Giotto");
//        Collections.shuffle(goblinList);
        return goblinList;
    }

    public static Point[] getAdjacentPoints(Point currPos) {
        return new Point[] {
                new Point(currPos.x, currPos.y - 1),
                new Point(currPos.x, currPos.y + 1),
                new Point(currPos.x + 1, currPos.y),
                new Point(currPos.x - 1, currPos.y)
        };
    }

    public static Point[] getFreeAdjacentPoints(Point currPos, char[][] matrix) {
        List<Point> freePoints;
        Point[] adjacentPoints = new Point[]{
                new Point(currPos.x, currPos.y - 1),
                new Point(currPos.x, currPos.y + 1),
                new Point(currPos.x + 1, currPos.y),
                new Point(currPos.x - 1, currPos.y)
        };
        freePoints = Arrays.stream(adjacentPoints).filter(p -> matrix[p.y][p.x] == '.').collect(Collectors.toList());
        return freePoints.stream().toArray(Point[]::new);
    }

    public static Comparator<Point> pointComparator() {
        //Sort points by increasing distance from the top-left corner
        Point p = new Point(0, -1000);
        final Point finalP = new Point(p.x, p.y);
        return (p0, p1) -> {
            double ds0 = p0.distanceSq(finalP);
            double ds1 = p1.distanceSq(finalP);
            return Double.compare(ds0, ds1);
        };
    }

    public static int findMinDistanceWithBFS(Unit playingUnit, Point targetCell, char[][] inputMatrix) {
        char[][] matrix = new char[inputMatrix[0].length][inputMatrix.length];
        IntStream.range(0, matrix.length).forEach(i -> System.arraycopy(inputMatrix[i], 0, matrix[i], 0, matrix[i].length));
        int count = 0;
        int edge = '0';
        boolean reachedStart = false;
        Point[] targetAdjacentPoints = getAdjacentPoints(targetCell);
        List<Point> edgeList = Arrays.stream(targetAdjacentPoints).filter(p -> matrix[p.y][p.x] == '.').collect(Collectors.toList());
        List<Point> visitedList;

        while (!reachedStart) {
            visitedList = new ArrayList<>();
            for (Point p : edgeList) {
                matrix[p.y][p.x] = (char) edge;
                visitedList.add(p);
            }
            printMatrix(matrix);
            edgeList = new ArrayList<>();

            for (Point v : visitedList) {
                targetAdjacentPoints = getAdjacentPoints(v);
                for (Point a : targetAdjacentPoints) {
                    if (!edgeList.contains(a) && matrix[a.y][a.x] == '.') {
                        edgeList.add(a);
                    }

                    if (matrix[a.y][a.x] == playingUnit.getIdChar() && a.equals(playingUnit.position)) {
                        reachedStart = true;
                    }
                }
            }
            if (!reachedStart) {
                count += 1;
                edge += 1;
                if (edge == 58) {
                    edge = '0';
                }
            }
        }
        return count;
    }

    public static int getDistanceWithBFS(Point sourceCell, Point targetCell, char[][] inputMatrix){

        char[][] matrix = new char[inputMatrix[0].length][inputMatrix.length];
        IntStream.range(0, matrix.length).forEach(i -> System.arraycopy(inputMatrix[i], 0, matrix[i], 0, matrix[i].length));

        int count = 1;
        int edge = '1';
        Point[] targetAdjacentPoints = getAdjacentPoints(targetCell);
        List<Point> edgeList = Arrays.stream(targetAdjacentPoints).filter(p -> matrix[p.y][p.x] == '.').collect(Collectors.toList());
        List<Point> visitedList;

        matrix[targetCell.y][targetCell.x] = 'T';
        matrix[sourceCell.y][sourceCell.x] = 'S';
        int matrixCheckSum = calcMatrixChecksum(matrix);
//        Thirteen.printMatrix(matrix);

        if (sourceCell.getLocation().equals(targetCell.getLocation()))
            return 0;
        if (areCellsAdiacents(sourceCell, targetCell))
            return 1;

        boolean reachedSource = false;

        while (!reachedSource) {
            visitedList = new ArrayList<>();
            for (Point p : edgeList) {
                matrix[p.y][p.x] = (char) edge;
                visitedList.add(p);
            }
//            Thirteen.printMatrix(matrix);
            edgeList = new ArrayList<>();

            for (Point v : visitedList) {
                targetAdjacentPoints = getAdjacentPoints(v);
                for (Point a : targetAdjacentPoints) {
                    if (!edgeList.contains(a) && matrix[a.y][a.x] == '.') {
                        edgeList.add(a);
                    }
                    if (a.equals(sourceCell)) {
                        reachedSource = true;
                    }
                }
            }
            if (!reachedSource) {
                count += 1;
                edge += 1;
                if (edge == 58) {
                    edge = '0';
                }
            }
            if (matrixCheckSum == calcMatrixChecksum(matrix)) {
                return 'e';
            } else {
                matrixCheckSum = calcMatrixChecksum(matrix);
            }
        }
        return count+1;
    }

    private static int calcMatrixChecksum(char[][] matrix) {
        int matrixCheckSum = 0;
        for (char[] y : matrix) {
            for (char x : y) {
                matrixCheckSum += x;
            }
        }
        return matrixCheckSum;
    }

    private static boolean areCellsAdiacents(Point one, Point two) {
        return (one.x == two.x && (one.y == two.y + 1 || one.y == two.y -1) ||
                one.y == two.y && (one.x == two.x + 1 || one.x == two.x -1));
    }
}
