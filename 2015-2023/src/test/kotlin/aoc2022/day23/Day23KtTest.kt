package aoc2022.day23

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine
import util.readInputToMap

internal class Day23KtTest {

    private val testInput0 = readInputToMap("src/main/kotlin/aoc2022/day23/assets/input0")
    private val testInput1 = readInputToMap("src/main/kotlin/aoc2022/day23/assets/input1")
    private val testInput = readInputToMap("src/main/kotlin/aoc2022/day23/assets/input")

    @Test
    fun testPart1() {
        assertEquals(3925, part1(testInput))
        assertEquals(25, part1(testInput1))
        assertEquals(110, part1(testInput0))
    }
}
