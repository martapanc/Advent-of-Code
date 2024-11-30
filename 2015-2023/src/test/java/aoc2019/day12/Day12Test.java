package aoc2019.day12;


import org.junit.jupiter.api.Test;

import static aoc2019.day12.Day12.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day12/input/input1";
    private static final String INPUT2 = "src/main/java/aoc2019/day12/input/input2";
    private static final String INPUT3 = "src/main/java/aoc2019/day12/input/input3";

    @Test
    public void testReadInput() {
        System.out.println(readInput(INPUT1));
        System.out.println(readInput(INPUT2));
    }

    @Test
    public void testComputeMovements() {
        computeMovements(readInput(INPUT2), 10);
        computeMovements(readInput(INPUT3), 100);
        computeMovements(readInput(INPUT2), 2772);
        computeMovements(readInput(INPUT2), 5544);
//        computeMovements(readInput(INPUT3), 4686774924L); // Should take ~ 40 mins - run at your own risk
    }

    @Test
    public void testComputeTotalEnergy() {
        assertEquals(179, computeTotalEnergyAfterNSteps(readInput(INPUT2), 10));
        assertEquals(1940, computeTotalEnergyAfterNSteps(readInput(INPUT3), 100));
        assertEquals(9958, computeTotalEnergyAfterNSteps(readInput(INPUT1), 1000));
    }

    @Test
    public void testFindAxisCyclePeriod() {
        long xAxisCyclePeriod = findXAxisCyclePeriod(readInput(INPUT2));
        long yAxisCyclePeriod = findYAxisCyclePeriod(readInput(INPUT2));
        long zAxisCyclePeriod = findZAxisCyclePeriod(readInput(INPUT2));

        assertEquals(18, xAxisCyclePeriod);
        assertEquals(28, yAxisCyclePeriod);
        assertEquals(44, zAxisCyclePeriod);

        assertEquals(2772, MathUtil.lcm(new long[]{xAxisCyclePeriod, yAxisCyclePeriod, zAxisCyclePeriod}));

        xAxisCyclePeriod = findXAxisCyclePeriod(readInput(INPUT3));
        yAxisCyclePeriod = findYAxisCyclePeriod(readInput(INPUT3));
        zAxisCyclePeriod = findZAxisCyclePeriod(readInput(INPUT3));
        assertEquals(2028, xAxisCyclePeriod);
        assertEquals(5898, yAxisCyclePeriod);
        assertEquals(4702, zAxisCyclePeriod);

        assertEquals(4686774924L, MathUtil.lcm(new long[]{xAxisCyclePeriod, yAxisCyclePeriod, zAxisCyclePeriod}));

        xAxisCyclePeriod = findXAxisCyclePeriod(readInput(INPUT1));
        yAxisCyclePeriod = findYAxisCyclePeriod(readInput(INPUT1));
        zAxisCyclePeriod = findZAxisCyclePeriod(readInput(INPUT1));
        assertEquals(28482, xAxisCyclePeriod);
        assertEquals(231614, yAxisCyclePeriod);
        assertEquals(193052, zAxisCyclePeriod);

        assertEquals(318382803780324L, MathUtil.lcm(new long[]{xAxisCyclePeriod, yAxisCyclePeriod, zAxisCyclePeriod}));
    }
}