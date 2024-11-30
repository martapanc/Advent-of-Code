package aoc2016.day02

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day2KtTest {

    private val path = "src/main/kotlin/aoc2016/day02/input"

    @Test
    fun testReadInputAndDial() {
        assertEquals("99332", readInputAndDial(path))
    }

    @Test
    fun testReadInputAndDialPart2() {
        assertEquals("DD483", readInputAndDial(path, isPart2 = true))
    }
}