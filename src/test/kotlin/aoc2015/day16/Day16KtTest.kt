package aoc2015.day16

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day16KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2015/day16/input")

    @Test
    fun testFindTheRightAuntSue() {
        assertEquals(40, findTheRightAuntSue(input))
    }
}