package aoc2023.day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine
import util.readInputToMap

internal class Day11KtTest {

    private val testInput = readInputToMap("src/main/kotlin/aoc2023/day11/assets/input0")

    private val input = readInputToMap("src/main/kotlin/aoc2023/day11/assets/input")

    @Test
    fun testPart1() {
        assertEquals(157, part1(testInput))
        assertEquals(8085, part1(input))
    }

    @Test
    fun testPart2() {
//        assertEquals(70, part2(testInput))
//        assertEquals(2515, part2(input))
    }
}
