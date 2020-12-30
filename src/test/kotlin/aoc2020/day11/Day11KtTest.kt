package aoc2020.day11

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day11KtTest {

    private val input = readInputToMap("src/main/kotlin/aoc2020/day11/input")
    private val input0 = readInputToMap("src/main/kotlin/aoc2020/day11/input0")


    @Test
    fun testReadInputToMap() {
//        println(input0)
//        println(input)
        printSeatMap(input0, 9, 9)
        printSeatMap(input, 90, 93)
    }

    @Test
    fun testRunRoundsPart1() {
        assertEquals(37, runRounds(input0, true))
        assertEquals(2222, runRounds(input, true))
    }

    @Test
    fun testRunRoundsPart2() {
        assertEquals(26, runRounds(input0, false))
        assertEquals(2032, runRounds(input, false))
    }
}