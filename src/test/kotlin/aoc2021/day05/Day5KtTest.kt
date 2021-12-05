package aoc2021.day05

import aoc2020.day05.readInputToList
import aoc2021.day02.readInputToCommands
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day5KtTest {

    val input0 = readInputToList("src/main/kotlin/aoc2021/day05/assets/input0")
    val input = readInputToList("src/main/kotlin/aoc2021/day05/assets/input")

    private val testInputList = getListOfPointPairs(input0)
    private val inputList = getListOfPointPairs(input)

    @Test
    fun getOverlappingPoints() {
        assertEquals(5, getOverlappingPoints(testInputList))
        assertEquals(6548, getOverlappingPoints(inputList))
    }
}