package aoc2017.day08

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day8KtTest {

    private val input = "src/main/kotlin/aoc2017/day08/input"

    @Test
    fun testReadInputAndFindMaxRegisterValue() {
        assertEquals(7787, readInputAndFindMaxRegisterValue(input))
        assertEquals(8997, readInputAndFindMaxRegisterValue(input, isPartTwo = true))
    }
}