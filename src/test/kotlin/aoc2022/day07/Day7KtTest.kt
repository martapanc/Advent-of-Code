package aoc2022.day07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day7KtTest {

    private val testInput0 = readInputLineByLine("src/main/kotlin/aoc2022/day07/assets/input0")
    private val testInput = readInputLineByLine("src/main/kotlin/aoc2022/day07/assets/input")

    private val tree0 = readTreeInputToList("src/main/kotlin/aoc2022/day07/assets/tree0")
    private val tree = readTreeInputToList("src/main/kotlin/aoc2022/day07/assets/tree")

    @Test
    fun testCreateScript() {
        createScript(testInput0, "_test")
        createScript(testInput)
    }

    @Test
    fun testPart1() {
        assertEquals(95437, part1(tree0))
        assertEquals(1989474, part1(tree))
    }

    @Test
    fun testPart2() {
        assertEquals(24933642, part2(tree0))
        assertEquals(1111607, part2(tree))
    }
}
