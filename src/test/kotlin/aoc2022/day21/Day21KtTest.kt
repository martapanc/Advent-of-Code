package aoc2022.day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day21KtTest {

    private val testInput0 = readInputToMonkeyJobs("src/main/kotlin/aoc2022/day21/assets/input0")
    private val testInput = readInputToMonkeyJobs("src/main/kotlin/aoc2022/day21/assets/input")

    @Test
    fun testPart1() {
        assertEquals(152, part1(testInput0))
        assertEquals(63119856257960, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(70, part2(testInput0))
        assertEquals(2515, part2(testInput))
    }
}
