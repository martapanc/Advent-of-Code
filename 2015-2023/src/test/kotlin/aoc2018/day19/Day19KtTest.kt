package aoc2018.day19

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day19KtTest {

    private val input = readInputToInstructions("src/main/kotlin/aoc2018/day19/input")
    private val testInput = readInputToInstructions("src/main/kotlin/aoc2018/day19/testInput")

    @Test
    fun testReadInputToInstructions() {
        assertEquals(0, testInput.first)
        assertEquals(7, testInput.second.size)
        assertEquals(3, input.first)
        assertEquals(36, input.second.size)
    }

    @Test
    fun testRunProgram() {
        assertEquals(6, runProgram(testInput))
        assertEquals(1008, runProgram(input))
    }

    @Test
    fun testRunProgramPart2() {
        assertEquals(11534976, runProgram(input, isPart2 = true))
    }
}