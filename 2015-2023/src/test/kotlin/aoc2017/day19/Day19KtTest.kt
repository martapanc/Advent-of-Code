package aoc2017.day19

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day19KtTest {

    private val input = "src/main/kotlin/aoc2017/day19/input.txt"

    @Test
    fun testComputePath() {
        assertEquals("EOCZQMURF", Day19(input).computeLettersVisited())
        assertEquals(16312, Day19(input).computeTotalSteps())
    }
}