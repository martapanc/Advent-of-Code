package aoc2021.day19

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day19KtTest {

  private val input0 = readScannerObservations("src/main/kotlin/aoc2021/day19/assets/input0")
  private val input1 = readScannerObservations("src/main/kotlin/aoc2021/day19/assets/input1")
  private val input = readScannerObservations("src/main/kotlin/aoc2021/day19/assets/input")

  @Test
  fun testReadScannerObservations() {
    assertEquals(5, input0.size)
    assertEquals(2, input1.size)
    assertEquals(35, input.size)
  }

  @Test
  fun testFindBeacons() {
//    assertEquals(3, findBeacons(input1))
    assertEquals(12, findBeacons3(input0))
    assertEquals(16, findBeacons(input))
  }
}
