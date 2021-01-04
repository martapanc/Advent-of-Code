package aoc2018.day24

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day24KtTest {

    private val testInput = readInputToList("src/main/kotlin/aoc2018/day24/testInput")
    private val input = readInputToList("src/main/kotlin/aoc2018/day24/input")

    @Test
    fun testReadInputToList() {
        assertEquals(2, testInput.first.size)
        assertEquals(2, testInput.second.size)
        assertEquals(Group(4485, 2961, listOf("fire", "cold"), listOf("radiation"), 12, "slashing", 4), testInput.second[1])
        assertEquals(10, input.first.size)
        assertEquals(10, input.second.size)
        assertEquals(Group(106, 3258, listOf(), listOf("slashing", "radiation"), 299, "cold", 13), input.first[9])

    }

    @Test
    fun testParseWeaknessesAndImmunities() {
        assertEquals(Pair(listOf(), listOf()), parseWeaknessesAndImmunities(""))
        assertEquals(Pair(listOf("radiation", "bludgeoning"), listOf()), parseWeaknessesAndImmunities("weak to radiation, bludgeoning"))
        assertEquals(Pair(listOf("radiation"), listOf()), parseWeaknessesAndImmunities("weak to radiation"))
        assertEquals(Pair(listOf(), listOf("fire")), parseWeaknessesAndImmunities("immune to fire"))
        assertEquals(Pair(listOf(), listOf("fire", "radiation")), parseWeaknessesAndImmunities("immune to fire, radiation"))
        assertEquals(Pair(listOf("fire", "cold"), listOf("radiation")), parseWeaknessesAndImmunities("immune to radiation; weak to fire, cold"))
        assertEquals(Pair(listOf("cold"), listOf("fire", "radiation", "slashing")), parseWeaknessesAndImmunities("weak to cold; immune to fire, radiation, slashing"))
        assertEquals(Pair(listOf("bludgeoning", "slashing"), listOf("fire")), parseWeaknessesAndImmunities("immune to fire; weak to bludgeoning, slashing"))
    }
}