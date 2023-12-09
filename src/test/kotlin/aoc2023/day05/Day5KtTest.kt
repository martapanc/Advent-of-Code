package aoc2023.day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day5KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2023/day05/assets/input0")
    private val testMapping = parse(testInput0)
    private val input = readInputLineByLine("src/main/kotlin/aoc2023/day05/assets/input")
    private val mapping = parse(input)

    @Test
    fun testPart1() {
        assertEquals(35, part1(testMapping))
        assertEquals(8085, part1(mapping))
    }

    @Test
    fun testPart2() {
//        assertEquals(70, part2(testMapping))
//        assertEquals(2515, part2(mapping))
    }
}
