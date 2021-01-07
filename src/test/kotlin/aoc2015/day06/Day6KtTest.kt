package aoc2015.day06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class Day6KtTest {

    private val testInput = readInputToInstructionList("src/main/kotlin/aoc2015/day06/testInput")
    private val input = readInputToInstructionList("src/main/kotlin/aoc2015/day06/input")

    @Test
    fun readInputToInstructionList() {
        assertEquals(3, testInput.size)
        assertEquals(300, input.size)
    }

    @Test
    fun testBuildGrid() {
        assertEquals(998996, buildGrid(testInput))
        assertEquals(400410, buildGrid(input))
    }

    @Test
    fun testBuildGridV2() {
        assertEquals(1001996, buildGridV2(testInput))
        assertEquals(15343601, buildGridV2(input))
    }
}