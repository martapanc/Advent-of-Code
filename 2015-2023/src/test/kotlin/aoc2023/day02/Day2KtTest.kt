package aoc2023.day02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day2KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2023/day02/assets/input0")
    private val games0 = parse(testInput0);
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2023/day02/assets/input")
    private val games = parse(testInput);

    @Test
    fun testPart1() {
        assertEquals(8, part1(games0))
        assertEquals(2449, part1(games))
    }

    @Test
    fun testPart2() {
        assertEquals(2286, part2(games0))
        assertEquals(63981, part2(games))
    }
}
