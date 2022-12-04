package aoc2021.day16

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day16KtTest {

  private val testInput0 = "D2FE28"
  private val testInput1 = "38006F45291200"
  private val testInput2 = "EE00D40C823060"
  private val testInput3 = "8A004A801A8002F478"

  private val testInput = "src/main/kotlin/aoc2021/day16/assets/input"

  @Test
  fun testSolve() {
    assertEquals(879, solve(testInput).first)
    assertEquals(539051801941, solve(testInput).second)
  }
}
