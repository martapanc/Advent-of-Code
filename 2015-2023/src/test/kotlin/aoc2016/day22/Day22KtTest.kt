package aoc2016.day22

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day22KtTest {

    private val input = readInputToMap("src/main/kotlin/aoc2016/day22/input")

    @Test
    fun testReadInputToMap() {
        assertEquals(960, input.size)
    }

    @Test
    fun testCountViableNodePairs() {
        assertEquals(952, countViableNodePairs(input))
    }

    @Test
    fun testComputeFewestStepsToMoveData() {
        assertEquals(181, computeFewestStepsToMoveData(input))
    }
}