package aoc2017.day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day5KtTest {

    private val testInput = readInputToList("src/main/kotlin/aoc2017/day05/test_input")
    private val input = readInputToList("src/main/kotlin/aoc2017/day05/input")

    @Test
    fun readInputToList() {
        assertEquals(5, testInput.size)
        assertEquals(1097, input.size)
    }

    @Test
    fun exitMaze() {
        assertEquals(5, exitMaze(testInput))
        assertEquals(396086, exitMaze(input))
    }

    @Test
    fun exitMazePart2() {
        assertEquals(10, exitMaze(testInput, true))
        assertEquals(28675390, exitMaze(input, true))
    }
}