package aoc2022.day06

import aoc2019.day16.readInputToString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day6KtTest {

    private val testInput0 = readInputToString("src/main/kotlin/aoc2022/day06/assets/input0")
    private val testInput1 = "bvwbjplbgvbhsrlpgdmjqwftvncz"
    private val testInput2 = "nppdvjthqldpwncqszvftbrmjlhg"
    private val testInput3 = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
    private val input = readInputToString("src/main/kotlin/aoc2022/day06/assets/input")

    @Test
    fun testPart1() {
        assertEquals(10, part1(testInput0))
        assertEquals(5, part1(testInput1))
        assertEquals(6, part1(testInput2))
        assertEquals(11, part1(testInput3))
        assertEquals(1855, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(29, part2(testInput0))
        assertEquals(23, part2(testInput1))
        assertEquals(23, part2(testInput2))
        assertEquals(26, part2(testInput3))
        assertEquals(3256, part2(input))
    }
}
