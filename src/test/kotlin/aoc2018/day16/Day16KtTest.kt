package aoc2018.day16

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day16KtTest {

    private val input1 = readInputPart1("src/main/kotlin/aoc2018/day16/input_1")

    @Test
    fun testReadInputPart1() {
        assertEquals(782, input1.size)
    }
}