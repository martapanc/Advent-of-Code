package aoc2022.day18

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day18KtTest {

    private val testInput0 = readInputTo3dCoords("src/main/kotlin/aoc2022/day18/assets/input0")
    private val testInput1 = listOf(Coord3d(1, 1, 1), Coord3d(2, 1, 1))
    private val testInput2 = listOf(
        Coord3d(1, 0, 0), Coord3d(-1, 0, 0),
        Coord3d(0, 1, 0), Coord3d(0, -1, 0),
        Coord3d(0, 0, 1), Coord3d(0, 0, -1)
    )
    private val testInput = readInputTo3dCoords("src/main/kotlin/aoc2022/day18/assets/input")

    @Test
    fun testPart1() {
        assertEquals(10, part1(testInput1))
        assertEquals(36, part1(testInput2))
        assertEquals(64, part1(testInput0))
        assertEquals(3412, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(30, part2(testInput2))
        assertEquals(10, part2(testInput1))
        assertEquals(58, part2(testInput0))
        assertEquals(2018, part2(testInput))
    }
}
