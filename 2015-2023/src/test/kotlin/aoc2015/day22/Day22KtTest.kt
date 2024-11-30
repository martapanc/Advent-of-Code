package aoc2015.day22

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day22KtTest {

    @Test
    fun testPlayWizardSimulator() {
        assertEquals(1824, playWizardSimulator())
        assertEquals(1937, playWizardSimulator(isPart2 = true))
    }
}