package aoc2023.day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputToMap

internal class Day21KtTest {

    private val testInput0 = readInputToMap("src/main/kotlin/aoc2023/day21/assets/input0")
    private val testInput1 = readInputToMap("src/main/kotlin/aoc2023/day21/assets/input1")
    private val testInput2 = readInputToMap("src/main/kotlin/aoc2023/day21/assets/input2")

    private val input = readInputToMap("src/main/kotlin/aoc2023/day21/assets/input")

    @Test
    fun testPart1() {
        assertEquals(16, part1(testInput0, steps = 6))
        assertEquals(3605, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(596734624269210, part2(input))
    }
}
