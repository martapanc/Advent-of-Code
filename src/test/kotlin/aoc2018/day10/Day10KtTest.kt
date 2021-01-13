package aoc2018.day10

import org.junit.jupiter.api.Test

class Day10KtTest {
    private val input1 = "src/main/kotlin/aoc2018/day10/input/input1"
    private val input2 = "src/main/kotlin/aoc2018/day10/input/input2"
    private val input3 = "src/main/kotlin/aoc2018/day10/input/input3"

    @Test
    fun test_compute_coordinates() {
        computeCloseCoordinates(readInput(input1), 0)
        computeCloseCoordinates(readInput(input3), 0)
    }

    @Test
    fun test_compute() {
        (0..4).forEach { s -> compute(readInput(input2), s) }
    }

    @Test
    fun test_compute_2() {
        (10515..10525).forEach { s -> compute(readInput(input1), s) }
        compute(readInput(input1), 10519)
    }

    @Test
    fun test_compute_3() {
        (10032..10042).forEach { s -> compute(readInput(input3), s) }
        compute(readInput(input3), 10036)
    }
}