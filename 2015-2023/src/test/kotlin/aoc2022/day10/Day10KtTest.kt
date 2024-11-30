package aoc2022.day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day10KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2022/day10/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2022/day10/assets/input")

    @Test
    fun testSolve() {
        assertEquals(13140, solve(testInput0))
        assertEquals(14860, solve(testInput)) // RGZEHURK
    }
}
