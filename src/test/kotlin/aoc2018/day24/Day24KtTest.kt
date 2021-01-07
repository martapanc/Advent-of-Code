package aoc2018.day24

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day24KtTest {

    private val testPath = "src/main/kotlin/aoc2018/day24/testInput"
    private val path = "src/main/kotlin/aoc2018/day24/input"

    @Test
    fun testReadInputToList() {
        val testInput = readInputToList(testPath)
        assertEquals(2, testInput.vaccineGroups.size)
        assertEquals(2, testInput.coronaGroups.size)
        assertEquals(
            Group(4485, 2961, listOf("fire", "cold"), listOf("radiation"), 12, "slashing", 4),
            testInput.coronaGroups[1]
        )
        val input = readInputToList(path)
        assertEquals(10, input.vaccineGroups.size)
        assertEquals(10, input.coronaGroups.size)
        assertEquals(
            Group(106, 3258, listOf(), listOf("slashing", "radiation"), 299, "cold", 13),
            input.vaccineGroups[9]
        )
    }

    @Test
    fun testParseWeaknessesAndImmunities() {
        val empty = listOf<String>()
        assertEquals(Pair(empty, empty), parseWeaknessesAndImmunities(""))
        assertEquals(
            Pair(listOf("radiation", "bludgeoning"), empty),
            parseWeaknessesAndImmunities("weak to radiation, bludgeoning")
        )
        assertEquals(Pair(listOf("radiation"), empty), parseWeaknessesAndImmunities("weak to radiation"))
        assertEquals(Pair(empty, listOf("fire")), parseWeaknessesAndImmunities("immune to fire"))
        assertEquals(
            Pair(empty, listOf("fire", "radiation")),
            parseWeaknessesAndImmunities("immune to fire, radiation")
        )
        assertEquals(
            Pair(listOf("fire", "cold"), listOf("radiation")),
            parseWeaknessesAndImmunities("immune to radiation; weak to fire, cold")
        )
        assertEquals(
            Pair(listOf("cold"), listOf("fire", "radiation", "slashing")),
            parseWeaknessesAndImmunities("weak to cold; immune to fire, radiation, slashing")
        )
        assertEquals(
            Pair(listOf("bludgeoning", "slashing"), listOf("fire")),
            parseWeaknessesAndImmunities("immune to fire; weak to bludgeoning, slashing")
        )
    }

    @Test
    fun testPlayRound() {
        val playRound = playRound(readInputToList(testPath))
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
        assertEquals(5216, playGameOfCorona(testPath))
        assertEquals(24318, playGameOfCorona(path))
    }

    @Test
    fun testPlayGameOfCoronaWithBoosts() {
        assertEquals(5216, playGameOfCorona(testPath, 0).first)
        assertEquals(51, playGameOfCorona(testPath, 1570).first)
        assertEquals(24318, playGameOfCorona(path, 0).first)

        assertEquals(51, playGameOfCoronaWithBoosts(testPath))
        assertEquals(1083, playGameOfCoronaWithBoosts(path))
        assertEquals(1083, playGameOfCorona(path, 79).first)
    }
}