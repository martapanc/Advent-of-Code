package aoc2015.day18

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import util.readInputToMap

internal class Day18KtTest {

    private val testInput = readInputToMap("src/main/kotlin/aoc2015/day18/testInput")
    private val input = readInputToMap("src/main/kotlin/aoc2015/day18/input")

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

    @Test
    fun testPlayGameOfLightsPart2() {
        assertEquals(18, playGameOfLights(testInput, stuckCorners = true))
        assertEquals(17, playGameOfLights(testInput, 5, true))
        assertEquals(924, playGameOfLights(input, 100,true))
    }
}