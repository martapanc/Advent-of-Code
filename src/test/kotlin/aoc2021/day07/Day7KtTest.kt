package aoc2021.day07

import aoc2019.day02.readInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day7KtTest {

    val input0 = readInput("src/main/kotlin/aoc2021/day07/assets/input0")
    val input = readInput("src/main/kotlin/aoc2021/day07/assets/input")

    @Test
    fun getMinimumFuel() {
        assertEquals(37, getMinimumFuel(input0))
        assertEquals(339321, getMinimumFuel(input))
    }

    @Test
    fun getMinimumFuelPart2() {
        assertEquals(168, getMinimumFuelPart2(input0))
        assertEquals(95476244, getMinimumFuelPart2(input))
    }
}