package aoc2022.day03

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import util.readInputLineByLine

internal class Day3KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2022/day03/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2022/day03/assets/input")

    @Test
    fun computePrioritySum() {
        assertEquals(157, computePrioritySum(testInput0))
        assertEquals(8085, computePrioritySum(testInput))
    }
}
