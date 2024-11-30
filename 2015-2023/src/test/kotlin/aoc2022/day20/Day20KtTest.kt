package aoc2022.day20

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day20KtTest {

    private val testInput0 = readInputToNumbers("src/main/kotlin/aoc2022/day20/assets/input0")
    private val testInput = readInputToNumbers("src/main/kotlin/aoc2022/day20/assets/input")

    @Test
    fun testPart1() {
        assertEquals(3, part1(testInput0))
        assertEquals(9866, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(1623178306, part2(testInput0))
        assertEquals(12374299815791, part2(testInput))
    }
}
