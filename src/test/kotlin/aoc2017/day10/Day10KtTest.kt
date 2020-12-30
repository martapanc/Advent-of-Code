package aoc2017.day10

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day10KtTest {

    private val testPath = "src/main/kotlin/aoc2017/day10/test_input"
    private val path = "src/main/kotlin/aoc2017/day10/input"
    private val testInput = readInputToList(testPath)
    private val input = readInputToList(path)

    private val asciiTestInput = convertListToASCII(testPath, true)
    private val asciiInput = convertListToASCII(path, true)

    @Test
    fun readInputToList() {
        println(testInput)
        println(input)
    }

    @Test
    fun testProcessList() {
        assertEquals(12, processList(testInput, 5))
        assertEquals(3770, processList(input, 256))
    }

    @Test
    fun testConvertListToASCII() {
        assertEquals(listOf(51, 44, 52, 44, 49, 44, 53), asciiTestInput)
    }

    @Test
    fun test2() {
        part2("")
    }

    @Test
    fun testProcessListPart2() {
        assertEquals("a2582a3a0e66e6e86e3812dcb672a272", processListPart2(convertListToASCII("")))
//        assertEquals("33efeb34ea91902bb2f59c9920caa6cd", processListPart2(convertListToASCII("AoC 2017")))
//        assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", processListPart2(convertListToASCII("1,2,3")))
//        assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", processListPart2(asciiTestInput))
    }

    @Test
    fun computeXorOfList() {
        assertEquals(64, computeXorOfList(listOf(65, 27, 9, 1, 4, 3, 40, 50, 91, 7, 6, 0, 2, 5, 68, 22)))
    }

    @Test
    fun computeHexString() {
        assertEquals("4007ff", computeHexString(listOf(64, 7, 255)))
    }
}