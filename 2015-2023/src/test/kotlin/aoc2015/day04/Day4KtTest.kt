package aoc2015.day04

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day4KtTest {

    @Test
    fun testFindMinMd5Hash() {
        assertEquals(609043, findMinMd5Hash("abcdef"))
        assertEquals(1048970, findMinMd5Hash("pqrstuv"))
        assertEquals(346386, findMinMd5Hash("iwrupvqb"))
        assertEquals(9958218, findMinMd5Hash("iwrupvqb", isPart2 = true))
    }
}
