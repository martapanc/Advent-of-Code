package aoc2017.day11

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import util.readInputLineByLine

internal class Day11KtTest {

    private val input = readInputLineByLine("src/main/kotlin/aoc2017/day11/input")[0].split(",")

    @Test
    fun testFindShortestDistance() {
        assertEquals(3, findShortestDistance("ne,ne,ne".split(",")))
        assertEquals(0, findShortestDistance("ne,ne,sw,sw".split(",")))
        assertEquals(2, findShortestDistance("ne,ne,s,s".split(",")))
        assertEquals(3, findShortestDistance("se,sw,se,sw,sw".split(",")))
        assertEquals(796, findShortestDistance(input))
    }

    @Test
    fun testFindFurthestPosition() {
        assertEquals(1585, findFurthestPosition(input))
    }
}