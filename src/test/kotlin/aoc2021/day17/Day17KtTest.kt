package aoc2021.day17

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day17KtTest {

  private val testInput = "x=20..30, y=-10..-5"
  private val input = "x=94..151, y=-156..-103"

  private val testRange = parseInput(testInput)
  private val range = parseInput(input)

  @Test
  fun testFindTrajectory() {
    assertEquals(-1, findTrajectory(testInput))
  }

  @Test
  fun testFireProbe() {
    assertEquals(3, fireProbe(7, 2, testRange).highestY)
    assertEquals(6, fireProbe(6, 3, testRange).highestY)
    assertEquals(0, fireProbe(9, 0, testRange).highestY)
    assertFalse(fireProbe(17,-4, testRange).hitTarget)
  }
}
