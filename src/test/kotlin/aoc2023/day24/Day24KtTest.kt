package aoc2023.day24

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day24KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day24/assets/input0")
    private val testInput = parse(testInputLines)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day24/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
        assertEquals(2, part1(testInput, LongRange(7, 27)))
        assertEquals(15262, part1(input))
    }

    @Test
    fun testPart2() {
//        assertEquals(47, part2(testInput))
        assertEquals(2515, part2(input))
    }
}
