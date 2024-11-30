package aoc2015.day19

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import util.readInputLineByLine

internal class Day19KtTest {

    private val input = readInputLineByLine("src/main/kotlin/aoc2015/day19/input")
    private val replacements = input.subList(0, input.size - 2)
    private val molecule = input.last()

    @Test
    fun testCountDistinctMolecules() {
        assertEquals(518, countDistinctMolecules(replacements, molecule))
    }

    @Test
    fun testFindStepsToCreateMolecules() {
        assertEquals(200, findStepsToCreateMolecules(replacements, molecule))
    }
}