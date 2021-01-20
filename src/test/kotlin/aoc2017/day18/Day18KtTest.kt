package aoc2017.day18

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day18KtTest {

    private val input = "src/main/kotlin/aoc2017/day18/input"
    private val testInput = "src/main/kotlin/aoc2017/day18/testInput"

    @Test
    fun testReadInputAndRunInstructions() {
        assertEquals(4, readInputAndRunInstructions(testInput))
        assertEquals(9423, readInputAndRunInstructions(input))
    }

    @Test
    fun testRunProgramPart1() {
        assertEquals(4, runProgramPart1(testInput))
        assertEquals(9423, runProgramPart1(input))
    }

    @Test
    fun testRunProgramPart2() {
        assertEquals(7620, runProgramPart2(input))
    }
}