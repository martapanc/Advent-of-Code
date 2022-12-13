package aoc2022.day13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day13KtTest {

    private val testInput0 = readInputToPairs("src/main/kotlin/aoc2022/day13/assets/input0")
    private val testInput = readInputToPairs("src/main/kotlin/aoc2022/day13/assets/input")

    @Test
    fun testPart1() {
        assertEquals(13, part1(testInput0))
        assertEquals(6086, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(140, part2(testInput0))
        assertEquals(27930, part2(testInput))
    }

    @Test
    fun testCountNumbers() {
        assertEquals(0, countNumbers("[]"))
        assertEquals(1, countNumbers("[[1]]"))
        assertEquals(2, countNumbers("[[1],4]"))
        assertEquals(3, countNumbers("[[9,[4,9]]]"))
        assertEquals(5, countNumbers("[1,1,5,1,1]"))
        assertEquals(6, countNumbers("[1,1,5,[9],1,1]"))
        assertEquals(8, countNumbers("[[[10,2,[5,7,10,3]]],[4,[],[],1]]"))
    }

    @Test
    fun testCountOpenBraces() {
        assertEquals(4, countOpenBraces("[[[]][]]"))
        assertEquals(1, countOpenBraces("[]"))
        assertEquals(0, countOpenBraces(""))
    }
}
