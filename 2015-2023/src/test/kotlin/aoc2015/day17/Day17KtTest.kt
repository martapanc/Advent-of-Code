package aoc2015.day17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day17KtTest {

    private val input = listOf(43, 3, 4, 10, 21, 44, 4, 6, 47, 41, 34, 17, 17, 44, 36, 31, 46, 9, 27, 38)
    private val testInput = listOf(20, 15, 10, 5, 5)

    @Test
    fun testCountCombinationsOfContainers() {
        assertEquals(4, countCombinationsOfContainers(25, testInput))
        assertEquals(1638, countCombinationsOfContainers(150, input))
    }

    @Test
    fun testCountCombinationsOfMinNumOfContainers() {
        assertEquals(3, countCombinationsOfMinNumOfContainers(25, testInput))
        assertEquals(17, countCombinationsOfMinNumOfContainers(150, input))
    }
}