package aoc2022.day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.lcm

internal class Day11KtTest {

    private val testInput0 = readInputToMonkeys("src/main/kotlin/aoc2022/day11/assets/input0")
    private val testInput = readInputToMonkeys("src/main/kotlin/aoc2022/day11/assets/input")

    @Test
    fun testPart1() {
        assertEquals(10605, part1(testInput0))
        assertEquals(95472, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(2713310158, part2(testInput0))
        assertEquals(17926061332, part2(testInput))
    }

    @Test
    fun testLcm() {
        assertEquals(2, lcm(listOf(2)))
        assertEquals(6, lcm(listOf(2, 3)))
        assertEquals(12, lcm(listOf(2, 3, 4)))
        assertEquals(12, lcm(listOf(2, 3, 4, 6)))
        assertEquals(96577, lcm(listOf(23, 19, 13, 17)))
    }
}
