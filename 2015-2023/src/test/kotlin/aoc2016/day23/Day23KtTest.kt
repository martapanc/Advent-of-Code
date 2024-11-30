package aoc2016.day23

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day23KtTest {

    private val input = "src/main/kotlin/aoc2016/day23/input"

    @Test
    fun testComputeAssembunnyRegisterValue() {
        val first = 79
        val second = 74
        assertEquals(10886, computeAssembunnyRegisterValue(7, first, second))
        assertEquals(479007446, computeAssembunnyRegisterValue(12, first, second))
    }
}