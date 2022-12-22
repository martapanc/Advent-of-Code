package aoc2022.day22

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

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
        assertEquals(70, part2(testInput0))
        assertEquals(2515, part2(testInput))
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
}
