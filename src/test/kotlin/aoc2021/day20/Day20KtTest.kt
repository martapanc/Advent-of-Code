package aoc2021.day20

import util.Coord
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Day20KtTest {

  private val input0 = readInputToImages("src/main/kotlin/aoc2021/day20/assets/input0")
  private val input = readInputToImages("src/main/kotlin/aoc2021/day20/assets/input")

  @Test
  fun testReadInputToImages() {
    assertEquals(25, input0.inputImage.keys.size)
    assertEquals("..#.#..###", input0.algorithm.subSequence(0, 10))
    assertEquals(10000, input.inputImage.keys.size)
    assertEquals("####....##", input.algorithm.subSequence(0, 10))
  }

  @Test
  fun testGetSurroundingSquare() {
    assertEquals("....#..#.", getSurroundingSquare(Coord(0, 0), input0.inputImage, false))
    assertEquals("...#...#.", getSurroundingSquare(Coord(2, 2), input0.inputImage, false))
  }

  @Test
  fun testConvertToNumber() {
    assertEquals(34, convertToNumber("...#...#."))
  }

  @Test
  fun testEnhanceAndCountPixels() {
    assertEquals(24, enhanceAndCountPixels(input0, 1, false))
    assertEquals(35, enhanceAndCountPixels(input0, 2, false))
    assertEquals(5597, enhanceAndCountPixels(input, 2, true))

//    assertEquals(3351, enhanceAndCountPixels(input0, 50, false))
//    assertEquals(18723, enhanceAndCountPixels(input, 50, false))
  }

}
