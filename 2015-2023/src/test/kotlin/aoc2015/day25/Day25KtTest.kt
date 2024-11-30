package aoc2015.day25

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day25KtTest {

    private val inputX = 3019
    private val inputY = 3010

    @Test
    fun testComputeValueOfXAtX() {
        assertEquals(28, computeValueOfNAtX(7))
        assertEquals(15, computeValueOfNAtX(5))
        assertEquals(4558690, computeValueOfNAtX(inputX))
    }

    @Test
    fun testComputeValueOfNAtY() {
        assertEquals(16, computeValueOfNAtY(6))
        assertEquals(29, computeValueOfNAtY(8))
        assertEquals(4555672, computeValueOfNAtY(inputX))
        assertEquals(4528546, computeValueOfNAtY(inputY))
    }

    @Test
    fun testComputeValueOfNAtXY() {
        assertEquals(26, computeValueOfNAtXY(5, 3))
        assertEquals(30, computeValueOfNAtXY(2, 7))
        assertEquals(1, computeValueOfNAtXY(1, 1))
        assertEquals(25, computeValueOfNAtXY(4, 4))
        assertEquals(18168397, computeValueOfNAtXY(inputX, inputY))
    }

    @Test
    fun testComputeNthCode() {
        val initialCode: Long = 20151125
        assertEquals(initialCode, computeNthCode(initialCode, 1))
        assertEquals(31916031, computeNthCode(initialCode, 2))
        assertEquals(18749137, computeNthCode(initialCode, 3))
        assertEquals(17289845, computeNthCode(initialCode, 6))
        assertEquals(77061, computeNthCode(initialCode, 11))
        assertEquals(31663883, computeNthCode(initialCode, computeValueOfNAtXY(6, 5)))
        assertEquals(8997277, computeNthCode(initialCode, computeValueOfNAtXY(inputX, inputY)))
    }
}