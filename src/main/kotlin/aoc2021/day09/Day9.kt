package aoc2021.day09

import aoc2020.day20.Coord
import util.readInputLineByLine

fun readInputToMap(path: String): Map<Coord, Int> {
  val map = mutableMapOf<Coord, Int>()
  var x = 0
  for ((y, line) in readInputLineByLine(path).withIndex()) {
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
  inputMap.entries.forEach { heightPoint : Map.Entry<Coord, Int> ->
    val neighbors = heightPoint.key.neighbors()
    val heightValue = heightPoint.value

    val nonNullNeighbors = mutableListOf<Coord>()
    neighbors.forEach { n ->
      if (inputMap[n] != null) {
        nonNullNeighbors.add(n)
      }
    }
    if (nonNullNeighbors.all { inputMap[it]!! > heightValue }) {
      riskLevelSum += heightValue + 1
    }
  }
  return riskLevelSum
}
