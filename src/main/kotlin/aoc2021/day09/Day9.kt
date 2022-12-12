package aoc2021.day09

import util.Coord
import util.readInputLineByLine

fun readInputToMap(path: String): Map<Coord, Int> {
  val map = mutableMapOf<Coord, Int>()
  var x = 0
  readInputLineByLine(path).withIndex().forEach { (y, line) ->
    for (char in line) {
      map[Coord(x, y)] = Character.getNumericValue(char)
      x++
    }
    x = 0
  }
  return map
}

fun findSumOfRiskLevels(inputMap: Map<Coord, Int>): Int {
  var riskLevelSum = 0
  getLowestPoints(inputMap).forEach {
    riskLevelSum += inputMap[it]!! + 1
  }
  return riskLevelSum
}

fun getLowestPoints(inputMap: Map<Coord, Int>): List<Coord> {
  val lowestPoints = mutableListOf<Coord>()
  inputMap.entries.forEach { heightPoint: Map.Entry<Coord, Int> ->
    val nonNullNeighbors = mutableListOf<Coord>()
    heightPoint.key.neighbors().forEach { n ->
      if (inputMap[n] != null) {
        nonNullNeighbors.add(n)
      }
    }
    if (nonNullNeighbors.all { inputMap[it]!! > heightPoint.value }) {
      lowestPoints.add(heightPoint.key)
    }
  }
  return lowestPoints
}

fun findProductOfLargestBasins(inputMap: Map<Coord, Int>): Int {
  val basinSizeList = mutableListOf<Int>()
  val visitedPoints = mutableSetOf<Coord>()

  getLowestPoints(inputMap).forEach { lowestPoint ->
    var basinSize = 1
    var edge = mutableSetOf<Coord>()
    edge.add(lowestPoint)

    while (edge.isNotEmpty()) {
      val newEdge = mutableSetOf<Coord>()
      edge.forEach { point ->
          getNonNullNeighbors(point, inputMap).forEach {
          if (inputMap[it]!! != 9 && !visitedPoints.contains(it) && inputMap[it]!! > inputMap[point]!!) {
            newEdge.add(it)
            visitedPoints.add(it)
          }
        }
      }
      basinSize += newEdge.size
      edge = newEdge
    }
    basinSizeList.add(basinSize)
  }
  basinSizeList.sortDescending()
  return basinSizeList[0] * basinSizeList[1] * basinSizeList[2]
}

private fun getNonNullNeighbors(lowestPoint: Coord, inputMap: Map<Coord, Int>): List<Coord> {
  val nonNullNeighbors = mutableListOf<Coord>()
  lowestPoint.neighbors().forEach { n -> if (inputMap[n] != null) nonNullNeighbors.add(n) }
  return nonNullNeighbors
}
