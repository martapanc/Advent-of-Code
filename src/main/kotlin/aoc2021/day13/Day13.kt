package aoc2021.day13

import aoc2020.day20.Coord
import util.readInputLineByLine
import kotlin.math.abs

fun readInputToCoordList(input: String): List<Coord> {
  val coordList = mutableListOf<Coord>()
  for (line in readInputLineByLine(input)) {
    if (line.isEmpty()) {
      break
    }
    val split = line.split(",").map { it.toInt() }
    coordList.add(Coord(split[0], split[1]))
  }

  return coordList
}

fun foldOrigamiAndCompute(coordList: List<Coord>, foldAlongX: Int? = null, foldAlongY: Int? = null): Int {
  val foldedList = mutableSetOf<Coord>()

  if (foldAlongY != null) {
    for (coord in coordList) {
      foldedList.add(Coord(coord.x, abs(foldAlongY - coord.y)))
    }
  }

  if (foldAlongX != null) {
    for (coord in coordList) {
      foldedList.add(Coord(abs(foldAlongX - coord.x), coord.y))
    }
  }

  return foldedList.size
}
