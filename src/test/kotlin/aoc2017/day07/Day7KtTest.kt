package aoc2017.day07

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day7KtTest {

    private val input = "src/main/kotlin/aoc2017/day07/input"
    private val testInput = "src/main/kotlin/aoc2017/day07/testInput"

    @Test
    fun testReadInputAndFindRoot() {
        assertEquals("tknk", readInputAndFindRoot(testInput))
        assertEquals("vtzay", readInputAndFindRoot(input))
    }
}