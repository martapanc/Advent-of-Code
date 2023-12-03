package aoc2023.day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day3KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2023/day03/assets/input0")
    private val testEngineSchematic0 = parse(testInput0)

    private val testInput1 = readInputLineByLine("src/main/kotlin/aoc2023/day03/assets/input1")
    private val testEngineSchematic1 = parse(testInput1)

    private val testInput = readInputLineByLine("src/main/kotlin/aoc2023/day03/assets/input")
    private val engineSchematic = parse(testInput)

    @Test
    fun testPart1() {
        assertEquals(4361, part1(testEngineSchematic0))
        assertEquals(4361, part1(testEngineSchematic1))
        assertEquals(530849, part1(engineSchematic))
    }

    @Test
    fun testPart2() {
//        assertEquals(70, part2(testInput0))
//        assertEquals(2515, part2(testInput))
    }
}
