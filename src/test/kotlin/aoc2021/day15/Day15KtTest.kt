package aoc2021.day15

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day15KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2021/day15/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2021/day15/assets/input")

    @Test
    fun testPart1() {
        assertEquals(40, solve(testInput0, 1))
        assertEquals(410, solve(testInput, 1))
    }

    @Test
    fun testPart2() {
        assertEquals(315, solve(testInput0, 5))
        assertEquals(2809, solve(testInput, 5))
    }
}
