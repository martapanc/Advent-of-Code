package aoc2016.day13

import aoc2020.day20.Coord
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day13KtTest {

    private val testInput = 10
    private val input = 1358

    @Test
    fun testComputeCoordinate() {
        assertEquals('.', computeCoordinate(Coord(0, 0), testInput))
        assertEquals('.', computeCoordinate(Coord(0, 1), testInput))
        assertEquals('#', computeCoordinate(Coord(1, 0), testInput))
        assertEquals('#', computeCoordinate(Coord(9, 0), testInput))
        assertEquals('#', computeCoordinate(Coord(9, 6), testInput))
    }

    @Test
    fun testComputeGrid() {
        assertEquals(11, computeManhattanDistanceOfTarget(testInput, Coord(7, 4)))
        assertEquals(96, computeManhattanDistanceOfTarget(input, Coord(31, 39)))
    }

    @Test
    fun testComputeReachableCellsAtDistance() {
        assertEquals(151, computeReachableCellsAtDistance(testInput, 50))
        assertEquals(141, computeReachableCellsAtDistance(input, 50))
    }
}