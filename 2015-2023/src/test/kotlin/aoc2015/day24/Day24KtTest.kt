package aoc2015.day24

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import util.readInputLineByLine

internal class Day24KtTest {

    private val input = readInputLineByLine("src/main/kotlin/aoc2015/day24/input").map { it.toInt() }

    @Test
    fun testSolve() {
        assertEquals(11846773891, balancePresents(input))
        assertEquals(80393059, balancePresents(input, true))
    }
}