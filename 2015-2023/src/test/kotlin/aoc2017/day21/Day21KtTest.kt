package aoc2017.day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day21KtTest {

    private val input = parseInput("src/main/kotlin/aoc2017/day21/input")
    private val testInput = parseInput("src/main/kotlin/aoc2017/day21/testInput")

    @Test
    fun testFindAlternativePatterns() {
        val allPatterns = setOf(
            ".#./..#/###", ".#./#../###", "#../#.#/##.", "..#/#.#/.##",
            "###/#../.#.", "###/..#/.#.", ".##/#.#/..#", "##./#.#/#.."
        )
        assertEquals(allPatterns, findAlternativePatterns(".#./..#/###") )
    }

    @Test
    fun testRotatePatternRight() {
        assertEquals("#../#.#/##.", rotatePatternRight(".#./..#/###"))
        assertEquals("###/#../.#.", rotatePatternRight("#../#.#/##."))
        assertEquals(".##/#.#/..#", rotatePatternRight("###/#../.#."))
        assertEquals(".#./..#/###", rotatePatternRight(".##/#.#/..#"))
    }

    @Test
    fun testFlipPattern() {
        assertEquals(".#./#../###", flipPattern(".#./..#/###"))
        assertEquals("..#/#.#/##.", flipPattern("#../#.#/.##"))
        assertEquals("###/..#/.#.", flipPattern("###/#../.#."))
        assertEquals("##./#.#/#..", flipPattern(".##/#.#/..#"))
    }

    @Test
    fun testEnhanceString() {
        assertEquals(12, enhanceString(testInput, 2))
        assertEquals(136, enhanceString(input, 5))
        assertEquals(1911767, enhanceString(input, 18))
    }
}