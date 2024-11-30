package aoc2020.day22

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day22KtTest {

    private val input0 = readInputToLists("src/main/kotlin/aoc2020/day22/input0")
    private val input = readInputToLists("src/main/kotlin/aoc2020/day22/input")

    @Test
    fun readInputToLists() {
        assertEquals(listOf(9, 2, 6, 3, 1), input0.first)
        assertEquals(5, input0.second.size)
        assertEquals(25, input.first.size)
        assertEquals(25, input.second.size)
    }

    @Test
    fun playGame() {
        assertEquals(306, playGame(input0))
        assertEquals(33631, playGame(input))
    }

    @Test
    fun playGameV2() {
        assertEquals(291, playGameV2(input0))
        assertEquals(105, playGameV2(Pair(listOf(43, 19), listOf(2, 29, 14))))
        assertEquals(33469, playGameV2(input))
    }
}