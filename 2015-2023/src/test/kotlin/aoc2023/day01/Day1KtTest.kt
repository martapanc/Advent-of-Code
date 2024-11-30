package aoc2023.day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine


internal class Day1KtTest {

    private val testInputList0 = readInputLineByLine("src/main/kotlin/aoc2023/day01/assets/input0")
    private val testInputList1 = readInputLineByLine("src/main/kotlin/aoc2023/day01/assets/input1")
    private val inputList = readInputLineByLine("src/main/kotlin/aoc2023/day01/assets/input")

    @Test
    fun testPart1() {
        assertEquals(142, part1(testInputList0))
        assertEquals(55712, part1(inputList))
    }

    @Test
    fun testPart2() {
        assertEquals(281, part2(testInputList1))
        assertEquals(55413, part2(inputList))
    }

    @Test
    fun testReplaceStringNumbersWithDigits() {
        assertEquals("823", replaceStringNumberWithDigits("eightwothree"))
        assertEquals("49872", replaceStringNumberWithDigits("4nineightseven2"))
        assertEquals("4259", replaceStringNumberWithDigits("425nine"))
    }
}
