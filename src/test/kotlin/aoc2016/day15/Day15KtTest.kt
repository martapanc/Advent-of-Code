package aoc2016.day15

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day15KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2016/day15/input")
    private val testInput = listOf(Disc(1, 5, 4), Disc(2, 2, 1))

    @Test
    fun testReadInputToList() {
        assertEquals(6, input.size)
        assertEquals(2, testInput.size)
    }

    @Test
    fun testPlayGameOfDiscs() {
        assertEquals(5, playGameOfDiscs(testInput))
        assertEquals(203660, playGameOfDiscs(input))
        assertEquals(2408135, playGameOfDiscs(input, isPart2 = true))
    }
}