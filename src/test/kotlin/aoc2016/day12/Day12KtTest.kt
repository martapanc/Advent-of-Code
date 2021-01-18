package aoc2016.day12

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day12KtTest {

    private val testInput = "src/main/kotlin/aoc2016/day12/testInput"
    private val input = "src/main/kotlin/aoc2016/day12/input"

    @Test
    fun testReadInputAndRun() {
        assertEquals(42, readInputAndRun(testInput))
        assertEquals(318083, readInputAndRun(input))
        assertEquals(9227737, readInputAndRun(input, true))
    }
}