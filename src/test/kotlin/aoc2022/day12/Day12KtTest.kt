package aoc2022.day12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine
import util.readInputToMap

internal class Day12KtTest {

    private val testInput0 = readInputToMap("src/main/kotlin/aoc2022/day12/assets/input0")
    private val testInput = readInputToMap("src/main/kotlin/aoc2022/day12/assets/input")

    @Test
    fun testPart1() {
        assertEquals(157, part1(testInput0))
        assertEquals(8085, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(70, part2(testInput0))
        assertEquals(2515, part2(testInput))
    }
}
