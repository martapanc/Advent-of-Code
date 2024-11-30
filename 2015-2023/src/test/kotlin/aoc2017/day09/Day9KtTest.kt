package aoc2017.day09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.readInputLineByLine

internal class Day9KtTest {

    private val input = readInputLineByLine("src/main/kotlin/aoc2017/day09/input")[0]

    @Test
    fun testCountGroupsAndComputeScore() {
        assertEquals(1, countGroupsAndComputeScore("{}"))
        assertEquals(5, countGroupsAndComputeScore("{{},{}}"))
        assertEquals(6, countGroupsAndComputeScore("{{{}}}"))
        assertEquals(16, countGroupsAndComputeScore("{{{},{},{{}}}}"))
        assertEquals(1, countGroupsAndComputeScore("{<a>,<a>,<a>,<a>}"))
        assertEquals(9, countGroupsAndComputeScore("{{<ab>},{<ab>},{<ab>},{<ab>}}"))
        assertEquals(9, countGroupsAndComputeScore("{{<!!>},{<!!>},{<!!>},{<!!>}}"))
        assertEquals(3, countGroupsAndComputeScore("{{<a!>},{<a!>},{<a!>},{<ab>}}"))
        assertEquals(21037, countGroupsAndComputeScore(input))
    }

    @Test
    fun testCountItemsInGarbage() {
        assertEquals(0, countItemsInGarbage("<>"))
        assertEquals(17, countItemsInGarbage("<random characters>"))
        assertEquals(3, countItemsInGarbage("<<<<>"))
        assertEquals(2, countItemsInGarbage("<{!>}>"))
        assertEquals(0, countItemsInGarbage("<!!>"))
        assertEquals(0, countItemsInGarbage("<!!!>>"))
        assertEquals(10, countItemsInGarbage("<{o\"i!a,<{i<a>"))
        assertEquals(9495, countItemsInGarbage(input))
    }
}