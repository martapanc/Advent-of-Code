package aoc2016.day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day5KtTest {

    private val testInput = "abc"
    private val input = "wtnhxymk"

    @Test
    fun testFindPassword() {
        assertEquals("18f47a30", findPassword(testInput))
        assertEquals("2414bc77", findPassword(input))
    }

    @Test
    fun testFindPasswordPart2() {
        assertEquals("05ace8e3", findPasswordPart2(testInput))
        assertEquals("437e60fc", findPasswordPart2(input))
    }
}