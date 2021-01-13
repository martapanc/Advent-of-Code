package aoc2018.day23

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day23KtTest {

    private val testInput = readInputToMap("src/main/kotlin/aoc2018/day23/testInput")
    private val testInput2 = readInputToMap("src/main/kotlin/aoc2018/day23/testInput2")
    private val input = readInputToMap("src/main/kotlin/aoc2018/day23/input")

    @Test
    fun testReadInputToMap() {
        assertEquals(9, testInput.size)
        assertEquals(3, testInput[BotCoord(4, 0, 0)])
        assertEquals(1000, input.size)
        assertEquals(96904841, input[BotCoord(83955302, -10758981, 41462536)])
    }

    @Test
    fun testCompute3dManhattanDistance() {
        assertEquals(5, compute3dManhattanDistance(BotCoord(1, 3, 1)))
        assertEquals(5, compute3dManhattanDistance(BotCoord(-1, -3, 1)))
    }

    @Test
    fun testCountNanobotsInRange() {
        assertEquals(7, countNanobotsInRange(testInput))
        assertEquals(297, countNanobotsInRange(input))
    }

    @Test
    fun testFindClosestCoordinateInRangeOfMostNanobots() {
        assertEquals(36, findClosestCoordinateInRangeOfMostNanobots(testInput2))
        assertEquals(126233088, findClosestCoordinateInRangeOfMostNanobotsV2(input))
    }
}