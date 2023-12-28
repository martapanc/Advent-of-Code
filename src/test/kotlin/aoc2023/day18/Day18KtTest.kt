package aoc2023.day18

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day18KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day18/assets/input0")
    private val testInput = parse(testInputLines)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day18/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
        assertEquals(0, part1(testInput))
        assertEquals(8085, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(70, part2(testInput))
        assertEquals(2515, part2(input))
    }
}
