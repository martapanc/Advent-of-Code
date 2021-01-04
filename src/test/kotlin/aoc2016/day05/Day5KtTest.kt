package aoc2016.day05

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day5KtTest {

    private val testInput = "abc"
    private val input = "wtnhxymk"

    @Test
    fun testFindPassword() {
        assertEquals("18f47a30", findPassword(testInput))
        assertEquals("2414bc77", findPassword(input))
    }
}