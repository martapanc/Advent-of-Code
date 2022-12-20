package aoc2022.day20

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day20KtTest {

    private val testInput0 = readInputToNumbers("src/main/kotlin/aoc2022/day20/assets/input0")
    private val testInput = readInputToNumbers("src/main/kotlin/aoc2022/day20/assets/input")

    @Test
    fun testPart1() {
        assertEquals(3, Day20(testInput0).part1())
        assertEquals(9866, Day20(testInput).part1())
    }

    @Test
    fun testPart2() {
        assertEquals(1623178306, Day20(testInput0).part2())
        assertEquals(12374299815791, Day20(testInput).part2())
    }

}
