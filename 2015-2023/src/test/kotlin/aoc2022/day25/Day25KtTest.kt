package aoc2022.day25

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day25KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2022/day25/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2022/day25/assets/input")

    @Test
    fun testPart1() {
        assertEquals("1===", part1(listOf("200", "1==")))
        assertEquals("2=-1=0", part1(testInput0))
        assertEquals("2-0-0=1-0=2====20=-2", part1(testInput))
    }

    @Test
    fun testConvertListAndSum() {
        assertEquals(4890, convertListAndSum(testInput0))
        assertEquals(34168440432447, convertListAndSum(testInput))
    }

    @Test
    fun testSnafuToDecimal() {
        assertEquals(1, snafuToDecimal("1"))
        assertEquals(2, snafuToDecimal("2"))
        assertEquals(3, snafuToDecimal("1="))
        assertEquals(4, snafuToDecimal("1-"))
        assertEquals(5, snafuToDecimal("10"))
        assertEquals(6, snafuToDecimal("11"))
        assertEquals(7, snafuToDecimal("12"))
        assertEquals(8, snafuToDecimal("2="))
        assertEquals(9, snafuToDecimal("2-"))
        assertEquals(10, snafuToDecimal("20"))
        assertEquals(12, snafuToDecimal("22"))
        assertEquals(13, snafuToDecimal("1=="))
        assertEquals(15, snafuToDecimal("1=0"))
        assertEquals(20, snafuToDecimal("1-0"))
        assertEquals(62, snafuToDecimal("222"))
        assertEquals(63, snafuToDecimal("1==="))
        assertEquals(2022, snafuToDecimal("1=11-2"))
        assertEquals(12345, snafuToDecimal("1-0---0"))
        assertEquals(314159265, snafuToDecimal("1121-1110-1=0"))
        assertEquals(34168440432447, snafuToDecimal("2-0-0=1-0=2====20=-2"))
    }

    @Test
    fun testDecimalToBase5() {
        assertEquals("10", decimalToBase5(5))
        assertEquals("11", decimalToBase5(6))
        assertEquals("20", decimalToBase5(10))
        assertEquals("100", decimalToBase5(25))
    }
}
