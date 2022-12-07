package aoc2022.day07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day7KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2022/day07/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2022/day07/assets/input")

    @Test
    fun testCreateScript() {
        createScript(testInput0, "_test")
        createScript(testInput)
    }

    @Test
    fun testPart1() {
//        assertEquals(0, part1(testInput0))
//        assertEquals(0, createScript(testInput))
    }

    @Test
    fun testPart2() {
//        assertEquals(70, part2(testInput0))
//        assertEquals(2515, part2(testInput))
    }
}
