package aoc2015.day06

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


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
}