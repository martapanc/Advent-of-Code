package aoc2017.day10

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day10KtTest {

    private val testInput = readInputToList("src/main/kotlin/aoc2017/day10/test_input")
    private val input = readInputToList("src/main/kotlin/aoc2017/day10/input")

    @Test
    fun readInputToList() {
        println(testInput)
        println(input)
    }

    @Test
    fun testProcessList() {
        assertEquals(12, processList(testInput, 5))
        assertEquals(37703770, processList(input, 256))
    }
}