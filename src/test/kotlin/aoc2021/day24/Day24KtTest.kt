package aoc2021.day24

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day24KtTest {

    private val testInput0 = readInputToInstructions("src/main/kotlin/aoc2021/day24/assets/input0")
//    private val testInput = readInputToInstructions("src/main/kotlin/aoc2021/day24/assets/input")

    @Test
    fun testPart1() {
//        assertEquals(157, part1(testInput0))
//        assertEquals(8085, part1(testInput))
    }

    @Test
    fun testPart2() {
//        assertEquals(70, part2(testInput0))
//        assertEquals(2515, part2(testInput))
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
    }
}

