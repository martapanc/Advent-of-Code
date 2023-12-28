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
        assertEquals(47139, part1(input, start = Coord(150, 10)))
    }

    @Test
    fun testPart2() {
        assertEquals(952408144115, part2(testInput))
        assertEquals(2515, part2(input))
    }
}
