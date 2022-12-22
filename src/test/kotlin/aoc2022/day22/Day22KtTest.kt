package aoc2022.day22

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.Coord

internal class Day22KtTest {

    private val testInput0 = "src/main/kotlin/aoc2022/day22/assets/input0"
    private val testInput = "src/main/kotlin/aoc2022/day22/assets/input"

    @Test
    fun testPart1() {
        assertEquals(6032, part1(testInput0))
        assertEquals(30552, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(5031, part2(testInput0))
//        assertEquals(2515, part2(testInput))
    }

    @Test
    fun testRotate() {
        assertEquals(Facing.NORTH, Facing.EAST.rotate(Rotation.LEFT))
        assertEquals(Facing.WEST, Facing.NORTH.rotate(Rotation.LEFT))
        assertEquals(Facing.SOUTH, Facing.WEST.rotate(Rotation.LEFT))
        assertEquals(Facing.EAST, Facing.SOUTH.rotate(Rotation.LEFT))
        assertEquals(Facing.SOUTH, Facing.EAST.rotate(Rotation.RIGHT))
        assertEquals(Facing.EAST, Facing.NORTH.rotate(Rotation.RIGHT))
        assertEquals(Facing.NORTH, Facing.WEST.rotate(Rotation.RIGHT))
        assertEquals(Facing.WEST, Facing.SOUTH.rotate(Rotation.RIGHT))
    }

    @Test
    fun testWrapAroundCubeExample() {
        assertEquals(Coord(0, 4), Coord(11, 0).wrapAroundCubeExample(Facing.NORTH).pos)
        assertEquals(Coord(9, 0), Coord(2, 4).wrapAroundCubeExample(Facing.NORTH).pos)

        assertEquals(Coord(10, 11), Coord(1, 7).wrapAroundCubeExample(Facing.SOUTH).pos)
        assertEquals(Coord(2, 7), Coord(9, 11).wrapAroundCubeExample(Facing.SOUTH).pos)

        assertEquals(Coord(15, 10), Coord(11, 1).wrapAroundCubeExample(Facing.EAST).pos)
        assertEquals(Coord(11, 3), Coord(15, 8).wrapAroundCubeExample(Facing.EAST).pos)

        assertEquals(Coord(15, 11), Coord(0, 4).wrapAroundCubeExample(Facing.WEST).pos)
        assertEquals(Coord(0, 6), Coord(13, 11).wrapAroundCubeExample(Facing.SOUTH).pos)

        assertEquals(Coord(5, 4), Coord(8, 1).wrapAroundCubeExample(Facing.WEST).pos)
        assertEquals(Coord(8, 2), Coord(6, 4).wrapAroundCubeExample(Facing.NORTH).pos)

        assertEquals(Coord(12, 8), Coord(11, 7).wrapAroundCubeExample(Facing.EAST).pos)
        assertEquals(Coord(11, 6), Coord(13, 8).wrapAroundCubeExample(Facing.NORTH).pos)

        assertEquals(Coord(8, 10), Coord(5, 7).wrapAroundCubeExample(Facing.SOUTH).pos)
        assertEquals(Coord(7, 7), Coord(8, 8).wrapAroundCubeExample(Facing.WEST).pos)
    }
}
