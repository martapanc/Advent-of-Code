package aoc2016.day09

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day9Test {

    private val testInput1 = "A(1x5)BC"
    private val testInput2 = "(3x3)XYZ"
    private val testInput3 = "A(2x2)BCD(2x2)EFG"
    private val testInput4 = "X(8x2)(3x3)ABCY"
    private val testInput5 = "(6x1)(1x3)A"
    private val testInput6 = "(27x12)(20x12)(13x14)(7x10)(1x12)A"
    private val testInput7 = "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"
    private val input = readInputLineByLine("src/main/kotlin/aoc2016/day09/input")[0]

    @Test
    fun testDecompressSequence() {
        assertEquals(7, decompressSequence(testInput1))
        assertEquals(9, decompressSequence(testInput2))
        assertEquals(11, decompressSequence(testInput3))
        assertEquals(18, decompressSequence(testInput4))
        assertEquals(6, decompressSequence(testInput5))
        assertEquals(6, decompressSequence(testInput5))
        assertEquals(120765, decompressSequence(input))

        assertEquals(7, decompressSequence(testInput1, true))
        assertEquals(9, decompressSequence(testInput2, true))
        assertEquals(20, decompressSequence(testInput4, true))
        assertEquals(241920, decompressSequence(testInput6, true))
        assertEquals(445, decompressSequence(testInput7, true))
        assertEquals(11658395076, decompressSequence(input, true))
    }
}