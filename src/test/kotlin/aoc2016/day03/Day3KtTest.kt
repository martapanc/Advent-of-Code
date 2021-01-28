package aoc2016.day03

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day3KtTest {

    private val input = "src/main/kotlin/aoc2016/day03/input"

    @Test
    fun testCountValidTriangles() {
        assertEquals(862, countValidTriangles(input))
    }

    @Test
    fun testCountValidTrianglesByColumn() {
        assertEquals(1577, countValidTrianglesByColumn(input))
    }
}