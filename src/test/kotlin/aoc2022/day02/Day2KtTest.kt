package aoc2022.day02

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day2KtTest {

    private val testInputList = readInputToPairOfPlays("src/main/kotlin/aoc2022/day02/assets/input0")
    private val inputList = readInputToPairOfPlays("src/main/kotlin/aoc2022/day02/assets/input")

    @Test
    fun testScissorPaperRock_part1() {
        assertEquals(15, scissorPaperRockPart1(testInputList))
        assertEquals(11150, scissorPaperRockPart1(inputList))
    }
}
