package aoc2021.day02

import aoc2020.day05.readInputToList
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day2KtTest {

    val input0 = "src/main/kotlin/aoc2021/day02/assets/input0"
    val input = "src/main/kotlin/aoc2021/day02/assets/input"
    private val testInputList = readInputToCommands(readInputToList(input0))
    private val inputList = readInputToCommands(readInputToList(input))

    @Test
    fun testComputeSubmarinePosition() {
        assertEquals(150, computeSubmarinePosition(testInputList))
        assertEquals(1524750, computeSubmarinePosition(inputList))
    }

    @Test
    fun testComputeSubmarinePositionPart2() {
        assertEquals(900, computeSubmarinePositionPart2(testInputList))
        assertEquals(1592426537, computeSubmarinePositionPart2(inputList))
    }
}
