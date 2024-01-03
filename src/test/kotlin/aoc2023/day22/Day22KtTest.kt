package aoc2023.day22

import aoc2022.day18.Coord3d
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.Coord
import util.readInputLineByLine

internal class Day22KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day22/assets/input0")
    private val testInput = parse(testInputLines)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day22/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
//        assertEquals(5, part1(testInput))
//        assertEquals(473, part1_2(input))
    }

    @Test
    fun testPart2() {
//        assertEquals(61045, part2(input))
    }

    @Test
    fun testBrickCanFall() {
        val bricks = listOf(Brick(Coord3d(1, 1, 1), Coord3d(1, 2, 1)))

        assertFalse(Brick(Coord3d(0, 0, 1), Coord3d(0, 3, 1)).canFall(bricks))
        assertTrue(Brick(Coord3d(0, 0, 3), Coord3d(0, 3, 3)).canFall(bricks))
        assertTrue(Brick(Coord3d(0, 0, 2), Coord3d(1, 0, 2)).canFall(bricks))
        assertFalse(Brick(Coord3d(0, 1, 2), Coord3d(1, 1, 2)).canFall(bricks))
    }

    @Test
    fun testMakeBrickFall() {
        val brickOnBottom = Brick(Coord3d(1, 1, 1), Coord3d(1, 2, 1))
        val brick = Brick(Coord3d(0, 0, 3), Coord3d(0, 3, 3))
        val fallenBrick = Brick(Coord3d(0, 0, 1), Coord3d(0, 3, 1))

        assertEquals(listOf(brickOnBottom, fallenBrick), makeBrickFall(brick, listOf(brickOnBottom, brick)))
    }

    @Test
    fun testBrick() {
        val testBrick = Brick(Coord3d(1, 1, 3), Coord3d(1, 1, 1))
        assertFalse(testBrick.occupiesLayerZ(0))
        assertTrue(testBrick.occupiesLayerZ(1))
        assertTrue(testBrick.occupiesLayerZ(2))
        assertTrue(testBrick.occupiesLayerZ(3))
        assertFalse(testBrick.occupiesLayerZ(4))
    }
    @Test
    fun testCellsOccupiedOnLayer() {
        val bricks = listOf(
            Brick(Coord3d(0, 0, 1), Coord3d(0, 2, 1)), // 1
            Brick(Coord3d(1, 1, 1), Coord3d(2, 1, 1)), // 2
            Brick(Coord3d(2, 2, 1), Coord3d(2, 2, 3))  // 3
        )
        val result = setOf(
            Coord(0, 0), // 1
            Coord(0, 1), // 1
            Coord(0, 2), // 1
            Coord(1, 1), // 2
            Coord(2, 1), // 2
            Coord(2, 2), // 3
        )
        assertEquals(result, cellsOccupiedOnLayer(bricks))
    }

    @Test
    fun testGetSupportingBricks() {
        val b1 = Brick(Coord3d(0, 0, 1), Coord3d(2, 0, 1))
        val b2 = Brick(Coord3d(0, 2, 1), Coord3d(2, 2, 1))
        val b3 = Brick(Coord3d(1, 1, 1), Coord3d(3, 1, 1))

        val bricks = listOf(b1, b2, b3)
        val testBrick1 = Brick(Coord3d(0, 0, 2), Coord3d(0, 2, 2))
        val testBrick2 = Brick(Coord3d(2, 1, 2), Coord3d(2, 1, 2))

        assertEquals(setOf(b1, b2), testBrick1.getSupportingBricks(bricks))
        assertEquals(setOf(b3), testBrick2.getSupportingBricks(bricks))
    }
}
