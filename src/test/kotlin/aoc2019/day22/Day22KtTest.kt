package aoc2019.day22

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day22KtTest {

    private val input = "src/main/kotlin/aoc2019/day22/input"

    @Test
    fun testRunNaiveShuffler() {
        assertEquals(6696, runNaiveShuffler(input))
    }

    @Test
    fun testRunInsaneShuffler() {
        assertEquals(93750418158025, runInsaneShuffler(input))
    }
}