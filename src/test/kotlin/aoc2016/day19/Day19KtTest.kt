package aoc2016.day19

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day19KtTest {

    @Test
    fun testFindThievingElf() {
        assertEquals(3, findThievingElf(5))
        assertEquals(1815603, findThievingElf(3004953))
    }
}