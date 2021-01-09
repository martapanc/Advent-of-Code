package aoc2019.day11;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static aoc2019.day09.Day9Kt.readInput;
import static aoc2019.day11.Day11.processInput;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

    private static final String INPUT1 = "src/main/java/aoc2019/day11/input1";

    @Test
    public void testReadInput() {
        ArrayList<Long> list = readInput(INPUT1);
        assertEquals(648, list.size());
        System.out.println(list);
    }

    @Test
    public void testProcessInput() {
        assertEquals(1951, processInput(readInput(INPUT1), 0));
        assertEquals(250, processInput(readInput(INPUT1), 1));
    }

    //
    //   █  █ █  █   ██ ███   ██  █  █  ██  ███
    //   █  █ █ █     █ █  █ █  █ █  █ █  █ █  █
    //   ████ ██      █ ███  █  █ ████ █    █  █
    //   █  █ █ █     █ █  █ ████ █  █ █    ███
    //   █  █ █ █  █  █ █  █ █  █ █  █ █  █ █ █
    //   █  █ █  █  ██  ███  █  █ █  █  ██  █  █
    //
}
