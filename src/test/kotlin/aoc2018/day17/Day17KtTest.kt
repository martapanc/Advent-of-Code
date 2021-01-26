package aoc2018.day17

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day17KtTest {
    private val input = readInputToMap("src/main/kotlin/aoc2018/day17/input")
    private val testInput = readInputToMap("src/main/kotlin/aoc2018/day17/testInput")

    private val gridPath = "src/main/kotlin/aoc2018/day17/grid"

    @Test
    fun testRunPart1() {
//        printTileMap(input)
        assertEquals(34541, countFlowingAndStillWater(gridPath).first)
    }

    @Test
    fun testRunPart2() {
        assertEquals(28000, countFlowingAndStillWater(gridPath).second)
    }
}