package aoc2023.day08

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day8KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2023/day08/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2023/day08/assets/input")

    @Test
    fun testPart1() {
        assertEquals(157, part1(testInput0))
        assertEquals(8085, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(70, part2(testInput0))
        assertEquals(2515, part2(testInput))
    }
}
