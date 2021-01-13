package aoc2015.day20

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day20KtTest {

    private val input = 29000000

    @Test
    fun testComputeFactorsOfNumber() {
        assertEquals(listOf(1), computeFactorsOfNumber(1))
        assertEquals(listOf(1, 2), computeFactorsOfNumber(2))
        assertEquals(listOf(1, 3), computeFactorsOfNumber(3))
        assertEquals(listOf(1, 2, 3, 4, 6, 8, 12, 24), computeFactorsOfNumber(24))
    }

    @Test
    fun testComputeGiftsDeliveredAtHouseNumber() {
        assertEquals(10, computeGiftsDeliveredAtHouseNumber(1))
        assertEquals(30, computeGiftsDeliveredAtHouseNumber(2))
        assertEquals(40, computeGiftsDeliveredAtHouseNumber(3))
        assertEquals(130, computeGiftsDeliveredAtHouseNumber(9))
    }

    @Test
    fun testFindHouseWithNGiftsDelivered() {
        assertEquals(665280, findHouseWithNGiftsDelivered(input))
        assertEquals(705600, findHouseWithNGiftsDeliveredPart2(input))
    }
}