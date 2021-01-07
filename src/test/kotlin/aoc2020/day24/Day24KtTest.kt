package aoc2020.day24

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day24KtTest {

    private val input0 = readInputToList("src/main/kotlin/aoc2020/day24/input0")
    private val input = readInputToList("src/main/kotlin/aoc2020/day24/input")

    @Test
    fun readInputToList() {
        println(input0)
        assertEquals(20, input0.size)
        assertEquals(597, input.size)
    }

    @Test
    fun findTiles() {
        assertEquals(10, findTilesAndCountBlackOnes(input0))
        assertEquals(549, findTilesAndCountBlackOnes(input))
    }

    @Test
    fun playTilesOfLife() {
        assertEquals(10, playGameOfTiles(input0, 0))
        assertEquals(15, playGameOfTiles(input0, 1))
        assertEquals(12, playGameOfTiles(input0, 2))
        assertEquals(25, playGameOfTiles(input0, 3))
        assertEquals(2208, playGameOfTiles(input0, 100))

        assertEquals(4147, playGameOfTiles(input, 100))
    }
}