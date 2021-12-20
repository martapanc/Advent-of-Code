package aoc2021.day14

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day14KtTest {

  val input0 = readInputToInsertionRules("src/main/kotlin/aoc2021/day14/assets/input0")
  val polymer0 = "NNCB"

  val input = readInputToInsertionRules("src/main/kotlin/aoc2021/day14/assets/input")
  val polymer = "OFSVVSFOCBNONHKFHNPK"

  @Test
  fun computePolimerisation() {
    assertEquals("NCNBCHB", computePolimerisation(input0, polymer0, 1))
    assertEquals("NBCCNBBBCBHCB", computePolimerisation(input0, polymer0, 2))
    assertEquals("NBBBCNCCNBBNBNBBCHBHHBCHB", computePolimerisation(input0, polymer0, 3))
    assertEquals("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB", computePolimerisation(input0, polymer0, 4))
  }

  @Test
  fun computeChecksum() {
    assertEquals(1588, computeChecksum(input0, polymer0, 10))
    assertEquals(3284, computeChecksum(input, polymer, 10))
  }
}
