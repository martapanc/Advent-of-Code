package aoc2021.day13

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day13KtTest {

  val input0 = readInputToCoordList("src/main/kotlin/aoc2021/day13/assets/input0")
  val input = readInputToCoordList("src/main/kotlin/aoc2021/day13/assets/input")

  @Test
    fun foldOrigamiAndCompute() {
      assertEquals(17, foldOrigamiAndCompute(input0, foldAlongY = 7))
      assertEquals(704, foldOrigamiAndCompute(input, foldAlongX=655))
    }
}
