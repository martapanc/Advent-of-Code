package aoc2018.day14

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day14KtTest {

    private val input = 919901

    @Test
    fun testGenerateRecipes() {
        assertEquals("0124515891", generateRecipes(5))
        assertEquals("9251071085", generateRecipes(18))
        assertEquals("5941429882", generateRecipes(2018))
        assertEquals("7861362411", generateRecipes(input))
    }

    @Test
    fun testGenerateRecipesPart2() {
        assertEquals(18, generateRecipesPart2(92510))
        assertEquals(2018, generateRecipesPart2(59414))
        assertEquals(20203532, generateRecipesPart2(input))
    }
}