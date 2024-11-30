package aoc2022.day16

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day16KtTest {

    private val testInput0 = readInputToValves("src/main/kotlin/aoc2022/day16/assets/input0")
    private val testInput = readInputToValves("src/main/kotlin/aoc2022/day16/assets/input")

    @Test
    fun testPart1() {
        assertEquals(1651, part1(testInput0))
        assertEquals(2077, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(1707, part2(testInput0))
        assertEquals(2741, part2(testInput))
    }
}
