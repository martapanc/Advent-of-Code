package aoc2021.day21

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day21KtTest {

  private val testInput = Pair(4, 8)
  private val input = Pair(1, 3)

  @Test
  fun testPlayDiracDice() {
    assertEquals(739785, playDiracDice(testInput))
    assertEquals(897798, playDiracDice(input))
  }
}
