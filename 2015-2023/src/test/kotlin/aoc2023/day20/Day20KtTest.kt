package aoc2023.day20

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day20KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day20/assets/input0")
    private val testInput = parse(testInputLines)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day20/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
        assertEquals(11687500, part1(testInput))
        assertEquals(703315117, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(230402300925361, part2(input))
    }
}
