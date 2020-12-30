package aoc2020.day13

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day13KtTest {

    private val inputRaw = "37,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,587,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,13,19,x,x,x,23,x,x,x,x,x,29,x,733,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,17"
    private val input0Raw = "7,13,x,x,59,x,31,19"
    private val input = readInputToList(inputRaw)
    private val input0 = readInputToList(input0Raw)
    private val input1 = readInputToList("17,x,13,19")
    private val input2 = readInputToList("67,7,59,61")
    private val input3 = readInputToList("67,x,7,59,61")
    private val input4 = readInputToList("67,7,x,59,61")
    private val input5 = readInputToList("1789,37,47,1889")

    @Test
    fun testReadInputToList() {
        println(input0)
        println(readInputToListExcludeX(inputRaw))
        println(input)
    }

    @Test
    fun findFirstAvailableBus() {
        assertEquals(295, findFirstAvailableBus(939, readInputToListExcludeX(input0Raw)))
        assertEquals(2935, findFirstAvailableBus(1005526, readInputToListExcludeX(inputRaw)))
    }

    @Test
    fun findEarliestTimestampMathEdition() {
        assertEquals(1068781, findEarliestTimestamp(input0))
        assertEquals(3417, findEarliestTimestamp(input1))
        assertEquals(754018, findEarliestTimestamp(input2))
        assertEquals(779210, findEarliestTimestamp(input3))
        assertEquals(1261476, findEarliestTimestamp(input4))
        assertEquals(1202161486, findEarliestTimestamp(input5))
        assertEquals(836024966345345, findEarliestTimestamp(input))
    }
}