package aoc2015.day15

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day15KtTest {

    @Test
    fun testFindOptimalCombination() {
        assertEquals(222870, findOptimalCombination())
        assertEquals(117936, findOptimalCombination(true))
    }
}