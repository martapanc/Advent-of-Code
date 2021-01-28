package aoc2015.day01

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import util.readInputLineByLine

internal class Day1KtTest {

    private val input = readInputLineByLine("src/main/kotlin/aoc2015/day01/input")[0]
    @Test
    fun testRunPart1() {
        assertEquals(0, runPart1("(())"))
        assertEquals(0, runPart1("()()"))
        assertEquals(3, runPart1("(()(()("))
        assertEquals(-3, runPart1(")))"))
        assertEquals(280, runPart1(input))
    }

    @Test
    fun runPart2() {
        assertEquals(1, runPart2(")"))
        assertEquals(5, runPart2("()())"))
        assertEquals(1797, runPart2(input))
    }
}