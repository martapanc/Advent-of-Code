package aoc2015.day14

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day14KtTest {

    private val input = readListOfReindeers("src/main/kotlin/aoc2015/day14/input")
    private val testInput = readListOfReindeers("src/main/kotlin/aoc2015/day14/testInput")

    @Test
    fun readListOfReindeers() {
        assertEquals(2, testInput.size)
        assertEquals(9, input.size)
        assertEquals(RacerReindeer("Vixen", 8, 8, 53), input[0])
    }

    @Test
    fun testRunReindeerRace() {
        assertEquals(1120, runReindeerRace(testInput, 1000))
        assertEquals(2655, runReindeerRace(input, 2503))
    }
}