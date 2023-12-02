package aoc2023.day01

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine


internal class Day1KtTest {

    private val testInputList = readInputLineByLine("src/main/kotlin/aoc2023/day01/assets/input0")
    private val inputList = readInputLineByLine("src/main/kotlin/aoc2023/day01/assets/input")

    @Test
    fun testPart1() {
        assertEquals(142, part1(testInputList))
        assertEquals(55712, part1(inputList))
    }

    @Test
    fun testPart2() {
//        assertEquals(45000, part2(testInputList))
//        assertEquals(204639, part2(inputList))
    }
}
