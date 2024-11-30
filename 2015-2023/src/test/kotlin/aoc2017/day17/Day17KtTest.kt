package aoc2017.day17

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day17KtTest {

    private val testInput = 3
    private val input = 370

    @Test
    fun testRunSpinlock() {
        assertEquals(638, runSpinlock(testInput))
        assertEquals(1244, runSpinlock(input))
        assertEquals(11162912, runSpinlockV2(input))
    }
}