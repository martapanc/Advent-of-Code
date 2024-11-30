package aoc2020.day20

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day20KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2020/day20/input")
    private val input0 = readInputToList("src/main/kotlin/aoc2020/day20/input0")

    private val inputPuzzle = readImageInput("src/main/kotlin/aoc2020/day20/input_puzzle")
    private val inputPuzzle0 = readImageInput("src/main/kotlin/aoc2020/day20/input_puzzle0")

    @Test
    fun readInputToList() {
        assertEquals(9, input0.size)
        assertEquals(144, input.size)
        assertEquals(9216, inputPuzzle.size)
    }

    @Test
    fun findCornerTiles() {
        assertEquals(18262194216271, findCornersCheckProduct(input))
        assertEquals(20899048083289, findCornersCheckProduct(input0))
    }

    @Test
    fun rotateTile90DegreesClockwise() {
        rotateOrFlip(input0[0], rotate = true, deg = 90, flip = '0')
        rotateOrFlip(input0[0], rotate = true, deg = 180, flip = '0')
        rotateOrFlip(input0[0], rotate = true, deg = 270, flip = '0')
        rotateOrFlip(input0[0], rotate = false, deg = 0, flip = 'v')
        rotateOrFlip(input0[0], rotate = false, deg = 0, flip = 'h')
        rotateOrFlip(input0[0], rotate = true, deg = 90, flip = 'h')
        rotateOrFlip(input0[0], rotate = true, deg = 90, flip = 'v')
    }

    @Test
    fun findSeaMonsters() {
        assertEquals(2, findSeaMonsters(inputPuzzle0, 24))
        assertEquals(41, findSeaMonsters(inputPuzzle, 96, 3, true))
    }

    @Test
    fun countHashesExcludingBorders() {
        assertEquals(303, countHashesExcludingBorders(input0))
        assertEquals(2638, countHashesExcludingBorders(input))
    }
}