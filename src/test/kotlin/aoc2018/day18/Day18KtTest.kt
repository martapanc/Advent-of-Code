package aoc2018.day18

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day18KtTest {

    private val testInput = readInputToMap("src/main/kotlin/aoc2018/day18/testInput")
    private val input = readInputToMap("src/main/kotlin/aoc2018/day18/input")

    @Test
    fun testReadInputToMap() {
        assertEquals(100, testInput.size)
        assertEquals(2500, input.size)
    }

    @Test
    fun testPlayGameOfLumberyards() {
        assertEquals(1147, playGameOfLumberyards(testInput, 10))
        assertEquals(653184, playGameOfLumberyards(input, 10))
    }

    @Test
    fun testPlayLongGameOfLumberyards() {
        assertEquals(169106, playLongGameOfLumberyards(input, 1000000000))
    }
}