package aoc2022.day09

import aoc2020.day20.Coord
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day9KtTest {

    private val testInput0 = readInputToMovementList("src/main/kotlin/aoc2022/day09/assets/input0")
    private val testInput1 = readInputToMovementList("src/main/kotlin/aoc2022/day09/assets/input1")
    private val testInput = readInputToMovementList("src/main/kotlin/aoc2022/day09/assets/input")

    @Test
    fun testPart1() {
        assertEquals(13, part1(testInput0))
        assertEquals(5902, part1(testInput))
    }

    @Test
    fun testPart2() {
//        assertEquals(1, part2(testInput0))
        assertEquals(36, part2(testInput1))
//        assertEquals(2515, part2(testInput))
    }

    @Test
    fun testMoveByOne() {
        assertEquals(Coord(1, 0), moveByOne(Coord(0, 0), Direction.RIGHT))
        assertEquals(Coord(0, 1), moveByOne(Coord(0, 0), Direction.UP))
        assertEquals(Coord(-1, 0), moveByOne(Coord(0, 0), Direction.LEFT))
        assertEquals(Coord(0, -1), moveByOne(Coord(0, 0), Direction.DOWN))
    }

    @Test
    fun testMoveDiagonally() {
        assertEquals(Coord(2, 1), moveDiagonally(Coord(1, 0), Coord(2, 2)))
        assertEquals(Coord(2, 1), moveDiagonally(Coord(3, 0), Coord(1, 1)))
        assertEquals(Coord(0, 1), moveDiagonally(Coord(1, 2), Coord(0, 0)))
        assertEquals(Coord(2, 1), moveDiagonally(Coord(1, 2), Coord(2, 0)))
    }

    @Test
    fun testTailFollowsHead() {
        assertEquals(Coord(0, 0), tailFollowsHead(Coord(0, 0), Coord(0, 0)))

        assertEquals(Coord(0, 0), tailFollowsHead(Coord(0, 0), Coord(1, 0)))
        assertEquals(Coord(0, 0), tailFollowsHead(Coord(0, 0), Coord(0, -1)))
        assertEquals(Coord(0, 0), tailFollowsHead(Coord(0, 0), Coord(1, 1)))

        assertEquals(Coord(-1, 0), tailFollowsHead(Coord(0, 0), Coord(-2, 0)))
        assertEquals(Coord(0, 1), tailFollowsHead(Coord(0, 0), Coord(0, 2)))
        assertEquals(Coord(-1, 0), tailFollowsHead(Coord(0, 0), Coord(-2, 0)))
        assertEquals(Coord(0, 1), tailFollowsHead(Coord(0, 0), Coord(0, 2)))

        assertEquals(Coord(1, 1), tailFollowsHead(Coord(0, 0), Coord(1, 2)))
        assertEquals(Coord(0, 0), tailFollowsHead(Coord(1, 1), Coord(0, -1)))
    }
}
