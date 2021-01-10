package aoc2019.day17

import aoc2019.day09.readInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

internal class Day17KtTest {

    companion object {
        private const val INPUT_PATH = "src/main/kotlin/aoc2019/day17/input1"
        private val READ_INPUT: ArrayList<Long> = readInput(INPUT_PATH)
        val input = readInput(INPUT_PATH)
    }

    @Test
    fun testReadInput() {
        assertEquals(1505, input.size)
    }

    @Test
    fun testComputeIntersections() {
        assertEquals(7720, computeIntersections(input))
    }

    @Test
    fun testProcessInputPart2() {
//        assertEquals(123, processInputPart2(READ_INPUT, Pair(2, 0)))
    }
}