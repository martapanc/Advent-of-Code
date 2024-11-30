package aoc2021.day12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day12KtTest {

    private val testInput0 = readInputToCaveSystem("src/main/kotlin/aoc2021/day12/assets/input0")
    private val testInput1 = readInputToCaveSystem("src/main/kotlin/aoc2021/day12/assets/input1")
    private val testInput = readInputToCaveSystem("src/main/kotlin/aoc2021/day12/assets/input")

    @Test
    fun testPart1() {
        assertEquals(19, solve(testInput0))
        assertEquals(10, solve(testInput1))
        assertEquals(3738, solve(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(103, solve(testInput0, true))
        assertEquals(36, solve(testInput1, true))
        assertEquals(120506, solve(testInput, true))
    }
}

