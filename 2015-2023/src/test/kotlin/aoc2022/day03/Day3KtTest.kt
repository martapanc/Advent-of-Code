package aoc2022.day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day3KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2022/day03/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2022/day03/assets/input")

    @Test
    fun computePrioritySumPart1() {
        assertEquals(157, computePrioritySumPart1(testInput0))
        assertEquals(8085, computePrioritySumPart1(testInput))
    }

    @Test
    fun computePrioritySumPart2() {
        assertEquals(70, computePrioritySumPart2(testInput0))
        assertEquals(2515, computePrioritySumPart2(testInput))
    }
}
