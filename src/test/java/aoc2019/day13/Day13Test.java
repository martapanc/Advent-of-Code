package aoc2019.day13;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static aoc2019.day09.Day9.readInput;
import static aoc2019.day13.Day13.printTileMap;
import static aoc2019.day13.Day13.processInput;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Day13Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day13/input1";
    private static final String INPUT2 = "src/main/java/aoc2019/day13/input2";

    @Test
    public void testReadInput() {
        ArrayList<Long> list = readInput(INPUT1);
        assertEquals(2710, list.size());
    }

    @Test
    public void testProcessInput() {
        System.out.println(processInput(readInput(INPUT1)));
    }

    @Test
    public void testCountTilesOfType() {
        System.out.println(processInput(readInput(INPUT2)));
    }

    @Test
    public void testPrintTileMap() {
        printTileMap(processInput(readInput(INPUT1)));
        printTileMap(processInput(readInput(INPUT2)));
    }
}