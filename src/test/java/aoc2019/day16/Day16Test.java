package aoc2019.day16;


import org.junit.jupiter.api.Test;

import static aoc2019.day16.Day16.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day16/input1";
    private static final String INPUT2 = "12345678";
    private static final String INPUT3 = "48226158";
    private static final String INPUT4 = "34040438";
    private static final String INPUT5 = "03415518";
    private static final String INPUT6 = "01029498";
    private static final String INPUT7 = "80871224585914546619083218645595";


    @Test
    public void testReadInput() {
        System.out.println(readInput(INPUT1));
        System.out.println(readStringInput(INPUT2));
    }

    @Test
    public void testComputeNthDigit() {
        assertEquals(4, computeNthDigit(readStringInput(INPUT2), 1));
        assertEquals(8, computeNthDigit(readStringInput(INPUT2), 2));
        assertEquals(2, computeNthDigit(readStringInput(INPUT2), 3));
        assertEquals(2, computeNthDigit(readStringInput(INPUT2), 4));
        assertEquals(6, computeNthDigit(readStringInput(INPUT2), 5));
        assertEquals(1, computeNthDigit(readStringInput(INPUT2), 6));
        assertEquals(5, computeNthDigit(readStringInput(INPUT2), 7));
        assertEquals(8, computeNthDigit(readStringInput(INPUT2), 8));
    }

    @Test
    public void testComputePhase() {
        assertEquals(INPUT3, computePhase(readStringInput(INPUT2), 1, true));

        assertEquals(INPUT4, computePhase(readStringInput(INPUT3), 1, true));
        assertEquals(INPUT4, computePhase(readStringInput(INPUT2), 2, true));
        assertEquals(INPUT5, computePhase(readStringInput(INPUT2), 3, true));
        assertEquals(INPUT6, computePhase(readStringInput(INPUT2), 4, true));

        assertEquals("24176176", computePhase(readStringInput(INPUT7), 100, true));
        assertEquals("77038830", computePhase(readInput(INPUT1), 100, true));
//        assertEquals("77038830", computePhase(tenThousandTimesList(readInput(INPUT1)), 1, true));

        computePhase(readStringInput(INPUT7), 100, false);
        computePhase(readStringInput(INPUT2), 100, false);
    }

    @Test
    public void testCreateTenThousandTimesList() {
        assertEquals(80000, tenThousandTimesList(readStringInput("12345678")).size());
        assertEquals(320000, tenThousandTimesList(readStringInput("03036732577212944063491565474664")).size());
        assertEquals(6500000, tenThousandTimesList(readInput(INPUT1)).size());
    }
}
