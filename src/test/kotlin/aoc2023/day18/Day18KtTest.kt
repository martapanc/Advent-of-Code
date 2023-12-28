package aoc2023.day18

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.Coord
import util.readInputLineByLine

internal class Day18KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day18/assets/input0")
    private val testInput = parse(testInputLines)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day18/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
        assertEquals(62, part1(testInput))
        assertEquals(44056, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(70, part2(testInput))
        assertEquals(2515, part2(input))
    }

    @Test
    fun testAreXCoordsContinuous() {
        assertTrue(areXCoordsContinuous(listOf(Coord(-1, 0), Coord(0, 0), Coord(2, 0), Coord(1, 0))))
        assertFalse(areXCoordsContinuous(listOf(Coord(-1, 0), Coord(0, 0), Coord(2, 0))))
    }

    @Test
    fun testFindBlocks() {
        assertEquals(listOf(listOf(-1, 0, 1, 2)), findBlocks(listOf(Coord(-1, 0), Coord(0, 0), Coord(2, 0), Coord(1, 0))))
        assertEquals(listOf(listOf(-1, 0, 2)), findBlocks(listOf(Coord(-1, 0), Coord(0, 0), Coord(2, 0))))
        assertEquals(listOf(listOf(-1, 0, 2, 3)), findBlocks(listOf(Coord(-1, 0), Coord(0, 0), Coord(2, 0), Coord(3, 0))))
        assertEquals(listOf(listOf(-1, 0, 2), listOf(4, 5)), findBlocks(listOf(Coord(-1, 0), Coord(0, 0), Coord(2, 0), Coord(4, 0), Coord(5, 0))))
        assertEquals(listOf(listOf(-1, 0, 2), listOf(4, 5, 7)), findBlocks(listOf(Coord(-1, 0), Coord(0, 0), Coord(2, 0), Coord(4, 0), Coord(5, 0), Coord(7, 0))))
        assertEquals(listOf(listOf(-1, 2), listOf(4, 7), listOf(9, 10)), findBlocks(listOf(Coord(-1, 0), Coord(2, 0), Coord(4, 0), Coord(7, 0), Coord(9, 0), Coord(10, 0))))
    }
}
