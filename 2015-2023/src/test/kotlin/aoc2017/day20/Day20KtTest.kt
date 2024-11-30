package aoc2017.day20

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day20KtTest {

    private val input = readInputToParticles("src/main/kotlin/aoc2017/day20/input")

    @Test
    fun testFindClosestParticle() {
        assertEquals(157, findClosestParticle(input))
        assertEquals(499, findClosestParticlePart2(input))
    }
}