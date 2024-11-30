package aoc2016.day16

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day16KtTest {

    private val input = "01111001100111011"

    @Test
    fun testExpandString() {
        assertEquals("100", expandString("1"))
        assertEquals("001", expandString("0"))
        assertEquals("11111000000", expandString("11111"))
        assertEquals("1111000010100101011110000", expandString("111100001010"))
    }

    @Test
    fun testComputeChecksum() {
        assertEquals("01100", computeChecksum("10000", 20))
        assertEquals("11111000111110000", computeChecksum(input))
        assertEquals("10111100110110100", computeChecksum(input, diskSize = 35651584))
    }
}