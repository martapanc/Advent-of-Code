package aoc2017.day22

import aoc2020.day20.Coord
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day22KtTest {

    private val path = "src/main/kotlin/aoc2017/day22/input"
    private val input = readInputToMap(path)
    private val rawInput = readInputLineByLine(path)
    private val initial = Coord(rawInput.size / 2, rawInput.first().length / 2)

    @Test
    fun testCountInfectionBurst() {
        assertEquals(5411, countInfectionBurst(initial, input))
    }

    @Test
    fun testCountInfectionBurstPart2() {
        assertEquals(2511416, countInfectionBurstPart2(initial, input))
    }
}