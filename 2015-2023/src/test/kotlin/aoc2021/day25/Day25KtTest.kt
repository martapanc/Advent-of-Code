package aoc2021.day25

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import util.readInputToMap

internal class Day25KtTest {

  private val input0 = readInputToMap("src/main/kotlin/aoc2021/day25/assets/input0")
  private val input1 = readInputToMap("src/main/kotlin/aoc2021/day25/assets/input1")
  private val input2 = readInputToMap("src/main/kotlin/aoc2021/day25/assets/input2")
  private val input = readInputToMap("src/main/kotlin/aoc2021/day25/assets/input")

  @Test
  fun testComputeSeaCucumberMovements() {
    assertEquals(58, computeSeaCucumberMovements(input1))
    assertEquals(458, computeSeaCucumberMovements(input))
  }

  @Test
  fun testRunStep() {
    runStep(input2.toMutableMap())
    runStep(input0.toMutableMap())
    runStep(runStep(input0.toMutableMap()))
    runStep(runStep(runStep(input0.toMutableMap())))
  }
}
