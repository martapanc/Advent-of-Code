package aoc2015.day21

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day21KtTest {

    private val input = Character(109, 8, 2)

    @Test
    fun testPlayRpgSimulator() {
        assertEquals(111, playRpgSimulator(input))
        assertEquals(188, playRpgSimulatorPart2(input))
    }
}