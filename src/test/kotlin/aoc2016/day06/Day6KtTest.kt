package aoc2016.day06

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day6KtTest {

    private val input = "src/main/kotlin/aoc2016/day06/input"
    private val testInput = "src/main/kotlin/aoc2016/day06/testInput"

    @Test
    fun testReadInputByColumnsAndComputeMessage() {
        assertEquals("easter", computeMessageByMostCommonCharacter(testInput))
        assertEquals("xhnqpqql", computeMessageByMostCommonCharacter(input))
    }

    @Test
    fun testComputeMessageByLeastCommonCharacter() {
        assertEquals("advent", computeMessageByLeastCommonCharacter(testInput))
        assertEquals("brhailro", computeMessageByLeastCommonCharacter(input))
    }
}