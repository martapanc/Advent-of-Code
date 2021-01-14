package aoc2017.day16

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day16KtTest {

    private val input = "src/main/kotlin/aoc2017/day16/input"
    private val testInput = "src/main/kotlin/aoc2017/day16/testInput"

    @Test
    fun testReadInputToListAndCompute() {
        assertEquals("baedc", readInputToListAndCompute(testInput, "abcde"))
        assertEquals("kpbodeajhlicngmf", readInputToListAndCompute(input, "abcdefghijklmnop"))

        assertEquals("kpbodeajhlicngmf", readInputToListAndCompute(input, "abcdefghijklmnop", 1))
        assertEquals("ahgpjdkcbfmneloi", readInputToListAndCompute(input, "abcdefghijklmnop", 1_000_000_000))
    }
}