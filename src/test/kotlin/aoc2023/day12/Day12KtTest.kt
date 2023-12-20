package aoc2023.day12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day12KtTest {

    private val testInputLines = readInputLineByLine("src/main/kotlin/aoc2023/day12/assets/input0")
    private val testInput = parse(testInputLines)

    private val inputLines = readInputLineByLine("src/main/kotlin/aoc2023/day12/assets/input")
    private val input = parse(inputLines)

    @Test
    fun testPart1() {
        assertEquals(21, part1(testInput))
        assertEquals(7169, part1(input))
    }

    @Test
    fun testPart2() {
        assertEquals(525152, part2(testInput))
        assertEquals(1738259948652, part2(input))
    }

    @Test
    fun testSolveRecord() {
        assertEquals(1, solveRecord("???.###", intArrayOf(1, 1, 3)))
        assertEquals(4, solveRecord(".??..??...?##.", intArrayOf(1, 1, 3)))
        assertEquals(1, solveRecord("?#?#?#?#?#?#?#?", intArrayOf(1, 3, 1, 6)))
        assertEquals(1, solveRecord("????.#...#...", intArrayOf(4, 1, 1)))
        assertEquals(4, solveRecord("????.######..#####.", intArrayOf(1, 6, 5)))
        assertEquals(10, solveRecord("?###????????", intArrayOf(3, 2, 1)))
        assertEquals(1, solveRecord("?##?##??#?", intArrayOf(7, 1)))
    }
}
