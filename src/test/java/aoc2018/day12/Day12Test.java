package aoc2018.day12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

    private final String INPUT1 = "src/main/java/aoc2018/day12/input/input1";
    private final String INPUT1_RULES = "src/main/java/aoc2018/day12/input/input1_rules";
    private final String INPUT2 = "src/main/java/aoc2018/day12/input/input2";
    private final String INPUT2_RULES = "src/main/java/aoc2018/day12/input/input2_rules";

    @Test
    void read_input() {
        System.out.println(Twelve.readInput(INPUT1));
        System.out.println(Twelve.readInput(INPUT2));
    }

    @Test
    void read_input_rules() {
        System.out.println(Twelve.read_input_rules(INPUT1_RULES));
        System.out.println(Twelve.read_input_rules(INPUT2_RULES));
    }

    @Test
    void doThings() {
        assertEquals(325, Twelve.findPotsWithPlantsAfterGenerations(INPUT2, INPUT2_RULES, 20));
        assertEquals(3337, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 20));
        assertEquals(19374, Twelve.findPotsWithPlantsAfterGenerations(INPUT2, INPUT2_RULES, 1000));
    }

    @Test
    void test_findPotsWithPlants() {
        assertEquals(325, Twelve.findPotsWithPlantsAfterGenerations(INPUT2, INPUT2_RULES, 20));

        assertEquals(1374, Twelve.findPotsWithPlantsAfterGenerations(INPUT2, INPUT2_RULES, 100));
        assertEquals(2374, Twelve.findPotsWithPlantsAfterGenerations(INPUT2, INPUT2_RULES, 150));
        assertEquals(3374, Twelve.findPotsWithPlantsAfterGenerations(INPUT2, INPUT2_RULES, 200));
        assertEquals(5374, Twelve.findPotsWithPlantsAfterGenerations(INPUT2, INPUT2_RULES, 300));
        assertEquals(9374, Twelve.findPotsWithPlantsAfterGenerations(INPUT2, INPUT2_RULES, 500));
        assertEquals(19374, Twelve.findPotsWithPlantsAfterGenerations(INPUT2, INPUT2_RULES, 1000));
        assertEquals(99374, Twelve.findPotsWithPlantsAfterGenerations(INPUT2, INPUT2_RULES, 5000));
        assertEquals(999374, Twelve.findPotsWithPlantsAfterGenerations(INPUT2, INPUT2_RULES, 50000));

        assertEquals(3337, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 20));
        assertEquals(8749, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 100));
        assertEquals(13249, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 150));
        assertEquals(17549, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 200));
        assertEquals(17549 + 86, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 201));
        assertEquals(17549 + 86 * 2, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 202));
        assertEquals(26149, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 300));
        assertEquals(43349, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 500));
        assertEquals(77749, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 900));
        assertEquals(86349, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 1000));
        assertEquals(430349, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 5000));
        assertEquals(4300000000349L, Twelve.findPotsWithPlantsAfterGenerations(INPUT1, INPUT1_RULES, 50000000000L));
    }
}