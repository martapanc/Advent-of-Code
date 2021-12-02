package aoc2021.day02

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day2KtTest {

    private val testInputList = aoc2020.day05.readInputToList("src/main/kotlin/aoc2021/day02/assets/input0")
    private val inputList = aoc2020.day05.readInputToList("src/main/kotlin/aoc2021/day02/assets/input")

    @Test
    fun testComputeSubmarinePosition() {
        assertEquals(150, computeSubmarinePosition(testInputList))
        assertEquals(1524750, computeSubmarinePosition(inputList))
    }
}