package aoc2015.day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day3KtTest {

    private val input = readInputLineByLine("src/main/kotlin/aoc2015/day03/input")[0]

    @Test
    fun testProcessTrip() {
        assertEquals(2, processTrip("^v^v^v^v^v"))
        assertEquals(2572, processTrip(input))
    }

    @Test
    fun testProcessTripPart2() {
        assertEquals(3, processTripPart2("^v"))
        assertEquals(11, processTripPart2("^v^v^v^v^v"))
        assertEquals(2631, processTripPart2(input))
    }
}