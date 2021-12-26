package aoc2021.day22

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day22KtTest {

  private val input0 = readInputToCoordRanges("src/main/kotlin/aoc2021/day22/assets/input0")
  private val input1 = readInputToCoordRanges("src/main/kotlin/aoc2021/day22/assets/input1")
  private val input = readInputToCoordRanges("src/main/kotlin/aoc2021/day22/assets/input")

  @Test
  fun testInitialiseReactor() {
    assertEquals(39, initialiseReactor(input1))
    assertEquals(590784, initialiseReactor(input0))
    assertEquals(615700, initialiseReactor(input))
  }

  @Test
  fun testInitialiseReactorPart2() {
    assertEquals(39, initialiseReactor(input1, isPart2 = true))
//    assertEquals(2758514936282235, initialiseReactor(input0, isPart2 = true))
//    assertEquals(1236463892941356, initialiseReactor(input, isPart2 = true),)
  }
}
