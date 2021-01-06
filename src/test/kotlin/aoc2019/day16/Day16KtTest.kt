package aoc2019.day16

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day16KtTest {

    companion object {
        private const val path = "src/main/kotlin/aoc2019/day16/input"
        private val INPUT = readInputToString(path)
        private const val INPUT2 = "12345678"
        private const val INPUT3 = "48226158"
        private const val INPUT4 = "34040438"
        private const val INPUT5 = "03415518"
        private const val INPUT6 = "01029498"
        private const val INPUT7 = "80871224585914546619083218645595"
        private const val INPUT8 = "19617804207202209144916044189917"
        private const val INPUT9 = "03036732577212944063491565474664"
        private const val INPUT10 = "02935109699940807407585447034323"
    }

    @Test
    fun readInputToString() {
        assertEquals(650, INPUT.length)
        assertEquals(8, INPUT2.length)
    }

    @Test
    fun testComputeSignal() {
        assertEquals(INPUT3, computeSignal(INPUT2, 1))
        assertEquals(INPUT4, computeSignal(INPUT2, 2))
        assertEquals(INPUT5, computeSignal(INPUT2, 3))
        assertEquals(INPUT6, computeSignal(INPUT2, 4))
        assertEquals("24176176", computeSignal(INPUT7, 100))
        assertEquals("73745418", computeSignal(INPUT8, 100))
        assertEquals("77038830", computeSignal(INPUT, 100))
    }

    @Test
    fun testComputeSignalPart2() {
        assertEquals("84462026", part2(INPUT9))
        assertEquals("78725270", part2(INPUT10))
        assertEquals("28135104", part2(INPUT))
    }

    @Test
    fun testRepeatInput10000Times() {
        assertEquals(80000, repeatInput10000Times(INPUT2).length)
        assertEquals(6500000, repeatInput10000Times(INPUT).length)
    }
}