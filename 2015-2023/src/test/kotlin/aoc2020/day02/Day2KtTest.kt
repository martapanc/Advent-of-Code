package aoc2020.day02

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day2KtTest {

    private val abcde = "abcde"
    private val aaaaa = "aaaaa"
    private val aabcd = "aabcd"

    private val testPath = "src/main/kotlin/aoc2020/day02/input0"
    private val path = "src/main/kotlin/aoc2020/day02/input"

    @Test
    fun testIsValidPassword() {
        assertTrue(isValidPassword(1, 3, 'a', abcde))
        assertTrue(isValidPassword(1, 1, 'b', abcde))
        assertFalse(isValidPassword(1, 3, 'a', aaaaa))
        assertFalse(isValidPassword(1, 1, 'a', aabcd))
    }

    @Test
    fun testCountValidPasswords() {

        val input0 = readInputFileToList(testPath)
        val input = readInputFileToList(path)
        assertEquals(2, countValidPasswords(input0, ::isValidPassword))
        assertEquals(569, countValidPasswords(input, ::isValidPassword))
    }

    @Test
    fun testIsValidPassword2() {
        assertTrue(isValidPassword2(1, 3, 'a', abcde))
        assertTrue(isValidPassword2(1, 2, 'b', abcde))
        assertFalse(isValidPassword2(1, 3, 'a', aaaaa))
        assertFalse(isValidPassword2(1, 2, 'a', aabcd))
    }

    @Test
    fun testCountValidPasswords2() {
        assertEquals(1, countValidPasswords(readInputFileToList(testPath), ::isValidPassword2))
        assertEquals(346, countValidPasswords(readInputFileToList(path), ::isValidPassword2))
    }

    @Test
    fun testReadInputToList() {
        println(readInputFileToList(testPath))
    }
}