package aoc2019.day14;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static aoc2019.day14.Day14.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day14/input/input1";
    private static final String INPUT2 = "src/main/java/aoc2019/day14/input/input2";
    private static final String INPUT3 = "src/main/java/aoc2019/day14/input/input3";
    private static final String INPUT4 = "src/main/java/aoc2019/day14/input/input4";
    private static final String INPUT5 = "src/main/java/aoc2019/day14/input/input5";
    private static final String INPUT6 = "src/main/java/aoc2019/day14/input/input6";

    @Test
    public void testReadInput() {
        Map<Chemical, List<Chemical>> map = readInput(INPUT1);
        assertEquals(6, map.size());

        map = readInput(INPUT2);
        assertEquals(7, map.size());

        map = readInput(INPUT6);
        assertEquals(56, map.size());
    }

    @Test
    public void testComputeChemicals() {
        assertEquals(31, computeChemicals(readInput(INPUT1)));
        assertEquals(165, computeChemicals(readInput(INPUT2)));
        assertEquals(13312, computeChemicals(readInput(INPUT3)));
        assertEquals(180697, computeChemicals(readInput(INPUT4)));
        assertEquals(2210736, computeChemicals(readInput(INPUT5)));
        assertEquals(365768, computeChemicals(readInput(INPUT6)));
    }

    @Test
    public void testComputeMaxFuel() {
//        assertEquals(82892753, computeMaxFuel(readInput(INPUT3), 1000000000000L));
//        assertEquals(5586022, computeMaxFuel(readInput(INPUT4), 1000000000000L));
        assertEquals(460664, computeMaxFuel(readInput(INPUT5), 1000000000000L));
        assertEquals(4606, computeMaxFuel(readInput(INPUT5), 10000000000L));
//        assertEquals(3756877, computeMaxFuel(readInput(INPUT6), 1000000000000L));
    }
}
