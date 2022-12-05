package aoc2022.day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day5KtTest {

    private val testInput0 = readInputToMovementList("src/main/kotlin/aoc2022/day05/assets/input0")
    private val testInput = readInputToMovementList("src/main/kotlin/aoc2022/day05/assets/input")

    private val testInitialStacks: List<ArrayDeque<Char>> = listOf(
        ArrayDeque(listOf('Z', 'N')),
        ArrayDeque(listOf('M', 'C', 'D')),
        ArrayDeque(listOf('P')),
    )

    private val initialStacks: List<ArrayDeque<Char>> = listOf(
        ArrayDeque(listOf('Z', 'J', 'N', 'W', 'P', 'S')),
        ArrayDeque(listOf('G', 'S', 'T')),
        ArrayDeque(listOf('V', 'Q', 'R', 'L', 'H')),
        ArrayDeque(listOf('V', 'S', 'T', 'D')),
        ArrayDeque(listOf('Q', 'Z', 'T', 'D', 'B', 'M', 'J')),
        ArrayDeque(listOf('M', 'W', 'T', 'J', 'D', 'C', 'Z', 'L')),
        ArrayDeque(listOf('L', 'P', 'M', 'W', 'G', 'T', 'J')),
        ArrayDeque(listOf('N', 'G', 'M', 'T', 'B', 'F', 'Q', 'H')),
        ArrayDeque(listOf('R', 'D', 'G', 'C', 'P', 'B', 'Q', 'W')),
    )

    @Test
    fun testPart1() {
        assertEquals("CMZ", part1(testInitialStacks, testInput0))
        assertEquals("MQTPGLLDN", part1(initialStacks, testInput))
    }

    @Test
    fun testPart2() {
        assertEquals("MCD", part2(testInitialStacks, testInput0))
    }
}
