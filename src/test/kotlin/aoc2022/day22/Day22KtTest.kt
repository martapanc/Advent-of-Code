package aoc2022.day22

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.Coord

internal class Day22KtTest {

    private val testInput0 = "src/main/kotlin/aoc2022/day22/assets/input0"
    private val testInput = "src/main/kotlin/aoc2022/day22/assets/input"

    private val day20Sample = Day22(testInput0)
    private val day20 = Day22(testInput)

    @Test
    fun testPart1() {
        assertEquals(6032, day20Sample.part1())
        assertEquals(30552, day20.part1())
    }

    @Test
    fun testPart2() {
        assertEquals(5031, day20Sample.part2(true))
        assertEquals(184106, day20.part2())
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

    @Test
    fun testWrapAroundCube() {
        assertEquals(Coord(0, 147), Coord(50, 2).wrapAroundCube(Facing.WEST).pos)
        assertEquals(Coord(50, 9), Coord(0, 140).wrapAroundCube(Facing.WEST).pos)
        assertEquals(Coord(50, 0), Coord(0, 149).wrapAroundCube(Facing.WEST).pos)

        assertEquals(Coord(99, 145), Coord(149, 4).wrapAroundCube(Facing.EAST).pos)
        assertEquals(Coord(149, 49), Coord(99, 100).wrapAroundCube(Facing.EAST).pos)

        assertEquals(Coord(17, 199), Coord(117, 0).wrapAroundCube(Facing.NORTH).pos)
        assertEquals(Coord(142, 0), Coord(42, 199).wrapAroundCube(Facing.SOUTH).pos)

        assertEquals(Coord(0, 198), Coord(98, 0).wrapAroundCube(Facing.NORTH).pos)
        assertEquals(Coord(55, 0), Coord(0, 155).wrapAroundCube(Facing.WEST).pos)

        assertEquals(Coord(99, 52), Coord(102, 49).wrapAroundCube(Facing.SOUTH).pos)
        assertEquals(Coord(147, 49), Coord(99, 97).wrapAroundCube(Facing.EAST).pos)

        assertEquals(Coord(49, 159), Coord(59, 149).wrapAroundCube(Facing.SOUTH).pos)
        assertEquals(Coord(51, 149), Coord(49, 151).wrapAroundCube(Facing.EAST).pos)

        assertEquals(Coord(25, 100), Coord(50, 75).wrapAroundCube(Facing.WEST).pos)
        assertEquals(Coord(50, 62), Coord(12, 100).wrapAroundCube(Facing.NORTH).pos)
    }
}
