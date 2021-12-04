package aoc2021.day04

import aoc2020.day05.readInputToList
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day4KtTest {

    val input0 = readInputToList("src/main/kotlin/aoc2021/day04/assets/input0")
    val input = readInputToList("src/main/kotlin/aoc2021/day04/assets/input")


    @Test
    fun playSquidBingo() {
        val (testNumbersDrawn, testBoardList) = readInputToSquidBingo(input0)
        assertEquals(4512, playSquidBingo(testNumbersDrawn, testBoardList))

        val (numbersDrawn, boardList) = readInputToSquidBingo(input)
        assertEquals(21607, playSquidBingo(numbersDrawn, boardList))
    }

    @Test
    fun isBingoWin() {
        val (_, testBoardList) = readInputToSquidBingo(input0)
        assertFalse(isBingoWin(testBoardList[0], 4).isWin)

        for (i: Int in 0 until 5) {
            testBoardList[0].bingoNumbers[i].drawn = true
        }
        val bingoWin = isBingoWin(testBoardList[0], 17)
        assertTrue(bingoWin.isWin)
        assertEquals(17, bingoWin.lastNumDrawn)
        assertEquals(17, bingoWin.lastNumDrawn)
        assertEquals(237, bingoWin.sumOfUnmarkedNumbers)
    }
}