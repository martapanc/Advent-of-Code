package aoc2018.day21

import aoc2018.day19.readInputToInstructions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day21KtTest {

    private val input = readInputToInstructions("src/main/kotlin/aoc2018/day21/input")

    @Test
    fun testRunProgramPart1() {
        assertEquals(13522479, runProgram(input).first())
    }

    @Test
    fun testRunProgramPart2() {
        assertEquals(14626276, runProgram(input).last())
    }
}