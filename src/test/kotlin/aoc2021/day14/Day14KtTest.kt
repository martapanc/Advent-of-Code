package aoc2021.day14

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day14KtTest {

  private val input0 = readInputToInsertionRules("src/main/kotlin/aoc2021/day14/assets/input0")
  private val input0_2 = readInputToInsertionRules2("src/main/kotlin/aoc2021/day14/assets/input0")
  private val polymer0 = "NNCB"

  private val input = readInputToInsertionRules("src/main/kotlin/aoc2021/day14/assets/input")
  private val input_2 = readInputToInsertionRules2("src/main/kotlin/aoc2021/day14/assets/input")
  private val polymer = "OFSVVSFOCBNONHKFHNPK"

  @Test
  fun computePolimerisation() {
    assertEquals("NCNBCHB", computePolymerisation(input0, polymer0, 1))
    assertEquals("NBCCNBBBCBHCB", computePolymerisation(input0, polymer0, 2))
    assertEquals("NBBBCNCCNBBNBNBBCHBHHBCHB", computePolymerisation(input0, polymer0, 3))
    assertEquals("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB", computePolymerisation(input0, polymer0, 4))
  }

  @Test
  fun computeChecksum() {
    assertEquals(1588, computeChecksum(input0, polymer0))
    assertEquals(3284, computeChecksum(input, polymer))
  }

  @Test
  fun testComputeChecksumPart2() {
    assertEquals(18, computeChecksumPart2(input0_2, polymer0, 4) + 1)
    assertEquals(1588, computeChecksumPart2(input0_2, polymer0, 10) + 1)
    assertEquals(2188189693529, computeChecksumPart2(input0_2, polymer0, 40) + 1)
    assertEquals(4302675529690, computeChecksumPart2(input_2, polymer, 40))
  }
}
