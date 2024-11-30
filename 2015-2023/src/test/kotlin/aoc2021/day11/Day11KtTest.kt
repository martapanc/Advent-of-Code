package aoc2021.day11

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day11KtTest {

  val input0 = "src/main/kotlin/aoc2021/day11/assets/input0"
  val input1 = "src/main/kotlin/aoc2021/day11/assets/input1"
  val input = "src/main/kotlin/aoc2021/day11/assets/input"

  @Test
  fun countFlashingOctopi() {
    assertEquals(9, countFlashingOctopi(input1, 1))
    assertEquals(9, countFlashingOctopi(input1, 2))

    assertEquals(0, countFlashingOctopi(input0, 1))
    assertEquals(35, countFlashingOctopi(input0, 2))
    assertEquals(80, countFlashingOctopi(input0, 3))
    assertEquals(96, countFlashingOctopi(input0, 4))
    assertEquals(104, countFlashingOctopi(input0, 5))
    assertEquals(105, countFlashingOctopi(input0, 6))
    assertEquals(112, countFlashingOctopi(input0, 7))
    assertEquals(204, countFlashingOctopi(input0, 10))

    assertEquals(1656, countFlashingOctopi(input0))
    assertEquals(1644, countFlashingOctopi(input))
  }

  @Test
  fun countFlashingOctopiPart2() {
    assertEquals(195, countFlashingOctopi(input0, 200, isPart2 = true))
    assertEquals(229, countFlashingOctopi(input, 250, isPart2 = true))
  }
}
