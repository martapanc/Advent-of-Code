package aoc2021.day01

import aoc2017.day05.readInputToList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day1KtTest {

    private val testInputList = readInputToList("src/main/kotlin/aoc2021/day01/assets/input0")
    private val inputList = readInputToList("src/main/kotlin/aoc2021/day01/assets/input")


    @Test
    fun testFindIncreasingDepthValues() {
        assertEquals(7, findIncreasingDepthValues(testInputList))
        assertEquals(1527, findIncreasingDepthValues(inputList))
    }

    @Test
    fun testFindIncreasingThreeMeasurementDepthValues() {
        assertEquals(5, findIncreasingThreeMeasurementDepthValues(ArrayList(testInputList)))
        assertEquals(1575, findIncreasingThreeMeasurementDepthValues(ArrayList(inputList)))
    }
}