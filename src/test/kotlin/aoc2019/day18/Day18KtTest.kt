package aoc2019.day18

import aoc2018.day18.readInputToMap
import aoc2020.day20.Coord
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day18KtTest {

    private val basePath = "src/main/kotlin/aoc2019/day18/input/"
    private val testInput1 = readInputToMap("${basePath}testInput1")
    private val testInput2 = readInputToMap("${basePath}testInput2")
    private val testInput3 = readInputToMap("${basePath}testInput3")
    private val testInput4 = readInputToMap("${basePath}testInput4")
    private val input = readInputToMap("${basePath}input")

    @Test
    fun testReadInputToGrid() {
        assertEquals(5*24, testInput1.size)
        assertEquals(9*17, testInput3.size)
        assertEquals(81*81, input.size)
    }

    @Test
    fun testFindRealDistanceToItem() {
        val initial = Coord(8, 4)
        assertEquals(5, findRealDistanceToItem(initial, 'c', testInput3))
        assertEquals(8, findRealDistanceToItem(initial, 'C', testInput3))
        assertEquals(6, findRealDistanceToItem(initial, 'B', testInput3))
        assertEquals(10, findRealDistanceToItem(initial, 'l', testInput3))
        assertEquals(24, findRealDistanceToItem(Coord(6, 3), 'C', testInput2))
    }

    @Test
    fun testIsDoorOrIsKey() {
        assertTrue(isKey('a'))
        assertTrue(isKey('n'))
        assertFalse(isKey('A'))
        assertFalse(isKey('K'))
        assertFalse(isDoor('a'))
        assertFalse(isDoor('p'))
        assertTrue(isDoor('J'))
        assertTrue(isDoor('R'))
    }

    @Test
    fun testFindReachableItems() {
        val keys = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')
        val doors = keys.map { it.toUpperCase() }
        assertEquals(doors, findReachableItems(Coord(8, 4), testInput3).first.map { it.char }.sorted())
        assertEquals(keys, findReachableItems(Coord(8, 4), testInput3).second.map { it.char }.sorted())

        assertEquals(listOf('B', 'C'), findReachableItems(Coord(6, 3), testInput2).first.map { it.char }.sorted())
        assertEquals(listOf('a', 'b'), findReachableItems(Coord(6, 3), testInput2).second.map { it.char }.sorted())

        assertEquals(listOf('D', 'K', 'Q', 'R', 'S', 'T', 'U', 'V', 'X'), findReachableItems(Coord(40, 40), input).first.map { it.char }.sorted())
        assertEquals(listOf('d', 'm', 'o', 'q', 'r', 'v'), findReachableItems(Coord(40, 40), input).second.map { it.char }.sorted())
    }
}