package aoc2022.day14

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day14KtTest {

    private val testInput0 = readInputToRockPaths("src/main/kotlin/aoc2022/day14/assets/input0")
    private val testInput = readInputToRockPaths("src/main/kotlin/aoc2022/day14/assets/input")

    @Test
    fun testPart1() {
        assertEquals(24, part1(testInput0))
        assertEquals(625, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(93, part2(testInput0))
        assertEquals(25193, part2(testInput))
    }
}
