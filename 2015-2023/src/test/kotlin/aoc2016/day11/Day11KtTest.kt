package aoc2016.day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day11KtTest {

    @Test
    fun testSolvePart1() {
        assertEquals(11, solveGameOfGeneratorsAndChips(listOf(2, 2, 0, 0)))
        assertEquals(37, solveGameOfGeneratorsAndChips(listOf(4, 5, 1, 0)))
        assertEquals(61, solveGameOfGeneratorsAndChips(listOf(8, 5, 1, 0)))
    }
}