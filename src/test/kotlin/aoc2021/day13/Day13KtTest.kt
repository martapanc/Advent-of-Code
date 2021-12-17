package aoc2021.day13

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day13KtTest {

  val input0 = readInputToCoordAndFoldList("src/main/kotlin/aoc2021/day13/assets/input0")
  val input = readInputToCoordAndFoldList("src/main/kotlin/aoc2021/day13/assets/input")

  @Test
  fun foldOrigamiAndCompute() {
    assertEquals(17, foldAndCompute(input0.coordinates, foldAlongY = 7).size)
    assertEquals(704, foldAndCompute(input.coordinates, foldAlongX = 655).size)
  }

  @Test
  fun foldOrigamiAndComputePart2() {
    foldOrigami(input0.coordinates, input0.folds)
    foldOrigami(input.coordinates, input.folds)
  }
}
