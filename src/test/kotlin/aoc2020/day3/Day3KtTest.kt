package aoc2020.day3

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day3KtTest {

    private val testInput = readInputFileToMap("src/main/kotlin/aoc2020/day3/input0")
    private val input = readInputFileToMap("src/main/kotlin/aoc2020/day3/input")

    @Test
    fun testReadInputFileToMap() {
        println(testInput)
        println(input)
    }

    @Test
    fun testCountTreesInMap() {
        assertEquals(7, countTreesInMap(testInput, 400, 400))
    }
}