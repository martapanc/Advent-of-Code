package aoc2019.day07;

import org.junit.jupiter.api.Test;

import static aoc2019.day02.Day2.readInput;
import static aoc2019.day07.Day7.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day7Test {

    private static final String PHASE_SETTINGS = "01234";
    private static final String PHASE_SETTINGS_2 = "98765";

    private final static String INPUT1 = "src/main/java/aoc2019/day07/input/input1";
    private final static String INPUT2 = "src/main/java/aoc2019/day07/input/input2";
    private final static String INPUT3 = "src/main/java/aoc2019/day07/input/input3";
    private final static String INPUT4 = "src/main/java/aoc2019/day07/input/input4";
    private final static String INPUT5 = "src/main/java/aoc2019/day07/input/input5";
    private final static String INPUT6 = "src/main/java/aoc2019/day07/input/input6";

    @Test
    public void testReadInput() {
        System.out.println(readInput(INPUT1));
        System.out.println(readInput(INPUT2));
        System.out.println(readInput(INPUT3));
        System.out.println(readInput(INPUT4));
    }

    @Test
    public void testProcessInput() {
        assertEquals(4, processInput(readInput(INPUT1), 4, 0, 0).getOutputValue());
        assertEquals(43, processInput(readInput(INPUT1), 3, 4, 0).getOutputValue());
        assertEquals(432, processInput(readInput(INPUT1), 2, 43, 0).getOutputValue());
        assertEquals(4321, processInput(readInput(INPUT1), 1, 432, 0).getOutputValue());
        assertEquals(43210, processInput(readInput(INPUT1), 0, 4321, 0).getOutputValue());

        assertEquals(5, processInput(readInput(INPUT2), 0, 0, 0).getOutputValue());
        assertEquals(54, processInput(readInput(INPUT2), 1, 5, 0).getOutputValue());
        assertEquals(543, processInput(readInput(INPUT2), 2, 54, 0).getOutputValue());
        assertEquals(5432, processInput(readInput(INPUT2), 3, 543, 0).getOutputValue());
        assertEquals(54321, processInput(readInput(INPUT2), 4, 5432, 0).getOutputValue());

        assertEquals(6, processInput(readInput(INPUT3), 1, 0, 0).getOutputValue());
        assertEquals(65, processInput(readInput(INPUT3), 0, 6, 0).getOutputValue());
        assertEquals(652, processInput(readInput(INPUT3), 4, 65, 0).getOutputValue());
        assertEquals(6521, processInput(readInput(INPUT3), 3, 652, 0).getOutputValue());
        assertEquals(65210, processInput(readInput(INPUT3), 2, 6521, 0).getOutputValue());
    }

    @Test
    public void testSetupAmplifiers() {
        assertEquals(43210, setupAmplifiers(readInput(INPUT1), new int[]{4, 3, 2, 1, 0}));
        assertEquals(54321, setupAmplifiers(readInput(INPUT2), new int[]{0, 1, 2, 3, 4}));
        assertEquals(12345, setupAmplifiers(readInput(INPUT2), new int[]{4, 3, 2, 1, 0}));
        assertEquals(65210, setupAmplifiers(readInput(INPUT3), new int[]{1, 0, 4, 3, 2}));
        assertEquals(1265, setupAmplifiers(readInput(INPUT3), new int[]{2, 3, 4, 1, 0}));
    }

    @Test
    public void testGeneratePermutations() {
        assertEquals(1, PermutationUtil.generatePermutations("0").size());
        assertEquals(2, PermutationUtil.generatePermutations("01").size());
        assertEquals(6, PermutationUtil.generatePermutations("012").size());
        assertEquals(24, PermutationUtil.generatePermutations("1023").size());
        assertEquals(120, PermutationUtil.generatePermutations("12345").size());
        assertEquals(720, PermutationUtil.generatePermutations("123456").size());

        PermutationUtil.generatePermutations("0123").forEach(System.out::println);
    }

    @Test
    public void testFindBestResult() {
        assertEquals(43210, findBestResult(readInput(INPUT1), PHASE_SETTINGS, false));
        assertEquals(54321, findBestResult(readInput(INPUT2), PHASE_SETTINGS, false));
        assertEquals(54321, findBestResult(readInput(INPUT2), PHASE_SETTINGS, false));
        assertEquals(65210, findBestResult(readInput(INPUT3), PHASE_SETTINGS, false));
        assertEquals(67023, findBestResult(readInput(INPUT4), PHASE_SETTINGS, false));
    }

    @Test
    public void testSetupLoopingAmplifiers() {
        assertEquals(139629729, setupLoopingAmplifiers(readInput(INPUT5), new int[]{9, 8, 7, 6, 5}));
        assertEquals(18216, setupLoopingAmplifiers(readInput(INPUT6), new int[]{9, 7, 8, 5, 6}));
        assertEquals(3710683, setupLoopingAmplifiers(readInput(INPUT4), new int[]{9, 8, 7, 6, 5}));
    }

    @Test
    public void testFindBestResultWithLoop() {
        assertEquals(139629729, findBestResult(readInput(INPUT5), PHASE_SETTINGS_2, true));
        assertEquals(18216, findBestResult(readInput(INPUT6), PHASE_SETTINGS_2, true));
        assertEquals(7818398, findBestResult(readInput(INPUT4), PHASE_SETTINGS_2, true));
    }
}