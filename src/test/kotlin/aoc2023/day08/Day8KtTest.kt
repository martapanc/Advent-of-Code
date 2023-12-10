package aoc2023.day08

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day8KtTest {

    private val testInputLines0 = readInputLineByLine("src/main/kotlin/aoc2023/day08/assets/input0")
    private val testInput0 = parse(testInputLines0)

    private val testInputLines1 = readInputLineByLine("src/main/kotlin/aoc2023/day08/assets/input1")
    private val testInput1 = parse(testInputLines1)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day08/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
        assertEquals(2, part1(testInput0))
        assertEquals(6, part1(testInput1))
        assertEquals(19667, part1(input))
    }

    @Test
    fun testPart2() {
//        assertEquals(70, part2(testInput0))
//        assertEquals(2515, part2(input))
    }
}
