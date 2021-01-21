package aoc2016.day10

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day10KtTest {

    private val input = "src/main/kotlin/aoc2016/day10/input"
    private val testInput = "src/main/kotlin/aoc2016/day10/testInput"

    @Test
    fun testReadInputAndSort() {
        assertEquals(2, readInputAndRun(testInput, setOf(2, 5)))
        assertEquals(181, readInputAndRun(input))
        assertEquals(12567, readInputAndRunPart2(input))
    }
}