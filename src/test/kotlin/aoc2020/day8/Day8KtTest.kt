package aoc2020.day8

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day8KtTest {

    private val input = readInputToMap("src/main/kotlin/aoc2020/day8/input")
    private val input0 = readInputToMap("src/main/kotlin/aoc2020/day8/input0")
    private val input0Modif = readInputToMap("src/main/kotlin/aoc2020/day8/input0_modif")

    @Test
    fun readInputToMap() {
        println(input0)
        println(input)
        println(input0Modif)
    }

    @Test
    fun playTheGame() {
        assertEquals(GameOver(5, false), playTheGame(input0))
        assertEquals(GameOver(8, true), playTheGame(input0Modif))
        assertEquals(GameOver(1200, false), playTheGame(input))
    }

    @Test
    fun fixInstructionAndPlay() {
        assertEquals(8, fixInstructionAndPlay(input0))
        assertEquals(1023, fixInstructionAndPlay(input))
    }
}