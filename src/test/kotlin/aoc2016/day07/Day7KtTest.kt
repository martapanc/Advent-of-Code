package aoc2016.day07

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day7KtTest {

    private val input = "src/main/kotlin/aoc2016/day07/input"
    private val testInput = "src/main/kotlin/aoc2016/day07/testInput"

    @Test
    fun testCountValidIPs() {
        assertEquals(2, countValidIPs(testInput))
        assertEquals(110, countValidIPs(input))
        assertEquals(242, countValidIPs(input, isPart2 = true))
    }
}