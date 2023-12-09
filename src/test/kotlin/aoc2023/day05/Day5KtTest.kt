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
        assertEquals(196167384, part1(mapping))
    }

    @Test
    fun testPart2() {
//        assertEquals(46, part2(testMapping))
        assertEquals(2515, part2(mapping))
    }

    @Test
    fun testFindMapping() {
        assertEquals(50, findMapping(98, listOf(Range(50, 98, 2))))
        assertEquals(51, findMapping(99, listOf(Range(50, 98, 2))))
        assertEquals(10, findMapping(10, listOf(Range(50, 98, 2))))
        assertEquals(52, findMapping(50, listOf(Range(50, 98, 2), Range(52, 50, 48))))
        assertEquals(99, findMapping(97, listOf(Range(50, 98, 2), Range(52, 50, 48))))
    }
}
