package aoc2021.day20

import aoc2020.day05.binaryToDecimal
import aoc2020.day20.Coord
import util.readInputLineByLine

fun readInputToImages(inputString: String): ImageEnhancementData {
  var algorithm = ""
  val map = mutableMapOf<Coord, Char>()
  readInputLineByLine(inputString).forEachIndexed { index, line ->
    if (index == 0) {
      algorithm = line
    } else if (line.isNotEmpty()) {
      var x = 0
      line.toCharArray().forEach { char ->
        map[Coord(x, index - 2)] = char
        x++
      }
    }
  }
  return ImageEnhancementData(algorithm, map)
}

fun enhanceAndCountPixels(inputData: ImageEnhancementData, times: Int = 2, realInput: Boolean): Int {
  var result = applyImageEnhancementAlgorithm(inputData.algorithm, inputData.inputImage, 1, realInput)
  (2..times).forEach { step ->
    result = applyImageEnhancementAlgorithm(inputData.algorithm, result, step, realInput)
  }
  return result.values.count { it == '#' }
}

fun applyImageEnhancementAlgorithm(algorithm: String, inputImage: Map<Coord, Char>, step: Int, realInput: Boolean): Map<Coord, Char> {
  val resultImage = mutableMapOf<Coord, Char>()
  val expandedImage = expandInputImage(inputImage, step, realInput)

  expandedImage.entries.forEach { entry ->
    val algorithmIndex = convertToNumber(getSurroundingSquare(entry.key, expandedImage, realInput, step))
    resultImage[entry.key] = algorithm[algorithmIndex]
  }

  return resultImage
}

private fun expandInputImage(inputImage: Map<Coord, Char>, step: Int, realInput: Boolean): Map<Coord, Char> {
  val expandedImage = inputImage.toMutableMap()
  val (minX, maxX) = Pair(inputImage.keys.minOf { it.x }, inputImage.keys.maxOf { it.x })
  val (minY, maxY) = Pair(inputImage.keys.minOf { it.y }, inputImage.keys.maxOf { it.y })
  for (y in minY - 1..maxY + 1)
    for (x in minX - 1..maxX + 1)
      if (expandedImage[Coord(x, y)] == null)
        expandedImage[Coord(x, y)] = if (realInput && step % 2 == 0) '#' else '.'
  return expandedImage
}

fun convertToNumber(inputString: String): Int {
  val inputBinary = inputString.replace("#", "1").replace(".", "0")
  return binaryToDecimal(inputBinary.toInt())
}

fun getSurroundingSquare(cell: Coord, inputImage: Map<Coord, Char>, realInput: Boolean, step: Int = 1): String {
  val expandedImage = expandInputImage(inputImage, step, realInput)
  return getNeighbors(cell).map { expandedImage[it] }.joinToString("")
}

fun getNeighbors(coord: Coord): List<Coord> {
  val neighbors = mutableListOf<Coord>()
  val (x, y) = Pair(coord.x, coord.y)
  neighbors.add(Coord(x - 1, y - 1))
  neighbors.add(Coord(x, y - 1))
  neighbors.add(Coord(x + 1, y - 1))
  neighbors.add(Coord(x - 1, y))
  neighbors.add(Coord(x, y))
  neighbors.add(Coord(x + 1, y))
  neighbors.add(Coord(x - 1, y + 1))
  neighbors.add(Coord(x, y + 1))
  neighbors.add(Coord(x + 1, y + 1))
  return neighbors
}

class ImageEnhancementData(val algorithm: String, val inputImage: Map<Coord, Char>)

