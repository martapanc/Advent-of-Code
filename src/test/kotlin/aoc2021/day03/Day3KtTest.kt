package aoc2021.day03

import aoc2020.day05.readInputToList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day3KtTest {

    private val testInputList = readInputToList("src/main/kotlin/aoc2021/day03/assets/input0")
    private val inputList = readInputToList("src/main/kotlin/aoc2021/day03/assets/input")

    @Test
    fun testGetPowerConsumption() {
        assertEquals(198, getPowerConsumption(testInputList))
        assertEquals(3633500, getPowerConsumption(inputList))
    }

    @Test
    fun testGetLifeSupportRating() {
        assertEquals(230, getLifeSupportRating(testInputList))
        assertEquals(4550283, getLifeSupportRating(inputList))
    }
}