package aoc2017.day23

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day23KtTest {

    private val input = "src/main/kotlin/aoc2017/day23/input"

    @Test
    fun testReadInputAndRun() {
        assertEquals(8281, readInputAndRun(input))
    }

    @Test
    fun testReadInputAndRunPart2() {
        assertEquals(911, readInputAndRunPart2(input))
    }
}