package aoc2018.day22

import aoc2020.day20.Coord
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day22KtTest {

    private val testDepth = 510
    private val testTarget = Coord(10, 10)

    private val depth = 4002
    private val target = Coord(5, 746)

    @Test
    fun testComputeRegionGeologicIndex() {
        val day22 = Day22(testTarget, testDepth)
        assertEquals(0, day22.computeRegionGeologicIndex(Coord(0, 0)))
        assertEquals(48271, day22.computeRegionGeologicIndex(Coord(0, 1)))
        assertEquals(16807, day22.computeRegionGeologicIndex(Coord(1, 0)))
        assertEquals(145722555, day22.computeRegionGeologicIndex(Coord(1, 1)))
        assertEquals(0, day22.computeRegionGeologicIndex(testTarget))
    }

    @Test
    fun testComputeRegionErosionLevel() {
        val day22 = Day22(testTarget, testDepth)
        assertEquals(510, day22.computeRegionErosionLevel(Coord(0, 0)))
        assertEquals(8415, day22.computeRegionErosionLevel(Coord(0, 1)))
        assertEquals(17317, day22.computeRegionErosionLevel(Coord(1, 0)))
        assertEquals(1805, day22.computeRegionErosionLevel(Coord(1, 1)))
        assertEquals(510, day22.computeRegionErosionLevel(testTarget))
    }

    @Test
    fun testComputeTotalRiskLevel() {
        assertEquals(114, Day22(testTarget, testDepth).computeTotalRiskLevel())
        assertEquals(4479, Day22(target, depth).computeTotalRiskLevel())
    }
}