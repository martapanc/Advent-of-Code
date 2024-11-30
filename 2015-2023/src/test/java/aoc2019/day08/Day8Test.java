package aoc2019.day08;


import org.junit.jupiter.api.Test;

import static aoc2019.day08.Day8.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day8Test {

    private final static String INPUT1 = "src/main/java/aoc2019/day08/input1";
    private final static String INPUT2 = "src/main/java/aoc2019/day08/input2";
    private final static String INPUT3 = "src/main/java/aoc2019/day08/input3";
    private final static String INPUT4 = "src/main/java/aoc2019/day08/input4";

    @Test
    public void testReadInput() {
        System.out.println(readInput(INPUT1, 25, 6));
        System.out.println(readInput(INPUT2, 3, 2));
        System.out.println(readInput(INPUT3, 2, 2));
    }

    @Test
    public void testGetBestLayer() {
        assertEquals(2480, getBestLayer(readInput(INPUT1, 25, 6)));
        assertEquals(2016, getBestLayer(readInput(INPUT4, 25, 6)));
    }

    @Test
    public void testGetRenderedImage() {
        assertEquals("0110", getRenderedImage(readInput(INPUT3, 2, 2), 2, 2));
        assertEquals("11110100011110010000100100001010001100101000010010001000101011100100001111001000" +
                        "0010010010100001001010000001001001010000100101111000100111001111010010",
                getRenderedImage(readInput(INPUT1, 25, 6), 25, 6));
        assertEquals("10010111100110011110100101001000010100100001010010111100010010000001001001010010" +
                        "0100010000010001001010010100001001010000100101001011110011001111001100",
                getRenderedImage(readInput(INPUT4, 25, 6), 25, 6));
    }
}
