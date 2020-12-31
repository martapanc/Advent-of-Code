package aoc2017.day5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day5KtTest {

    private val testInput = readInputToList("src/main/kotlin/aoc2017/day5/test_input")
    private val input = readInputToList("src/main/kotlin/aoc2017/day5/input")

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
        assertEquals(10, exitMazePart2(testInput))
        assertEquals(28675390, exitMazePart2(input))
    }
}