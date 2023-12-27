package aoc2023.day16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine
import util.readInputToMap

internal class Day16KtTest {

    private val testInput = readInputToMap("src/main/kotlin/aoc2023/day16/assets/input0")
    private val input = readInputToMap("src/main/kotlin/aoc2023/day16/assets/input")

    @Test
    fun testPart1() {
        assertEquals(46, part1(testInput))
        assertEquals(8125, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(51, part2(testInput))
        assertEquals(8489, part2(input))
    }
}
