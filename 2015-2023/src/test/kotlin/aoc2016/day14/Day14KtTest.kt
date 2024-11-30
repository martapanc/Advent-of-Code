package aoc2016.day14

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day14KtTest {

    private val testInput = "abc"
    private val input = "ahsbgdzn"

    @Test
    fun testGetIndexProducing64thKey() {
        assertEquals(22728, getIndexProducing64thKey(testInput))
        assertEquals(23890, getIndexProducing64thKey(input))
    }

    @Test
    fun testGetIndexProducing64thKeyPart2() {
//        assertEquals(22551, getIndexProducing64thKey(testInput, true))
        assertEquals(22696, getIndexProducing64thKey(input, true))
    }
}