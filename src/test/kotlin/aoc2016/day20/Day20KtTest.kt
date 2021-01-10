package aoc2016.day20

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day20KtTest {

    val input = readInputToRangeList("src/main/kotlin/aoc2016/day20/input")

    @Test
    fun testReadInputToRangeList() {
        assertEquals(945, input.size)
        assertEquals(Pair(881357481, 892360003).toString(), input[2].toString())
    }

    @Test
    fun testFindMinimumIPValue() {
        assertEquals(14975795, findMinimumIPValue(input))
    }

    @Test
    fun testCountValidIps() {
        assertEquals(101, countValidIps(input))
    }
}