package aoc2021.day18

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day18KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2021/day18/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2021/day18/assets/input")

    @Test
    fun testPart1() {
        assertEquals(4140, part1(testInput0))
        assertEquals(3359, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(3993, part2(testInput0))
        assertEquals(4616, part2(testInput))
    }
}
