package aoc2020.day2

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day2KtTest {

    private val ABCDE = "abcde"
    private val AAAAA = "aaaaa"
    private val AABCD = "aabcd"

    @Test
    fun testIsValidPassword() {
        assertTrue(isValidPassword(1, 3, 'a', ABCDE))
        assertTrue(isValidPassword(1, 1, 'b', ABCDE))
        assertFalse(isValidPassword(1, 3, 'a', AAAAA))
        assertFalse(isValidPassword(1, 1, 'a', AABCD))
    }

    @Test
    fun testCountValidPasswords() {
        val input0 = readInputFileToList("src/main/kotlin/aoc2020/day2/input0")
        val input = readInputFileToList("src/main/kotlin/aoc2020/day2/input")
        assertEquals(2, countValidPasswords(input0, ::isValidPassword))
        assertEquals(569, countValidPasswords(input, ::isValidPassword))
    }

    @Test
    fun testIsValidPassword2() {
        assertTrue(isValidPassword2(1, 3, 'a', ABCDE))
        assertTrue(isValidPassword2(1, 2, 'b', ABCDE))
        assertFalse(isValidPassword2(1, 3, 'a', AAAAA))
        assertFalse(isValidPassword2(1, 2, 'a', AABCD))
    }

    @Test
    fun testCountValidPasswords2() {
        assertEquals(1, countValidPasswords(readInputFileToList("src/main/kotlin/aoc2020/day2/input0"), ::isValidPassword2))
        assertEquals(346, countValidPasswords(readInputFileToList("src/main/kotlin/aoc2020/day2/input"), ::isValidPassword2))
    }

    @Test
    fun testReadInputToList() {
        println(readInputFileToList("src/main/kotlin/aoc2020/day2/input0"))
    }
}