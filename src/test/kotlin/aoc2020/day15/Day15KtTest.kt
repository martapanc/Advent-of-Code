package aoc2020.day15

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day15KtTest {

    private val input = readInputToList("6,13,1,15,2,0")
    private val input0 = readInputToList("0,3,6")
    private val input1 = readInputToList("1,3,2")
    private val input2 = readInputToList("2,1,3")

    @Test
    fun readInputToList() {
        println(input0)
        println(input1)
        println(input)
    }

    @Test
    fun testSpeakNumbers() {
        val limit = 2020
        assertEquals(436, speakNumbers(input0, limit))
        assertEquals(1, speakNumbers(input1, limit))
        assertEquals(10, speakNumbers(input2, limit))
        assertEquals(1194, speakNumbers(input, limit))
    }

    @Test
    fun testSpeakNumbers2() {
        var limit = 2020
        assertEquals(436, speakNumbersV2(input0, limit))
        assertEquals(1, speakNumbersV2(input1, limit))
        assertEquals(10, speakNumbersV2(input2, limit))
        assertEquals(1194, speakNumbersV2(input, limit))

        limit = 30000000
        assertEquals(175594, speakNumbersV2(input0, limit))
        assertEquals(2578, speakNumbersV2(input1, limit))
        assertEquals(3544142, speakNumbersV2(input2, limit))
        assertEquals(48710, speakNumbersV2(input, limit))
    }
}