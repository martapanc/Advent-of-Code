package aoc2017.day24

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day24KtTest {

    private val testInput = readInputBridges("src/main/kotlin/aoc2017/day24/testInput")
    private val input = readInputBridges("src/main/kotlin/aoc2017/day24/input")

    @Test
    fun readInputBridges() {
        assertEquals(8, testInput.size)
        assertEquals(57, input.size)
    }

    @Test
    fun testFindStrongestBridge() {
        assertEquals(31, findStrongestBridge(testInput))
        assertEquals(1868, findStrongestBridge(input))
    }

    @Test
    fun testFindStrengthOfLongestBridge() {
        assertEquals(1854, findStrengthOfLongestBridge(input))
    }
}