package aoc2021.day09

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day9KtTest {

  val input0 = readInputToMap("src/main/kotlin/aoc2021/day09/assets/input0")
  val input = readInputToMap("src/main/kotlin/aoc2021/day09/assets/input")

  @Test
  fun findSumOfRiskLevels() {
    assertEquals(15, findSumOfRiskLevels(input0))
    assertEquals(577, findSumOfRiskLevels(input))
  }

  @Test
  fun findProductOfLargestBasins() {
    assertEquals(1134, findProductOfLargestBasins(input0))
    assertEquals(1069200, findProductOfLargestBasins(input))
  }
}
