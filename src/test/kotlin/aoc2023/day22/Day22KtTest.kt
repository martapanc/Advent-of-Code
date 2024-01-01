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
        assertEquals(5, part1(testInput))
        assertEquals(8085, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(70, part2(testInput))
        assertEquals(2515, part2(input))
    }

    @Test
    fun testBrickCanFall() {
        val brickOnBottom = Brick(Coord3d(1, 1, 1), Coord3d(1, 2, 1))

        assertFalse(brickCanFall(Brick(Coord3d(0, 0, 1), Coord3d(0, 3, 1)), listOf(brickOnBottom)))
        assertTrue(brickCanFall(Brick(Coord3d(0, 0, 3), Coord3d(0, 3, 3)), listOf(brickOnBottom)))
        assertTrue(brickCanFall(Brick(Coord3d(0, 0, 2), Coord3d(1, 0, 2)), listOf(brickOnBottom)))
        assertFalse(brickCanFall(Brick(Coord3d(0, 1, 2), Coord3d(1, 1, 2)), listOf(brickOnBottom)))
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
}
