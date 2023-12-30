package aoc2023.day19

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day19KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day19/assets/input0")
    private val testInput = parse(testInputLines)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day19/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
        assertEquals(19114, part1(testInput))
        assertEquals(397061, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(167409079868000, part2(testInput))
        assertEquals(125657431183201, part2(input))
    }

    @Test
    fun testCheckToFunct() {
        assertTrue(evalCheck("x<800", testInput.ratings[0]))
        assertTrue(evalCheck("m>2654", testInput.ratings[0]))
        assertFalse(evalCheck("a>1222", testInput.ratings[0]))
        assertFalse(evalCheck("s<2800", testInput.ratings[0]))
    }
}
