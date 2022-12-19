package aoc2022.day19

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day19KtTest {

    private val testInput0 = readInputToBlueprint("src/main/kotlin/aoc2022/day19/assets/input0")
    private val testInput = readInputToBlueprint("src/main/kotlin/aoc2022/day19/assets/input")

    @Test
    fun testPart1() {
        assertEquals(33, part1(testInput0))
        assertEquals(1365, part1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(3472, part2(testInput0))
        assertEquals(4864, part2(testInput))
    }
}
