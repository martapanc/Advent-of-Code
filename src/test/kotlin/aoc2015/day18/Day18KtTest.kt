package aoc2015.day18

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day18KtTest {

    private val testInput = aoc2018.day18.readInputToMap("src/main/kotlin/aoc2015/day18/testInput")
    private val input = aoc2018.day18.readInputToMap("src/main/kotlin/aoc2015/day18/input")

    @Test
    fun testReadInputToMap() {
        assertEquals(36, testInput.size)
        assertEquals(10000, input.size)
    }

    @Test
    fun testPlayGameOfLights() {
        assertEquals(11, playGameOfLights(testInput))
        assertEquals(4, playGameOfLights(testInput, 4))
        assertEquals(814, playGameOfLights(input, 100))
    }
}