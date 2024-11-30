package aoc2021.day23

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day23KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2021/day23/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2021/day23/assets/input")

    private val testInput0part2 = readInputLineByLine("src/main/kotlin/aoc2021/day23/assets/input0_2")
    private val testInputPart2 = readInputLineByLine("src/main/kotlin/aoc2021/day23/assets/input_2")

    @Test
    fun testPart1() {
        assertEquals(12521, computeBestPath(testInput0))
        assertEquals(13556, computeBestPath(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(44169, computeBestPath(testInput0part2))
        assertEquals(54200, computeBestPath(testInputPart2))
    }
}

