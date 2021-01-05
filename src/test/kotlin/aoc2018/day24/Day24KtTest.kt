package aoc2018.day24

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day24KtTest {

    private val testInput = readInputToList("src/main/kotlin/aoc2018/day24/testInput")
    private val input = readInputToList("src/main/kotlin/aoc2018/day24/input")

    @Test
    fun testReadInputToList() {
        assertEquals(2, testInput.vaccineGroups.size)
        assertEquals(2, testInput.coronaGroups.size)
        assertEquals(Group(4485, 2961, listOf("fire", "cold"), listOf("radiation"), 12, "slashing", 4), testInput.coronaGroups[1])
        assertEquals(10, input.vaccineGroups.size)
        assertEquals(10, input.coronaGroups.size)
        assertEquals(Group(106, 3258, listOf(), listOf("slashing", "radiation"), 299, "cold", 13), input.vaccineGroups[9])
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

    @Test
    fun testPlayRound() {
        val playRound = playRound(testInput)
        assertEquals(1, playRound.vaccineGroups.size)
        assertEquals(905, playRound.vaccineGroups[0].size)
        assertEquals(2, playRound.coronaGroups.size)
        assertEquals(797, playRound.coronaGroups[0].size)
        assertEquals(4434, playRound.coronaGroups[1].size)

        val play2Rounds = playRound(playRound)
        assertEquals(761, play2Rounds.vaccineGroups[0].size)
        assertEquals(793, play2Rounds.coronaGroups[0].size)
        assertEquals(4434, play2Rounds.coronaGroups[1].size)

        val play3Rounds = playRound(play2Rounds)
        assertEquals(618, play3Rounds.vaccineGroups[0].size)
        assertEquals(789, play3Rounds.coronaGroups[0].size)
        assertEquals(4434, play3Rounds.coronaGroups[1].size)

        val play7Rounds = playRound(playRound(playRound(playRound(play3Rounds))))
        assertEquals(49, play7Rounds.vaccineGroups[0].size)
        assertEquals(782, play7Rounds.coronaGroups[0].size)
        assertEquals(4434, play7Rounds.coronaGroups[1].size)

        val play8Rounds = playRound(play7Rounds)
        assertEquals(0, play8Rounds.vaccineGroups.size)
        assertEquals(782, play8Rounds.coronaGroups[0].size)
        assertEquals(4434, play8Rounds.coronaGroups[1].size)
    }

    @Test
    fun testPlayGameOfCorona() {
        assertEquals(5216, playGameOfCorona(testInput))
        assertEquals(24318, playGameOfCorona(input))
    }
}