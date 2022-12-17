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
//        assertEquals(70, part2(testInput0))
//        assertEquals(2515, part2(testInput))
    }
}
