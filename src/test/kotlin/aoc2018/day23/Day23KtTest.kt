package aoc2018.day23

import aoc2020.day17.Coord3d
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day23KtTest {

    private val testInput = readInputToMap("src/main/kotlin/aoc2018/day23/testInput")
    private val input = readInputToMap("src/main/kotlin/aoc2018/day23/input")

    @Test
    fun testReadInputToMap() {
        assertEquals(9, testInput.size)
        assertEquals(3, testInput[Coord3d(4, 0, 0)])
        assertEquals(1000, input.size)
        assertEquals(96904841, input[Coord3d(83955302, -10758981, 41462536)])
    }

    @Test
    fun testCompute3dManhattanDistance() {
        assertEquals(5, compute3dManhattanDistance(Coord3d(1, 3, 1)))
        assertEquals(5, compute3dManhattanDistance(Coord3d(-1, -3, 1)))
    }

    @Test
    fun testCountNanobotsInRange() {
        assertEquals(7, countNanobotsInRange(testInput))
        assertEquals(297, countNanobotsInRange(input))
    }
}