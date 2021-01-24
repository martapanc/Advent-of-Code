package aoc2017.day12

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day12KtTest {

    private val input = readInputToMap("src/main/kotlin/aoc2017/day12/input")
    private val testInput = readInputToMap("src/main/kotlin/aoc2017/day12/testInput")

    @Test
    fun testFindConnectedPrograms() {
        assertEquals(6, findConnectedPrograms(testInput))
        assertEquals(130, findConnectedPrograms(input))
    }

    @Test
    fun testFindConnectedProgramsPart2() {
        assertEquals(2, findConnectedPrograms(testInput, isPart1 = false))
        assertEquals(189, findConnectedPrograms(input, isPart1 = false))
    }
}