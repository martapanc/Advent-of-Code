package aoc2021.day11

import aoc2021.day09.readInputToMap
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day11KtTest {

  val input0 = readInputToMap("src/main/kotlin/aoc2021/day11/assets/input0")
  val input1 = readInputToMap("src/main/kotlin/aoc2021/day11/assets/input1")
  val input = readInputToMap("src/main/kotlin/aoc2021/day11/assets/input")

  @Test
  fun countFlashingOctopi() {
//    assertEquals(9, countFlashingOctopi(input1, 1))
//
//    assertEquals(0, countFlashingOctopi(input0, 1))
//    assertEquals(35, countFlashingOctopi(input0, 2))
    assertEquals(80, countFlashingOctopi(input0, 3))
    assertEquals(96, countFlashingOctopi(input0, 4))
    assertEquals(107, countFlashingOctopi(input0, 5))
    assertEquals(108, countFlashingOctopi(input0, 6))
    assertEquals(204, countFlashingOctopi(input0, 10))
//    assertEquals(1656, countFlashingOctopi(input0))
  }
}
