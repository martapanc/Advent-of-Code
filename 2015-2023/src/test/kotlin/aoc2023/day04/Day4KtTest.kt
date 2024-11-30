package aoc2023.day04

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day4KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2023/day04/assets/input0")
    private val testGame = parse(testInput0)

    private val input = readInputLineByLine("src/main/kotlin/aoc2023/day04/assets/input")
    private val game = parse(input)

    @Test
    fun testPart1() {
        assertEquals(13, part1(testGame))
        assertEquals(24175, part1(game))
    }

    @Test
    fun testPart2() {
        assertEquals(30, part2(testGame))
        assertEquals(18846301, part2(game))
    }
}
