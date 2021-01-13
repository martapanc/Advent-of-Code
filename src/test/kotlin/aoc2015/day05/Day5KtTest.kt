package aoc2015.day05

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day5KtTest {

    private val input = "src/main/kotlin/aoc2015/day05/input"

    @Test
    fun testIsNiceString() {
        assertTrue(isNiceString("ugknbfddgicrmopn"))
        assertTrue(isNiceString("aaa"))
        assertFalse(isNiceString("jchzalrnumimnmhp"))
        assertFalse(isNiceString("haegwjzuvuyypxyu"))
        assertFalse(isNiceString("dvszwmarrgswjxmb"))
    }

    @Test
    fun testIsNiceStringPart2() {
        assertTrue(isNiceStringPart2("qjhvhtzxzqqjkmpb"))
        assertTrue(isNiceStringPart2("xxyxx"))
        assertFalse(isNiceStringPart2("uurcxstgmygtbstg"))
        assertFalse(isNiceStringPart2("ieodomkazucvgmuy"))
    }

    @Test
    fun testCountNiceString() {
        assertEquals(236, countNiceStrings(input, ::isNiceString))
        assertEquals(51, countNiceStrings(input, ::isNiceStringPart2))
    }
}