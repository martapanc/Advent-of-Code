package aoc2023.day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.Coord
import util.readInputLineByLine
import util.readInputToMap

internal class Day11KtTest {

    private val testInput = readInputToMap("src/main/kotlin/aoc2023/day11/assets/input0")

    private val input = readInputToMap("src/main/kotlin/aoc2023/day11/assets/input")

    @Test
    fun testPart1() {
        assertEquals(374, part1(testInput))
        assertEquals(9445168, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(1030, part2(testInput, 9))
        assertEquals(8410, part2(testInput, 99))
        assertEquals(742305960572, part2(input, 999999))
    }

    @Test
    fun testCountEmptyRowsAndColsBetween() {
        val emptyCols = findEmptyColumns(testInput)
        val emptyRows = findEmptyRows(testInput)

        assertEquals(1, countEmptyRowsAndColsBetween(Coord(3, 0), Coord(0, 2), emptyCols, emptyRows))
        assertEquals(2, countEmptyRowsAndColsBetween(Coord(3, 0), Coord(1, 5), emptyCols, emptyRows))
        assertEquals(5, countEmptyRowsAndColsBetween(Coord(0, 2), Coord(9, 9), emptyCols, emptyRows))
    }
}
