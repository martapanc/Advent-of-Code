package aoc2015.day23

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day23KtTest {

    private val input = "src/main/kotlin/aoc2015/day23/input"

    @Test
    fun testReadInputToList() {
        assertEquals(184, readInputToList(input))
        assertEquals(231, readInputToList(input, isPart2 = true))
    }
}