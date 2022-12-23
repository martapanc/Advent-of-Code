package aoc2022.day23

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputToMap

internal class Day23KtTestPart2 {

    private val testInput0 = readInputToMap("src/main/kotlin/aoc2022/day23/assets/input0")
    private val testInput = readInputToMap("src/main/kotlin/aoc2022/day23/assets/input")

    @Test
    fun testPart2() {
        assertEquals(903, part2(testInput))
        assertEquals(19, part2(testInput0))
    }
}
