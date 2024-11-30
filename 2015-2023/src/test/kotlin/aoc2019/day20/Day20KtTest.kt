package aoc2019.day20

import util.Coord
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputToMap

internal class Day20KtTest {

    private val input = readInputToMap("src/main/kotlin/aoc2019/day20/input")
    private val testInput = readInputToMap("src/main/kotlin/aoc2019/day20/testInput")

    @Test
    fun testFindPathFromPortalToPortals() {
        assertEquals(
            mapOf(Coord(21, 9) to 13, Coord(17, 9) to 13),
            findPathFromPortalToPortals(Coord(19, 2), 'A', testInput)
        )
        assertEquals(
            mapOf(Coord(33, 11) to 9),
            findPathFromPortalToPortals(Coord(26, 13), 'N', testInput)
        )
    }

    @Test
    fun testFindCoordinatesOfCellsNextToPortals() {
        assertEquals(21, findCoordinatesOfCellsNextToPortals(testInput).size)
        assertEquals(Portal(Coord(19,2), 'A'), findCoordinatesOfCellsNextToPortals(testInput)[0])
       // assertEquals(56, findCoordinatesOfCellsNextToPortals(input).size)
        assertEquals(Portal(Coord(49, 2), 'E'), findCoordinatesOfCellsNextToPortals(input)[1])
    }

    @Test
    fun testMapPortalsToPortals() {
        mapPortalsToPortals(input)
        mapPortalsToPortals(testInput)
    }
}
