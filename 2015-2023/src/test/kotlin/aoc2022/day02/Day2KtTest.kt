package aoc2022.day02

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day2KtTest {

    private val testInputList1 = readInputToPairOfPlays("src/main/kotlin/aoc2022/day02/assets/input0")
    private val inputList1 = readInputToPairOfPlays("src/main/kotlin/aoc2022/day02/assets/input")

    private val testInputList2 = readInputToPairOfPlayOutcome("src/main/kotlin/aoc2022/day02/assets/input0")
    private val inputList2 = readInputToPairOfPlayOutcome("src/main/kotlin/aoc2022/day02/assets/input")

    @Test
    fun testScissorPaperRock_part1() {
        assertEquals(15, scissorPaperRockPart1(testInputList1))
        assertEquals(11150, scissorPaperRockPart1(inputList1))
    }

    @Test
    fun testScissorPaperRock_part2() {
        assertEquals(12, scissorPaperRockPart2(testInputList2))
        assertEquals(8295, scissorPaperRockPart2(inputList2))
    }
}
