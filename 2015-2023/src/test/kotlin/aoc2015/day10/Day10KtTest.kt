package aoc2015.day10

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day10KtTest {

    private val input = "1113222113"

    @Test
    fun testExpandString() {
        assertEquals(252594, lookAndSayV2(input, 40).length)
        assertEquals(3579328, lookAndSayV2(input, 50).length)
    }
}