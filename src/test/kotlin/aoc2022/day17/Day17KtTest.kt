package aoc2022.day17

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day17KtTest {

    private val testInput0 = readInputToJetStreams("src/main/kotlin/aoc2022/day17/assets/input0")
    private val testInput = readInputToJetStreams("src/main/kotlin/aoc2022/day17/assets/input")

    @Test
    fun testPart1() {
        assertEquals(4, part1(testInput0, 2))
        assertEquals(17, part1(testInput0, 10))
        assertEquals(3068, part1(testInput0))
        assertEquals(3069, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(322, solve(testInput, 220))
        assertEquals(2919, solve(testInput, 1925))
        assertEquals(5516, solve(testInput, 3630))

        assertEquals(2214, solve(testInput, 1475))
//        assertEquals(15199, solve(testInput, 10000)) // OK

        assertEquals(2393, solve(testInput, 1585))

        assertEquals(1523167155404, part2(testInput))
    }
}
