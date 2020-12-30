package aoc2020.day5

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day5KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2020/day5/input")

    @Test
    fun readInput() {
        println(input)
    }

    @Test
    fun testFromStringToRowAndColumn() {
        assertArrayEquals(arrayOf(44, 5, 357), fromStringToRowAndColumn("FBFBBFFRLR"))
        assertArrayEquals(arrayOf(70, 7, 567), fromStringToRowAndColumn("BFFFBBFRRR"))
        assertArrayEquals(arrayOf(14, 7, 119), fromStringToRowAndColumn("FFFBBBFRRR"))
        assertArrayEquals(arrayOf(102, 4, 820), fromStringToRowAndColumn("BBFFBBFRLL"))
    }

    @Test
    fun testFindHighestSeatId() {
        assertEquals(996, findHighestSeatId(input))
    }

    @Test
    fun testMapSeats() {
        mapSeats(input)
    }
}