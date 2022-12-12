package aoc2022.day12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.Coord
import util.readInputToMap

internal class Day12KtTest {

    private val testInput0 = readInputToMap("src/main/kotlin/aoc2022/day12/assets/input0")
    private val testInput = readInputToMap("src/main/kotlin/aoc2022/day12/assets/input")

    @Test
    fun testPart1() {
        assertEquals(31, part1(testInput0))
        assertEquals(447, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(29, part2(testInput0))
        assertEquals(446, part2(testInput))
    }

    @Test
    fun testGetValidNeighbors() {
        assertEquals(setOf(Coord(1,0), Coord(0, 1)), getValidNeighbors(testInput0, Coord(0, 0)))
        assertEquals(setOf(Coord(0,0), Coord(2,0), Coord(1, 1)), getValidNeighbors(testInput0, Coord(1, 0)))
        assertEquals(setOf(Coord(1,0), Coord(2, 1)), getValidNeighbors(testInput0, Coord(2, 0)))
        assertEquals(setOf(Coord(5, 2), Coord(4, 1), Coord(3, 2), Coord(4, 3)), getValidNeighbors(testInput0, Coord(4, 2)))
    }
}
