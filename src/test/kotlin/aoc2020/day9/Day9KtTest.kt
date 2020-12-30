package aoc2020.day9

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day9KtTest {

    private val input = readInputToList("src/main/kotlin/aoc2020/day9/input")
    private val input0 = readInputToList("src/main/kotlin/aoc2020/day9/input0")
    //removed all numbers after N and some previous larger numbers:
    private val inputEdit = readInputToList("src/main/kotlin/aoc2020/day9/input")

    @Test
    fun readInputToList() {
        println(input0)
        println(input)
    }

    @Test
    fun testFindFirstNumberNotTheSumOfPreviousN() {
        assertEquals(127, findFirstNumberNotTheSumOfPreviousKNumbers(input0, 5))
        assertEquals(10884537, findFirstNumberNotTheSumOfPreviousKNumbers(input, 25))
    }

    @Test
    fun testFindContiguousNumberGivingN() {
        assertEquals(62, findContiguousNumberGivingN(input0, 127))
        assertEquals(1261309, findContiguousNumberGivingN(inputEdit, 10884537))
    }
}