package aoc2017.day14

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day14KtTest {

    private val testInput = "flqrgnkx"
    private val input = "xlqgujun"

    @Test
    fun defragmentDisk() {
        assertEquals(8108, defragmentDisk(testInput))
        assertEquals(8204, defragmentDisk(input))
    }
}