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
}