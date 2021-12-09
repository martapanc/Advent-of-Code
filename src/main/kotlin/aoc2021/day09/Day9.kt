package aoc2021.day09

import aoc2020.day20.Coord
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
  inputMap.entries.forEach { heightPoint: Map.Entry<Coord, Int> ->
    val nonNullNeighbors = mutableListOf<Coord>()
    heightPoint.key.neighbors().forEach { n ->
      if (inputMap[n] != null) {
        nonNullNeighbors.add(n)
      }
    }
    if (nonNullNeighbors.all { inputMap[it]!! > heightPoint.value }) {
      riskLevelSum += heightPoint.value + 1
    }
  }
  return riskLevelSum
}
