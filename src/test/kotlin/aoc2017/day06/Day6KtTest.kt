package aoc2017.day06

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day6KtTest {

    private val testInput = mutableListOf(0, 2, 7, 0)
    private val input = mutableListOf(11, 11, 13, 7, 0, 15, 5, 5, 4, 4, 1, 1, 7, 1, 15, 11)

    @Test
    fun testRunRedistributionProgram() {
        assertEquals(5, runRedistributionProgram(testInput))
        assertEquals(4074, runRedistributionProgram(input))
    }
    @Test
    fun testRunRedistributionProgramPart2() {
        assertEquals(4, runRedistributionProgram(testInput, true))
        assertEquals(2793, runRedistributionProgram(input, true))
    }
}