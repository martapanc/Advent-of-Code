package aoc2020.day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day10KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2020/day10/input")
    private val input0 = readInputToList("src/main/kotlin/aoc2020/day10/input0")
    private val input1 = readInputToList("src/main/kotlin/aoc2020/day10/input1")

    @Test
    fun testReadInputToList() {
        println(input0)
        println(input1)
        println(input)
    }

    @Test
    fun testFindJoltsDifferences() {
        assertEquals(35, findJoltsDifferences(input0))
        assertEquals(220, findJoltsDifferences(input1))
        assertEquals(1984, findJoltsDifferences(input))
    }

    @Test
    fun testPrintSubsets() {
        assertEquals(listOf(listOf<Int>()), generateSubsets(mutableListOf()))
        assertEquals(listOf(listOf(), listOf(1)), generateSubsets(mutableListOf(1)))
        assertEquals(4, generateSubsets(mutableListOf(1, 2)).size)
        assertEquals(8, generateSubsets(mutableListOf(1, 2, 3)).size)
        println(generateSubsets(mutableListOf(1, 2, 3, 4)))
    }

    @Test
    fun testGenerateValidSubsets() {
        assertEquals(7, getNumberOfValidSubsets(listOf(1, 2, 3)))
        assertEquals(7, getNumberOfValidSubsets(listOf(4, 5, 6)))
        assertEquals(13, getNumberOfValidSubsets((listOf(4, 5, 6, 7))))
        assertEquals(24, getNumberOfValidSubsets((listOf(4, 5, 6, 7, 8))))
        assertEquals(44, getNumberOfValidSubsets((listOf(4, 5, 6, 7, 8, 9))))
        assertEquals(81, getNumberOfValidSubsets((listOf(4, 5, 6, 7, 8, 9, 10))))
    }

    @Test
    fun testFindPermutations() {
        assertEquals(8, findPermutations(input0))
        assertEquals(19208, findPermutations(input1))
        assertEquals(3543369523456, findPermutations(input))
    }
}