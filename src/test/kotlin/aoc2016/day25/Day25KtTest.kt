package aoc2016.day25

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day25KtTest {

    private val input = "src/main/kotlin/aoc2016/day25/input"

    @Test
    fun testRunAssembunnyCode() {
        assertEquals(192, runAssembunnyCode(9, 282))
    }
}