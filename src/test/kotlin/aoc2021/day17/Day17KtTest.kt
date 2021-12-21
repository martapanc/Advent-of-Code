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
    assertEquals(45, fireProbe(6, 9, testRange).highestY)
    assertFalse(fireProbe(17,-4, testRange).hitTarget)

    assertEquals(45, fireProbe(15, 9, range).highestY)
    assertEquals(55, fireProbe(15, 10, range).highestY)
    assertEquals(120, fireProbe(15, 15, range).highestY)
    assertEquals(325, fireProbe(15, 25, range).highestY)
    assertEquals(1128, fireProbe(15, 47, range).highestY)
    assertEquals(10878, fireProbe(15, 147, range).highestY)
    assertEquals(12090, fireProbe(15, 155, range).highestY)
    assertTrue(fireProbe(15, 155, range).hitTarget)
  }
}
