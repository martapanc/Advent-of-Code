package aoc2021.day16

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day16KtTest {

  private val testInput = "src/main/kotlin/aoc2021/day16/assets/input"

  @Test
  fun testSolve() {
    assertEquals(879, solve(testInput).first)
    assertEquals(539051801941, solve(testInput).second)
  }
}
