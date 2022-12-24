package aoc2022.day24

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import util.readInputToMap

internal class Day24KtTest {

    private val testInput0 = readInputToMap("src/main/kotlin/aoc2022/day24/assets/input0")
    private val testInput1 = readInputToMap("src/main/kotlin/aoc2022/day24/assets/input1")
    private val testInput = readInputToMap("src/main/kotlin/aoc2022/day24/assets/input")

    @Test
    fun testPart1() {
//        assertEquals(0, Day24(testInput0).part1(4))
//        assertEquals(25, Day24(testInput1).part1(19))
        assertEquals(110, Day24(testInput).part1(300))
    }
}

