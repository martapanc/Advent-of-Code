package aoc2020.day6

import aoc2020.day4.readInputFile
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day6KtTest {

    private val input = readInputFile("src/main/kotlin/aoc2020/day6/input")
    private val input0 = readInputFile("src/main/kotlin/aoc2020/day6/input0")

    @Test
    fun readInput() {
        println(input)
        println(input0)
    }

    @Test
    fun testCountUniqueLetters() {
        assertEquals(2, countUniqueChars("i x i i"))
        assertEquals(3, countUniqueChars("ab ac"))
        assertEquals(3, countUniqueChars("abc"))
        assertEquals(6, countUniqueChars("abcx abcy abcz"))
    }

    @Test
    fun testCountCommonLetters() {
        assertEquals(0, countCommonChars("i x i i"))
        assertEquals(1, countCommonChars("ab ac"))
        assertEquals(3, countCommonChars("abc"))
        assertEquals(3, countCommonChars("abcx abcy abcz"))
    }

    @Test
    fun testCountTotalAnswers() {
        assertEquals(17, countTotalAnswers(input0, ::countUniqueChars))
        assertEquals(6768, countTotalAnswers(input, ::countUniqueChars))
    }

    @Test
    fun testCountCommonAnswers() {
        assertEquals(9, countTotalAnswers(input0, ::countCommonChars))
        assertEquals(3489, countTotalAnswers(input, ::countCommonChars))
    }
}