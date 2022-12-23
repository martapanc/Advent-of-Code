package aoc2022.day23

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine
import util.readInputToMap

internal class Day23KtTest {

    private val testInput0 = readInputToMap("src/main/kotlin/aoc2022/day23/assets/input0").toMutableMap()
    private val testInput1 = readInputToMap("src/main/kotlin/aoc2022/day23/assets/input1").toMutableMap()
    private val testInput = readInputToMap("src/main/kotlin/aoc2022/day23/assets/input").toMutableMap()

    @Test
    fun testPart1() {
        assertEquals(25, part1(testInput1))
        assertEquals(110, part1(testInput0))
        assertEquals(3925, part1(testInput)) // 3925
    }

    @Test
    fun testPart2() {
        assertEquals(70, part2(testInput0))
        assertEquals(2515, part2(testInput))
    }
}
