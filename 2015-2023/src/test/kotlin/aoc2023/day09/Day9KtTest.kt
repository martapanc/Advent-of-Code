package aoc2023.day09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day9KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day09/assets/input0")
    private val testInput = parse(testInputLines)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day09/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
        assertEquals(114, part1(testInput))
        assertEquals(2043183816, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(2, part2(testInput))
        assertEquals(1118, part2(input))
    }

    @Test
    fun testReduceToNext() {
        assertEquals(0, reduceToNext(listOf(2, 2, 2, 2)))
        assertEquals(2, reduceToNext(listOf(0, 2, 4, 6)))
        assertEquals(1, reduceToNext(listOf(1, 2, 3)))
        assertEquals(3, reduceToNext(listOf(0, 3, 6, 9, 12, 15)))
        assertEquals(7, reduceToNext(listOf(1, 3, 6, 10, 15, 21)))
        assertEquals(23, reduceToNext(listOf(10, 13, 16, 21, 30, 45)))
    }

    @Test
    fun testFindNextNumInSequence() {
        assertEquals(2, findNextNumInSequence(listOf(2, 2, 2, 2)))
        assertEquals(8, findNextNumInSequence(listOf(0, 2, 4, 6)))
        assertEquals(4, findNextNumInSequence(listOf(1, 2, 3)))
        assertEquals(18, findNextNumInSequence(listOf(0, 3, 6, 9, 12, 15)))
        assertEquals(28, findNextNumInSequence(listOf(1, 3, 6, 10, 15, 21)))
        assertEquals(68, findNextNumInSequence(listOf(10, 13, 16, 21, 30, 45)))
    }
    @Test
    fun testFindPreviousNumInSequence() {
        assertEquals(2, findPreviousNumInSequence(listOf(2, 2, 2, 2)))
        assertEquals(-2, findPreviousNumInSequence(listOf(0, 2, 4, 6)))
        assertEquals(0, findPreviousNumInSequence(listOf(1, 2, 3)))
        assertEquals(-3, findPreviousNumInSequence(listOf(0, 3, 6, 9, 12, 15)))
        assertEquals(0, findPreviousNumInSequence(listOf(1, 3, 6, 10, 15, 21)))
        assertEquals(5, findPreviousNumInSequence(listOf(10, 13, 16, 21, 30, 45)))
    }
}
