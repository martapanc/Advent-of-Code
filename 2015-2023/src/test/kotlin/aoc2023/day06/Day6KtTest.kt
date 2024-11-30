package aoc2023.day06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day6KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day06/assets/input0")
    private val testInput = parse(testInputLines)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day06/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
        assertEquals(288, part1(testInput))
        assertEquals(1108800, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(71503, part2(testInput))
        assertEquals(36919753, part2(input))
    }

    @Test
    fun testComputeDistance() {
        assertEquals(0, computeDistance(7, 0))
        assertEquals(6, computeDistance(7, 1))
        assertEquals(10, computeDistance(7, 2))
        assertEquals(12, computeDistance(7, 3))
        assertEquals(12, computeDistance(7, 4))
        assertEquals(10, computeDistance(7, 5))
        assertEquals(6, computeDistance(7, 6))
        assertEquals(0, computeDistance(7, 7))
        assertEquals(200, computeDistance(30, 20))
    }
}
