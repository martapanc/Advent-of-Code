package aoc2016.day18

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day18KtTest {

    private val input =
        "^.^^^..^^...^.^..^^^^^.....^...^^^..^^^^.^^.^^^^^^^^.^^.^^^^...^^...^^^^.^.^..^^..^..^.^^.^.^......."
    private val testInput1 = "..^^."
    private val testInput2 = ".^^.^.^^^^"

    @Test
    fun testComputeTraps() {
        assertEquals(6, computeTraps(testInput1, 3))
        assertEquals(38, computeTraps(testInput2, 10))
        assertEquals(1913, computeTraps(input, 40))
        assertEquals(19993564, computeTraps(input, 400000))
    }
}