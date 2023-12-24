package aoc2023.day13

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day13KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day13/assets/input0")
    private val testInput = parse(testInputLines)

    private val testInputLines1 = readInputLineByLine("src/main/kotlin/aoc2023/day13/assets/input1")
    private val testInput1 = parse(testInputLines1)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day13/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
        assertEquals(405, part1(testInput))
        assertEquals(35691, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(400, part2(testInput))
        assertEquals(10, part2(testInput1))
        assertEquals(39037, part2(input))
    }

    @Test
    fun testFindMirror() {
        assertEquals(5, findMirror(testInput[0]))
        assertEquals(400, findMirror(testInput[1]))
    }

    @Test
    fun testIsAlmostIdentical() {
        assertFalse(isAlmostIdentical("#.##..##.", "#.##..##."))
        assertTrue(isAlmostIdentical("#.##..##.", "#.##..###"))
        assertFalse(isAlmostIdentical("#.##..##.", "..##..###"))
    }
}
