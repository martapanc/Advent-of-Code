package aoc2020.day12

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day12KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2020/day12/input")
    private val input0 = readInputToList("src/main/kotlin/aoc2020/day12/input0")

    @Test
    fun testReadInputToList() {
        println(input0)
        println(input)
    }

    @Test
    fun testNavigateShip() {
        assertEquals(25, navigateShip(input0))
        assertEquals(882, navigateShip(input))
    }

    @Test
    fun testNavigateShipAndWaypoint() {
        assertEquals(286, navigateShipAndWaypoint(input0))
        assertEquals(28885, navigateShipAndWaypoint(input))
    }

    @Test
    fun testRotateShip() {
        assertEquals(CardinalPoints.SOUTH, rotateShip(CardinalPoints.EAST, 90, 'R'))
        assertEquals(CardinalPoints.NORTH, rotateShip(CardinalPoints.EAST, 90, 'L'))
        assertEquals(CardinalPoints.NORTH, rotateShip(CardinalPoints.SOUTH, 180, 'L'))
        assertEquals(CardinalPoints.NORTH, rotateShip(CardinalPoints.SOUTH, 180, 'R'))
        assertEquals(CardinalPoints.SOUTH, rotateShip(CardinalPoints.WEST, 270, 'R'))
        assertEquals(CardinalPoints.NORTH, rotateShip(CardinalPoints.WEST, 270, 'L'))
        assertEquals(CardinalPoints.WEST, rotateShip(CardinalPoints.NORTH, 90, 'L'))
        assertEquals(CardinalPoints.EAST, rotateShip(CardinalPoints.NORTH, 270, 'L'))
        assertEquals(CardinalPoints.NORTH, rotateShip(CardinalPoints.NORTH, 0, 'L'))
        assertEquals(CardinalPoints.WEST, rotateShip(CardinalPoints.WEST, 0, 'R'))
    }
}