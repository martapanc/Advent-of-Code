package aoc2015.day13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day13KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2015/day13/input")

    @Test
    fun readInputToList() {
        assertEquals(56, input.size)
        assertEquals(Neighbors(Person.A, Person.B, -2), input[0])
    }

    @Test
    fun testFindMostEfficientArrangement() {
        assertEquals(664, findMostEfficientArrangement(input))
    }

    @Test
    fun testFindMostEfficientArrangementIncludingME() {
        assertEquals(640, findMostEfficientArrangement(input, true))
    }
}