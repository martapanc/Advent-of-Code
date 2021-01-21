package aoc2015.day07

import Day1
import TestDay7
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.not

internal class Day7KtTest {

    private val input = "src/main/kotlin/aoc2015/day07/input"
    private val testInput = "src/main/kotlin/aoc2015/day07/testInput"

    @Test
    fun testReadInputToList() {
        assertEquals(72, Day1(testInput).readInputAndRun())
        assertEquals(956, Day1(input).readInputAndRun())
    }

    @Test
    fun testPart1() {
        assertEquals(72, TestDay7(testInput).readInputAndRun())
        assertEquals(956, TestDay7(input).readInputAndRun())
    }

    @Test
    fun testBitOperations() {
        assertEquals(72, 123 and 456)
        assertEquals(507, 123 or 456)
        assertEquals(492, 123 shl 2)
        assertEquals(114, 456 shr 2)
        assertEquals(65412, not(123))
        assertEquals(65079, not(456))
    }
}