package aoc2015.day11

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day11KtTest {

    private val testInput1 = "abcdefgh"
    private val testInput2 = "ghijklmn"
    private val input = "hxbxwxba"

    @Test
    fun testFindNextValidPassword() {
        assertEquals("abcdffaa", findNextValidPassword(testInput1))
//        assertEquals("ghjaabcc", findNextValidPassword(testInput2))
        assertEquals("hxbxxyzz", findNextValidPassword(input))
        assertEquals("hxcaabcc", findNextValidPassword("hxbxxyzz"))
    }

    @Test
    fun testIsValidPassword() {
        assertTrue(isValidPassword("abcdffaa"))
        assertTrue(isValidPassword("ghjaabcc"))
        assertFalse(isValidPassword("ghippqrr"))
        assertFalse(isValidPassword("aabcaaf"))
        assertFalse(isValidPassword("abcdeffe"))
        assertFalse(isValidPassword("hijklmmn"))
        assertFalse(isValidPassword("abbceffg"))
        assertFalse(isValidPassword("abbcegjk"))
    }

    @Test
    fun testIncrementCharacter() {
        assertEquals('a', incrementCharacter('z'))
        assertEquals('c', incrementCharacter('b'))
        assertEquals('m', incrementCharacter('l'))
        assertEquals('p', incrementCharacter('n'))
        assertEquals('j', incrementCharacter('h'))
        assertEquals('m', incrementCharacter('k'))
    }
}