package aoc2016.day01

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import util.readInputLineByLine

internal class Day1KtTest {

    private val input = readInputLineByLine("src/main/kotlin/aoc2016/day01/input")[0]

    @Test
    fun testComputeDistance() {
        assertEquals(5, computeDistance("R2, L3"))
        assertEquals(2, computeDistance("R2, R2, R2"))
        assertEquals(12, computeDistance("R5, L5, R5, R3"))
        assertEquals(161, computeDistance(input))
    }

    @Test
    fun testComputeDistancePart2() {
        assertEquals(4, computeDistancePart2("R8, R4, R4, R8"))
        assertEquals(110, computeDistancePart2(input))
    }
}