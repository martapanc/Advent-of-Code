package aoc2022.day15

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day15KtTest {

    private val testInput0 = readInputToSensorList("src/main/kotlin/aoc2022/day15/assets/input0")
    private val testInput = readInputToSensorList("src/main/kotlin/aoc2022/day15/assets/input")

    @Test
    fun testPart1() {
        assertEquals(26, part1(testInput0, 10))
        assertEquals(5508234, part1(testInput, 2000000))
    }

    @Test
    fun testPart2() {
//        assertEquals(56000011, part2(testInput0, 20))
//        assertEquals(2515, part2Big(testInput, 2000))
        assertEquals(56000011, solve(testInput0, 20))
        assertEquals(2515, solve(testInput, 4000000))
    }

    @Test
    fun testManhattanDistance() {
        assertEquals(0, Coord(0, 0).manhattanDistance(Coord(0, 0)))
        assertEquals(1, Coord(0, 0).manhattanDistance(Coord(0, 1)))
        assertEquals(2, Coord(0, 0).manhattanDistance(Coord(-2, 0)))
        assertEquals(6, Coord(1, 2).manhattanDistance(Coord(-3, 4)))
    }

    @Test
    fun testGetCellsCoveredBySensor() {
        assertEquals(setOf(Coord(1, 2)), getCellsCoveredBySensor(Coord(1, 2), Coord(1 ,2)))
        assertEquals(setOf(Coord(-1, 0), Coord(1, 0), Coord(0, 0), Coord(0, 1), Coord(0, -1)),
            getCellsCoveredBySensor(Coord(0, 0), Coord(1 ,0)))
        assertEquals(13, getCellsCoveredBySensor(Coord(0, 0), Coord(-2 ,0)).size)
        assertEquals(25, getCellsCoveredBySensor(Coord(0, 0), Coord(0 ,3)).size)
        assertEquals(181, getCellsCoveredBySensor(Coord(8, 7), Coord(2 ,10)).size)
    }
}
