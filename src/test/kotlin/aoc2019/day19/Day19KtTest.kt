package aoc2019.day19

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day19KtTest {

    private val path = "src/main/kotlin/aoc2019/day19/input"

    @Test
    fun testCountPointsInTractorBeam() {
        assertEquals(114, countPointsInTractorBeam(path))
    }

    @Test
    fun testFindTopLeftCoordOfSquareOfSide() {
        assertEquals(130021, findTopLeftCoordOfSquareOfSide(path, 2))
        assertEquals(230037, findTopLeftCoordOfSquareOfSide(path, 3))
        assertEquals(330053, findTopLeftCoordOfSquareOfSide(path, 4))
        assertEquals(10671712, findTopLeftCoordOfSquareOfSide(path, 100, areaSide = 1900, filterFirst = 1000))
    }
}