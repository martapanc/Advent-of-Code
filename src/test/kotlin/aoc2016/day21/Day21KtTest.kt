package aoc2016.day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day21KtTest {

    private val basePath = "src/main/kotlin/aoc2016/day21/"

    @Test
    fun readInputToListAndCompute() {
        assertEquals("decab", readInputToListAndCompute("${basePath}testInput", "abcde"))
        assertEquals("gbhcefad", readInputToListAndCompute("${basePath}input", "abcdefgh"))
        assertEquals("fbgdceah", readInputToListAndCompute("${basePath}input", "gahedfcb"))
    }

    @Test
    fun readInputToListAndComputeReversed() {
        assertEquals("gahedfcb", readInputToListAndComputeReversed("${basePath}input", "fbgdceah"))
//        assertEquals("abcdefgh", readInputToListAndComputeReversed("${basePath}input", "gbhcefad"))
    }

    @Test
    fun testPositionInstruction() {
        assertEquals("ebcda", positionInstruction(4, 0, "abcde", Operation.SWAP))
        assertEquals("abcde", positionInstruction(0, 4, "edcba", Operation.REVERSE))
        assertEquals("bdeac", positionInstruction(1, 4, "bcdea", Operation.MOVE))
        assertEquals("abdec", positionInstruction(3, 0, "bdeac", Operation.MOVE))
    }

    @Test
    fun testSwapLetter() {
        assertEquals("abcdef", swapLetter("abcdfe", 'f', 'e'))
        assertEquals("ba", swapLetter("ab", 'b', 'a'))
    }

    @Test
    fun testRotateLeftRight() {
        assertEquals("eabcd", rotateLeftRight("abcde", Rotation.RIGHT, 1))
        assertEquals("deabc", rotateLeftRight("abcde", Rotation.RIGHT, 2))
        assertEquals("bcdea", rotateLeftRight("abcde", Rotation.LEFT, 1))
        assertEquals("cdeab", rotateLeftRight("abcde", Rotation.LEFT, 2))
    }
}