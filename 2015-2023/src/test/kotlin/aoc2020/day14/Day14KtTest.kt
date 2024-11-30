package aoc2020.day14

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day14KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2020/day14/input")
    private val input0 = readInputToList("src/main/kotlin/aoc2020/day14/input0")
    private val input1 = readInputToList("src/main/kotlin/aoc2020/day14/input1")

    @Test
    fun readInputToList() {
        println(input0)
        println(input)
        println(input1)
    }

    @Test
    fun testRunInitProgramme() {
        assertEquals(165, runInitProgramme(input0))
        assertEquals(13476250121721, runInitProgramme(input))
    }

    @Test
    fun testBinaryToDecimalLong() {
        assertEquals(0, binaryToDecimal("0"))
        assertEquals(1, binaryToDecimal("1"))
        assertEquals(1, binaryToDecimal("000000001"))
        assertEquals(2, binaryToDecimal("010"))
        assertEquals(4, binaryToDecimal("100"))
        assertEquals(38762125873, binaryToDecimal("100100000110011001110001101000110001"))
    }

    @Test
    fun testRunInitProgramme2() {
        assertEquals(208, runInitProgramme2(input1))
        assertEquals(4463708436768, runInitProgramme2(input))
    }

    @Test
    fun generateListOfBinaries() {
        assertEquals(listOf("0", "1"), generateListOfBinaries(1))
        assertEquals(listOf("00", "01", "10", "11"), generateListOfBinaries(2))
        assertEquals(listOf("000", "001", "010", "011", "100", "101", "110", "111"), generateListOfBinaries(3))
    }
}