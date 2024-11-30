package aoc2019.day23

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day23KtTest {

    private val input = "src/main/kotlin/aoc2019/day23/input"

    @Test
    fun testRunNetworkProgram() {
        assertEquals(18982, runNetworkProgram(input).first)
        assertEquals(11088, runNetworkProgram(input).second)
    }
}