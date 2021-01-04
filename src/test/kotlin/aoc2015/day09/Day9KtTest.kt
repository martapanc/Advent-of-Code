package aoc2015.day09

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day9KtTest {

    private val testInput = readInputToListDay9("src/main/kotlin/aoc2015/day09/testInput")
    private val input = readInputToListDay9("src/main/kotlin/aoc2015/day09/input")

    @Test
    fun readInputToListDay9() {
        assertEquals(3, testInput.size)
        assertEquals(28, input.size)
    }

    @Test
    fun testFindShortestTripPermutation() {
        assertEquals(117, findShortestTripPermutation(input))
        assertEquals(909, findShortestTripPermutation(input, longest = true))
    }
}