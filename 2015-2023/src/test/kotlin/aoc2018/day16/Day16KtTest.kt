package aoc2018.day16

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day16KtTest {

    private val input1 = readInputPart1("src/main/kotlin/aoc2018/day16/input_1")
    private val input2 = readInputPart2("src/main/kotlin/aoc2018/day16/input_2")

    @Test
    fun testReadInput() {
        assertEquals(782, input1.size)
        assertEquals(946, input2.size)
    }

    @Test
    fun testCountSamplesMatchingOpcodes() {
        assertEquals(542, countSamplesMatchingOpcodes(input1))
    }

    @Test
    fun testRunProgram() {
        assertEquals(575, runProgram(input2))
    }
}