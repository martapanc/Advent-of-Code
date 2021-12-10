package aoc2021.day10

import aoc2020.day05.readInputToList
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day10KtTest {

  val input0 = readInputToList("src/main/kotlin/aoc2021/day10/assets/input0")
  val input = readInputToList("src/main/kotlin/aoc2021/day10/assets/input")

  @Test
  fun findSyntaxErrorChecksum() {
    assertEquals(26397, findSyntaxErrorChecksum(input0))
    assertEquals(413733, findSyntaxErrorChecksum(input))
  }
}
