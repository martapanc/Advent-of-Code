package aoc2020.day7

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day7KtTest {

    private val input = readInputToMap("src/main/kotlin/aoc2020/day7/input")
    private val input0 = readInputToMap("src/main/kotlin/aoc2020/day7/input0")
    private val input2= readInputToMap("src/main/kotlin/aoc2020/day7/input2")
    private val start = "shiny gold"

    @Test
    fun readInputToList() {
        println(input0)
        println(input)

        println(buildMapFromContainedBags(input0))
        println(buildMapFromContainedBags(input))
    }

    @Test
    fun testFindContainingBags() {
        assertEquals(4, findContainingBags(buildMapFromContainedBags(input0), start))
        assertEquals(179, findContainingBags(buildMapFromContainedBags(input), start))
    }

    @Test
    fun testFindContainedIndividualBags() {
        assertEquals(32, findContainedIndividualBags(input0, start))
        assertEquals(126, findContainedIndividualBags(input2, start))
        assertEquals(18925, findContainedIndividualBags(input, start))
    }
}