package aoc2021.day13

import aoc2020.day20.Coord
import util.readInputLineByLine
import kotlin.math.abs

fun readInputToCoordAndFoldList(input: String): Instructions {
  val coordList = mutableListOf<Coord>()
  val foldList = mutableListOf<Fold>()
  for (line in readInputLineByLine(input)) {
    if (line.isEmpty()) {
      continue
    } else {
      if (line.startsWith("fold")) {
        val split = line.replace("fold along ", "").split("=")
        foldList.add(Fold(Direction.valueOf(split[0].toUpperCase()), split[1].toInt()))
      } else {
        val split = line.split(",").map { it.toInt() }
        coordList.add(Coord(split[0], split[1]))
      }
    }
  }
  return Instructions(coordList, foldList)
}

fun foldAndCompute(coordList: List<Coord>, foldAlongX: Int? = null, foldAlongY: Int? = null): Set<Coord> {
  val foldedList = mutableSetOf<Coord>()

  if (foldAlongY != null) {
    for (coord in coordList) {
      if (foldAlongY > coord.y) {
        foldedList.add(coord)
      } else {
        foldedList.add(Coord(coord.x, coord.y - 2 * abs(foldAlongY - coord.y)))
      }
    }
  }

  if (foldAlongX != null) {
    for (coord in coordList) {
      if (foldAlongX > coord.x) {
        foldedList.add(coord)
      } else {
        foldedList.add(Coord(coord.x - 2 * abs(foldAlongX - coord.x), coord.y))
      }
    }
  }

  return foldedList
}

fun foldOrigami(coordList: List<Coord>, foldList: List<Fold>) {
  var mutableCoordList = coordList.toMutableList()
  for (fold in foldList) {
    val foldedCoords =
      if (fold.direction == Direction.X) {
        foldAndCompute(mutableCoordList, foldAlongX = fold.foldValue)
      } else {
        foldAndCompute(mutableCoordList, foldAlongY = fold.foldValue)
      }

    mutableCoordList = foldedCoords.toMutableList()
  }
  printCoords(mutableCoordList)
}

class Fold(val direction: Direction, val foldValue: Int)

enum class Direction { X, Y }

class Instructions(val coordinates: List<Coord>, val folds: List<Fold>)

fun printCoords(coords: List<Coord>) {
  for (y in 0..coords.maxOf { it.y }) {
    for (x in 0..coords.maxOf { it.x })
      if (coords.contains(Coord(x, y))) print("#") else print(" ")
    println()
  }
  println()
}
