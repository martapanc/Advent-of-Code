package aoc2018.day15;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static aoc2018.day13.Thirteen.readInput;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FifteenTest {

    private final String input1 = "src/main/java/aoc2018/day15/input/input1";
    private final String input2 = "src/main/java/aoc2018/day15/input/input2";
    private final String input_flood = "src/main/java/aoc2018/day15/input/input_flood";
    private final String input_attackFirst = "src/main/java/aoc2018/day15/input/input_attackFirst";
    private final String input_bfs = "src/main/java/aoc2018/day15/input/input_bfs";

    @Test
    public void testReadInputAndPrintMatrix(){
//        printMatrix(readInput(input1, 9,9));
    }

    @Test
    public void getInitialUnitPositions() {
        List<Unit> unitList = new ArrayList<>();
        unitList.add(new Goblin(new Point(1, 1)));
        unitList.add(new Goblin(new Point(4, 1)));
        unitList.add(new Goblin(new Point(7, 1)));
        unitList.add(new Goblin(new Point(1, 4)));
        unitList.add(new Elf(new Point(4, 4)));
        unitList.add(new Goblin(new Point(7, 4)));
        unitList.add(new Goblin(new Point(1, 7)));
        unitList.add(new Goblin(new Point(4, 7)));
        unitList.add(new Goblin(new Point(7, 7)));

        List<Unit> actual = Utils.getInitialUnitPositions(readInput(input1, 9,9));
        IntStream.range(0, unitList.size()).forEach(i -> assertEquals(unitList.get(i).position, actual.get(i).position));
    }

    @Test
    public void testFindPossibleTargets() {
        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(4, 3));
        pointList.add(new Point(4, 5));
        pointList.add(new Point(5, 4));
        pointList.add(new Point(3, 4));

        assertEquals(pointList, Fifteen.findPossibleTargets(
                new Goblin(new Point(1,1)),
                Utils.getInitialUnitPositions(readInput(input1, 9, 9)),
                readInput(input1, 9, 9)
        ));
    }

    @Test
    public void testGetAccessiblePoints() {
        List<Point> accessiblePoints = new ArrayList<>();
        accessiblePoints.add(new Point(2,1));
        accessiblePoints.add(new Point(3,1));
        accessiblePoints.add(new Point(1,2));
        accessiblePoints.add(new Point(2,2));
        accessiblePoints.add(new Point(3,2));
        accessiblePoints.add(new Point(1,3));
        accessiblePoints.add(new Point(2,3));
        accessiblePoints.add(new Point(3,3));

//        assertThat("List equality without order",
//                Fifteen.getAllAccessibleTargets(new Goblin(new Point(1,1)), readInput(input_flood, 9,9)),
//                containsInAnyOrder(accessiblePoints.toArray()));
//                containsInAnyOrder(accessiblePoints.toArray());
    }

    @Test
    public void testFindReachableTargets(){

        List<Point> pointList = new ArrayList<>();
        pointList.add(new Point(1, 5));
        pointList.add(new Point(7, 5));
        pointList.add(new Point(1, 6));
        pointList.add(new Point(2, 7));
        pointList.add(new Point(7, 6));
        pointList.add(new Point(6, 7));

        assertEquals(pointList, Fifteen.findReachableTargets(
                new Elf(new Point(4,4)),
                Utils.getInitialUnitPositions(readInput(input_flood, 9, 9)),
                readInput(input_flood, 9, 9)));

        assertEquals(new ArrayList<>(), Fifteen.findReachableTargets(
                new Goblin(new Point(1,1)),
                Utils.getInitialUnitPositions(readInput(input_flood, 9, 9)),
                readInput(input_flood, 9, 9)));
    }

    @Test
    public void testGetClosestTargetInReadingOrder() {
        Unit e1 = new Elf(new Point(4,4));
        Unit e2 = new Elf(new Point(4,7));
        char[][] matrix1 = readInput(input_flood, 9, 9);

        assertEquals(new Point(1, 5), Fifteen.getClosestTargetInReadingOrder(e1,
                Fifteen.findReachableTargets(e1,
                        Utils.getInitialUnitPositions(matrix1),
                        matrix1),
                matrix1)
        );
        assertEquals(new Point(2, 7), Fifteen.getClosestTargetInReadingOrder(e2,
                Fifteen.findReachableTargets(e2,
                        Utils.getInitialUnitPositions(matrix1),
                        matrix1),
                matrix1)
        );

        Unit e3 = new Elf(new Point(4,4));
        Unit g1 = new Goblin(new Point(1,1));
        char[][] matrix2 = readInput(input1, 9, 9);

        assertEquals(new Point(4, 2), Fifteen.getClosestTargetInReadingOrder(e3,
                Fifteen.findReachableTargets(e3,
                        Utils.getInitialUnitPositions(matrix2),
                        matrix2),
                matrix2)
        );
        assertEquals(new Point(4,3), Fifteen.getClosestTargetInReadingOrder(g1,
                Fifteen.findReachableTargets(g1,
                        Utils.getInitialUnitPositions(matrix2),
                        matrix2),
                matrix2)
        );
    }

    @Test
    public void testGetNextPositionInReadingOrder() {
        Unit e1 = new Elf(new Point(4,4));
        Unit e2 = new Elf(new Point(4,7));
        assertEquals(new Point(4,5), Fifteen.getNextPositionInReadingOrder(e1, new Point(1, 4), readInput(input_flood, 9,9)));
        assertEquals(new Point(4,5), Fifteen.getNextPositionInReadingOrder(e1, new Point(7, 4), readInput(input_flood, 9,9)));
        assertEquals(new Point(3,7), Fifteen.getNextPositionInReadingOrder(e2, new Point(2, 7), readInput(input_flood, 9,9)));
        assertEquals(new Point(4,6), Fifteen.getNextPositionInReadingOrder(e2, new Point(4, 5), readInput(input_flood, 9,9)));
        assertEquals(new Point(5,7), Fifteen.getNextPositionInReadingOrder(e2, new Point(6, 7), readInput(input_flood, 9,9)));
        assertEquals(new Point(4,6), Fifteen.getNextPositionInReadingOrder(e2, new Point(1, 6), readInput(input_flood, 9,9)));
        assertEquals(new Point(4,6), Fifteen.getNextPositionInReadingOrder(e2, new Point(7, 6), readInput(input_flood, 9,9)));

        Unit g1 = new Goblin(new Point(1, 7));
        assertEquals(new Point(2,7), Fifteen.getNextPositionInReadingOrder(g1, new Point(4, 7), readInput(input_flood, 9,9)));
        assertEquals(new Point(1,6), Fifteen.getNextPositionInReadingOrder(g1, new Point(5, 6), readInput(input_flood, 9,9)));
    }

    @Test
    public void canUnitAttackDirectly() {
        Unit e1 = new Elf(new Point(4,3));
        Unit g1 = new Goblin(new Point(4,2));
        assertTrue(Fifteen.canUnitAttack(e1, Utils.getInitialUnitPositions(readInput(input_attackFirst, 9,9))));
        assertTrue(Fifteen.canUnitAttack(g1, Utils.getInitialUnitPositions(readInput(input_attackFirst, 9,9))));
    }

    @Test
    public void testBFS() {
        Unit g1 = new Goblin(new Point(5,1));
        Unit g2 = new Goblin(new Point(5,3));
        Unit g3 = new Goblin(new Point(2,2));
        Unit e1 = new Elf(new Point(5,4));

        char[][] inputMatrix = readInput(input_bfs, 7, 7);

        assertEquals(7, Utils.findMinDistanceWithBFS(g1, e1.position, inputMatrix));
        assertEquals(7, Utils.findMinDistanceWithBFS(e1, g1.position, inputMatrix));
        assertEquals(7, Utils.findMinDistanceWithBFS(g2, e1.position, inputMatrix));
        assertEquals(7, Utils.findMinDistanceWithBFS(e1, g2.position, inputMatrix));
        assertEquals(5, Utils.findMinDistanceWithBFS(g3, e1.position, inputMatrix));
        assertEquals(5, Utils.findMinDistanceWithBFS(e1, g3.position, inputMatrix));

        assertEquals(6, Utils.findMinDistanceWithBFS(g1, new Point(5,5), inputMatrix));
        assertEquals(6, Utils.findMinDistanceWithBFS(g2, new Point(5,5), inputMatrix));
        assertEquals(4, Utils.findMinDistanceWithBFS(g3, new Point(5,5), inputMatrix));

        assertEquals(2, Utils.findMinDistanceWithBFS(g3, new Point(2,4), inputMatrix));

        Unit g4 = new Goblin(new Point(11, 11));
        Unit e2 = new Elf(new Point(19, 26));

        assertEquals(27, Utils.findMinDistanceWithBFS(g4, e2.position, readInput(input2, 32,32)));
        assertEquals(27, Utils.findMinDistanceWithBFS(e2, g4.position, readInput(input2, 32,32)));
    }

    @Test
    public void testGetDistance() {
        char[][] matrix = readInput(input_bfs, 7, 7);

        assertEquals(9, Utils.getDistanceWithBFS(new Point(5,1), new Point(5,4), matrix));
        assertEquals(9, Utils.getDistanceWithBFS(new Point(5,4), new Point(5,1), matrix));
        assertEquals(5, Utils.getDistanceWithBFS(new Point(2,1), new Point(1,5), matrix));
        assertEquals(4, Utils.getDistanceWithBFS(new Point(1,1), new Point(1,5), matrix));
    }
}
