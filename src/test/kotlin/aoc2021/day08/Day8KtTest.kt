package aoc2021.day08

import aoc2020.day05.readInputToList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day8KtTest {

    val input0 = readInputToList("src/main/kotlin/aoc2021/day08/assets/input0")
    val input1 = readInputToList("src/main/kotlin/aoc2021/day08/assets/input1")
    val input = readInputToList("src/main/kotlin/aoc2021/day08/assets/input")

    @Test
    fun countUniqueSegments() {
        assertEquals(26, countUniqueSegments(readToListsOfOutputs(input0)))
        assertEquals(288, countUniqueSegments(readToListsOfOutputs(input)))
    }

    @Test
    fun decodePatterns() {
        assertEquals(5353, decodePatterns(input1))
        assertEquals(61229, decodePatterns(input0))
        assertEquals(940724, decodePatterns(input))
    }
}