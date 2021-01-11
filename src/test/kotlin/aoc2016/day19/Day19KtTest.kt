package aoc2016.day19

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day19KtTest {

    @Test
    fun testFindThievingElf() {
        assertEquals(3, findThievingElf(5))
        assertEquals(1815603, findThievingElf(3004953))
    }

    @Test
    fun testFindThievingElfPart2() {
        assertEquals(2, findThievingElfPart2(5))
        assertEquals(9, findThievingElfPart2(9))
        assertEquals(1410630, findThievingElfPart2(3004953))
    }
}