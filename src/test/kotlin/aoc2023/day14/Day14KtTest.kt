package aoc2023.day14

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputToMap

internal class Day14KtTest {

    private val testInput = readInputToMap("src/main/kotlin/aoc2023/day14/assets/input0")

    private val input = readInputToMap("src/main/kotlin/aoc2023/day14/assets/input")

    @Test
    fun testPart1() {
        assertEquals(136, part1(testInput))
        assertEquals(113486, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(70, part2(testInput))
        assertEquals(2515, part2(input))
    }
}
