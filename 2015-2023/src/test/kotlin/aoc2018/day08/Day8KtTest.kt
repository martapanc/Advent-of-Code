package aoc2018.day08

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day8KtTest {

    @Test
    fun test_sumMetadata() {
        val input1 = "src/main/kotlin/aoc2018/day08/input/input"
        val input2 = "src/main/kotlin/aoc2018/day08/input/input2"
        val input3 = "src/main/kotlin/aoc2018/day08/input/input3"
        val input4 = "src/main/kotlin/aoc2018/day08/input/input4"
        assertEquals(37262, sumMetadata(input1))
        assertEquals(138, sumMetadata(input2))
        assertEquals(104, sumMetadata(input4))
        assertEquals(99, sumMetadata(input3))
    }

    @Test
    fun test_getRootScore() {
        val input1 = "src/main/kotlin/aoc2018/day08/input/input"
        val input2 = "src/main/kotlin/aoc2018/day08/input/input2"
        assertEquals(20839, getRootScore(input1))
        assertEquals(66, getRootScore(input2))
    }
}