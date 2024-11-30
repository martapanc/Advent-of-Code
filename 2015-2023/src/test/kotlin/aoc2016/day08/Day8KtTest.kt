package aoc2016.day08

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day8KtTest {

    private val input = "src/main/kotlin/aoc2016/day08/input"

    @Test
    fun testRun2FAProgram() {
        assertEquals(106, run2FAProgram(input))
    }

    @Test
    fun testRotateRowOrColumn() {
        assertEquals(listOf('.', '.', '#', '.', '.'), rotateRowOrColumn(listOf('#', '.', '.', '.', '.'), 2))
    }
}