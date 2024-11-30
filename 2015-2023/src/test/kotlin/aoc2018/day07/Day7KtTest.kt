package aoc2018.day07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day7KtTest {

    private val testInput = readInputNumbers("src/main/kotlin/aoc2018/day07/testInput")
    private val input = readInputNumbers("src/main/kotlin/aoc2018/day07/input")

    @Test
    fun testReadInputNumbers() {
        assertEquals(7, testInput.size)
        println(testInput)
        assertEquals(101, input.size)
    }

    @Test
    fun testDetermineStepOrder() {
        assertEquals("CABDFE", determineStepOrder(testInput))
        assertEquals("ACBDESULXKYZIMNTFGWJVPOHRQ", determineStepOrder(input))
    }

    @Test
    fun testCompleteAllSteps() {
        assertEquals(15, completeAllSteps(testInput, 2, 0))
        assertEquals(980, completeAllSteps(input, 5, 60))
    }
}