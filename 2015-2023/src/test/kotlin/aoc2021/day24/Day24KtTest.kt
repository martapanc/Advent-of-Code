package aoc2021.day24

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day24KtTest {

    private val testInput0 = readInputToInstructions("src/main/kotlin/aoc2021/day24/assets/input0")
    private val testInput1 = readInputToInstructions("src/main/kotlin/aoc2021/day24/assets/input1")
    private val testInput = readInputToInstructions("src/main/kotlin/aoc2021/day24/assets/input")

    @Test
    fun testPart1() {
        assertEquals(8, part1(testInput0))

        assertEquals("99196997985942", solve(readInputLineByLine("src/main/kotlin/aoc2021/day24/assets/input")).first)
        assertEquals("84191521311611", solve(readInputLineByLine("src/main/kotlin/aoc2021/day24/assets/input")).second)
    }

    @Test
    fun testVerifyModelNumber() {
        assertFalse(verifyModelNumber(testInput0, "1"))
        assertTrue(verifyModelNumber(testInput0, "2"))
        assertFalse(verifyModelNumber(testInput0, "3"))
        assertTrue(verifyModelNumber(testInput0, "4"))
        assertFalse(verifyModelNumber(testInput0, "5"))
        assertTrue(verifyModelNumber(testInput0, "6"))
        assertFalse(verifyModelNumber(testInput0, "7"))
        assertTrue(verifyModelNumber(testInput0, "8"))
        assertFalse(verifyModelNumber(testInput0, "9"))

        assertTrue(verifyModelNumber(testInput1, "11"))
        assertFalse(verifyModelNumber(testInput1, "13"))
        assertFalse(verifyModelNumber(testInput1, "20"))

        assertFalse(verifyModelNumber(testInput, "99987991848459"))
        assertFalse(verifyModelNumber(testInput, "99987991848458"))
        assertFalse(verifyModelNumber(testInput, "99987991848457"))
        assertFalse(verifyModelNumber(testInput, "99987991848456"))
        assertFalse(verifyModelNumber(testInput, "99987991848455"))
        assertFalse(verifyModelNumber(testInput, "99987991848454"))
        assertFalse(verifyModelNumber(testInput, "99987991848453"))
        assertFalse(verifyModelNumber(testInput, "99987991848452"))
        assertFalse(verifyModelNumber(testInput, "99986912383145"))
        assertFalse(verifyModelNumber(testInput, "99986912383144"))
        assertFalse(verifyModelNumber(testInput, "99986912381111"))
        assertFalse(verifyModelNumber(testInput, "99986912381110"))
        assertFalse(verifyModelNumber(testInput, "99196997985942"))
    }
}

