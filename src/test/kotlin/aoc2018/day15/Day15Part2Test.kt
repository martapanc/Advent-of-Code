package aoc2018.day15

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine


internal class Day15Part2Test {

    private val input = readInputLineByLine("src/main/java/aoc2018/day15/input/input2")

    @Test
    fun testRunPart2() {
        assertEquals(61750, Day15Part2(input).playGamePart2())
    }
}