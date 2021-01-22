package aoc2015.day08

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day8KtTest {

    private val input = "src/main/kotlin/aoc2015/day08/input"
    private val testInput = "src/main/kotlin/aoc2015/day08/testInput"

    @Test
    fun testReadInputToListAndRun() {
        assertEquals(12, readInputToListAndRun(testInput))
        assertEquals(1342, readInputToListAndRun(input))
        assertEquals(19, partTwo(testInput))
        assertEquals(2074, partTwo(input))
    }
}