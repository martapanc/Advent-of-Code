package aoc2023.day10

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine
import util.readInputToMap

internal class Day10KtTest {

    private val testInput0 = readInputToMap("src/main/kotlin/aoc2023/day10/assets/input0")
    private val testInput1 = readInputToMap("src/main/kotlin/aoc2023/day10/assets/input1")

    private val input = readInputToMap("src/main/kotlin/aoc2023/day10/assets/input")

    @Test
    fun testPart1() {
        assertEquals(4, part1(testInput0))
        assertEquals(8, part1(testInput1))
        assertEquals(6717, part1(input))
    }

    @Test
    fun testPart2() {
//        assertEquals(70, part2(testInput1))
//        assertEquals(2515, part2(input))
    }
}
