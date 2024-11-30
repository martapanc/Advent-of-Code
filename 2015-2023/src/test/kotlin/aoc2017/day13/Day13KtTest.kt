package aoc2017.day13

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day13KtTest {

    private val input = readInputToMap("src/main/kotlin/aoc2017/day13/input")
    private val testInput = readInputToMap("src/main/kotlin/aoc2017/day13/testInput")

    @Test
    fun testReadInputToMap() {
        assertEquals(43, input.size)
        assertEquals(4, testInput.size)
    }

    @Test
    fun testComputeTripSeverity() {
        assertEquals(24, computeTripSeverity(testInput))
        assertEquals(1624, computeTripSeverity(input))
    }

    @Test
    fun computeTripSeverityPart2() {
        assertEquals(10, computeTripSeverityPart2(testInput))
        assertEquals(3923436, computeTripSeverityPart2(input))
    }
}