package aoc2022.day08

import util.Coord
import aoc2021.day09.readInputToMap
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day8KtTest {

    private val testInput0 = readInputToMap("src/main/kotlin/aoc2022/day08/assets/input0")
    private val testInput = readInputToMap("src/main/kotlin/aoc2022/day08/assets/input")

    @Test
    fun testPart1() {
        assertEquals(21, part1(testInput0))
        assertEquals(1713, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(8, part2(testInput0))
        assertEquals(268464, part2(testInput))
    }

    @Test
    fun testGetCurrentRingCoords() {
        assertEquals(setOf(
            Coord(1, 1), Coord(2, 1), Coord(3, 1),
            Coord(1, 2),                    Coord(3, 2),
            Coord(1, 3), Coord(2, 3), Coord(3, 3),
        ), getCurrentBorderCoords(1, 5))
        assertEquals(setOf(Coord(2, 2)), getCurrentBorderCoords(2, 5))
        assertEquals(16, getCurrentBorderCoords(0, 5).size)
        assertEquals(16, getCurrentBorderCoords(1, 7).size)
        assertEquals(16, getCurrentBorderCoords(2, 9).size)
        assertEquals(392, getCurrentBorderCoords(0, 99).size)
    }

    @Test
    fun testGetHorizontalAndVerticalNeighborCoords() {
        assertEquals(setOf(
            Coord(2, 0),
            Coord(2, 2),
            Coord(2, 3),
            Coord(2, 4),
            Coord(0, 1),
            Coord(1, 1),
            Coord(3, 1),
            Coord(4, 1),
        ), getHorizontalAndVerticalNeighborCoords(Coord(2, 1), 5))
        assertEquals(setOf(
            Coord(0, 2),
            Coord(1, 2),
            Coord(2, 0),
            Coord(2, 1),
        ), getHorizontalAndVerticalNeighborCoords(Coord(2, 2), 3))
        assertEquals(8, getHorizontalAndVerticalNeighborCoords(Coord(4, 3), 5).size)
        assertEquals(12, getHorizontalAndVerticalNeighborCoords(Coord(4, 3), 7).size)
    }

    @Test
    fun testGetNESWNeighborCoords() {
        val neighborCoords = getHorizontalAndVerticalNeighborCoords(Coord(2, 1), 5)
        assertEquals(mapOf(
            Cardinal.NORTH to setOf(Coord(2, 0)),
            Cardinal.SOUTH to setOf(Coord(2, 2), Coord(2, 3), Coord(2, 4)),
            Cardinal.WEST to setOf(Coord(0, 1), Coord(1, 1)),
            Cardinal.EAST to setOf(Coord(3, 1), Coord(4, 1))
        ), getNESWNeighbors(Coord(2, 1), neighborCoords))
    }

    @Test
    fun testGetLinearScenicScore() {
        assertEquals(0, getLinearScenicScore(testInput0, Coord(0, 0), setOf()))
        assertEquals(3, getLinearScenicScore(testInput0, Coord(3, 0), setOf(
            Coord(2, 0), Coord(1, 0), Coord(0, 0))))
        assertEquals(2, getLinearScenicScore(testInput0, Coord(2, 0), setOf(Coord(1, 0), Coord(0, 0))))
        assertEquals(1, getLinearScenicScore(testInput0, Coord(2, 0), setOf(Coord(3, 0), Coord(4, 0))))
    }
}
