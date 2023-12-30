package aoc2023.day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine
import util.readInputToMap

internal class Day21KtTest {

    private val testInput = readInputToMap("src/main/kotlin/aoc2023/day21/assets/input0")

    private val input = readInputToMap("src/main/kotlin/aoc2023/day21/assets/input")

    @Test
    fun testPart1() {
        assertEquals(16, part1(testInput, steps = 6))
        assertEquals(3605, part1(input))
    }

    @Test
    fun testPart2() {
//        assertEquals(16733044, part2(testInput, 5000))
//        assertEquals(2515, part2(input))
    }
}
