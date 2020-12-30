package aoc2020.day17

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day17KtTest {

    private val input = readInputToMap("src/main/kotlin/aoc2020/day17/input")
    private val input0 = readInputToMap("src/main/kotlin/aoc2020/day17/input0")

    private val input4d = readInputToMap4d("src/main/kotlin/aoc2020/day17/input")
    private val input04d = readInputToMap4d("src/main/kotlin/aoc2020/day17/input0")

    @Test
    fun testReadInputToMap() {
        println(input0)
        println(input)
    }

    @Test
    fun testRunCycles() {
        assertEquals(112, runCycles(input0))
        assertEquals(322, runCycles(input))
    }

    @Test
    fun testRunCyclesPart2() {
        assertEquals(848, run4dCycles(input04d))
        assertEquals(2000, run4dCycles(input4d))
    }
}