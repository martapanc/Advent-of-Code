package aoc2015.day02

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day2KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2015/day02/input")

    @Test
    fun testReadInputToList() {
        assertEquals(1000, input.size)
    }

    @Test
    fun testComputeTotalArea() {
        assertEquals(101, computeTotalArea(listOf(Sides(2, 3, 4), Sides(1, 1, 10))))
        assertEquals(1606483, computeTotalArea(input))
    }

    @Test
    fun testComputeTotalRibbon() {
        assertEquals(48, computeTotalRibbon(listOf(Sides(2, 3, 4), Sides(1, 1, 10))))
        assertEquals(3842356, computeTotalRibbon(input))
    }
}