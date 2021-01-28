package aoc2017.day22

import aoc2020.day20.Coord
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day22KtTest {

    private val path = "src/main/kotlin/aoc2017/day22/input"
    private val input = readInputToMap(path)

    @Test
    fun testCountInfectionBurst() {
        val rawInput = readInputLineByLine(path)
        val initial = Coord(rawInput.size / 2, rawInput.first().length / 2)
        assertEquals(5411, countInfectionBurst(initial, input))
    }

}