package aoc2022.day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day10KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2022/day10/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2022/day10/assets/input")

    @Test
    fun testPart1() {
        assertEquals(13140, part1(testInput0))
        assertEquals(14860, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(70, part2(testInput0))
        assertEquals(2515, part2(testInput))
    }
}
