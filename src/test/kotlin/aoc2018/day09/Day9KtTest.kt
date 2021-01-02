package aoc2018.day09

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day9KtTest {

    private val testInput0 = MarbleGame(25, 9)
    private val testInput1 = MarbleGame(1618, 10)
    private val testInput2 = MarbleGame(7999, 13)
    private val testInput3 = MarbleGame(1104, 17)
    private val testInput4 = MarbleGame(6111, 21)
    private val testInput5 = MarbleGame(5807, 30)
    private val input = MarbleGame(71035, 479)

    @Test
    fun testPlayMarbleGame() {
        assertEquals(32, playMarbleGame(testInput0.highestMarble, testInput0.players))
        assertEquals(8317, playMarbleGame(testInput1.highestMarble, testInput1.players))
        assertEquals(146373, playMarbleGame(testInput2.highestMarble, testInput2.players))
        assertEquals(2764, playMarbleGame(testInput3.highestMarble, testInput3.players))
        assertEquals(54718, playMarbleGame(testInput4.highestMarble, testInput4.players))
        assertEquals(37305, playMarbleGame(testInput5.highestMarble, testInput5.players))
        assertEquals(367634, playMarbleGame(input.highestMarble, input.players))
    }

    @Test
    fun testPlayMarbleGameV2() {
        assertEquals(367634, playMarbleGameV2(input.highestMarble, input.players))
        assertEquals(3020072891, playMarbleGameV2(input.highestMarble * 100, input.players))
    }
}

class MarbleGame(val highestMarble: Int, val players: Int)