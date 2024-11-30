package aoc2018.day25

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day25KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2018/day25/input")
    private val testInput1 = readInputToList("src/main/kotlin/aoc2018/day25/testInput1")
    private val testInput2 = readInputToList("src/main/kotlin/aoc2018/day25/testInput2")
    private val testInput3 = readInputToList("src/main/kotlin/aoc2018/day25/testInput3")
    private val testInput4 = readInputToList("src/main/kotlin/aoc2018/day25/testInput4")
    private val testInput5 = readInputToList("src/main/kotlin/aoc2018/day25/testInput5")

    @Test
    fun testReadInputToList() {
        assertEquals(1257, input.size)
        assertEquals(10, testInput1.size)
    }

    @Test
    fun testGroupConstellations() {
        assertEquals(4, groupConstellations(testInput1))
        assertEquals(2, groupConstellations(testInput4))
        assertEquals(1, groupConstellations(testInput5))
        assertEquals(3, groupConstellations(testInput2))
        assertEquals(8, groupConstellations(testInput3))
        assertEquals(457, groupConstellations(input))
    }
}