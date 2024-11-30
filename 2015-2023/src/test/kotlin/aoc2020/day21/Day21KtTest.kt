package aoc2020.day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day21KtTest {

    private val input0 = readInputToMap("src/main/kotlin/aoc2020/day21/input0")
    private val input = readInputToMap("src/main/kotlin/aoc2020/day21/input")

    @Test
    fun readInputToMap() {
        println(input0)
    }

    @Test
    fun compute() {
        assertEquals(5, countTimesSafeIngredientsAppear(input0))
        assertEquals(1913, countTimesSafeIngredientsAppear(input))
    }

    @Test
    fun listIngredientsByAlphabeticalAllergen() {
        assertEquals("mxmxvkd,sqjhc,fvjkl", listIngredientsByAlphabeticalAllergen(input0))
        assertEquals("gpgrb,tjlz,gtjmd,spbxz,pfdkkzp,xcfpc,txzv,znqbr", listIngredientsByAlphabeticalAllergen(input))
    }
}