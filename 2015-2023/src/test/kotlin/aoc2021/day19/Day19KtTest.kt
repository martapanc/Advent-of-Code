package aoc2021.day19

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day19KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2021/day19/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2021/day19/assets/input")

    @Test
    fun testPart1() {
        assertEquals(79, solve(testInput0).first)
        assertEquals(432, solve(testInput).first)
    }

    @Test
    fun testPart2() {
        assertEquals(3621, solve(testInput0).second)
        assertEquals(14414, solve(testInput).second)
    }
}
