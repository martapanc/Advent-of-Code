package aoc2021.day25

import util.Coord


fun computeSeaCucumberMovements(inputMap: Map<Coord, Char>): Int {
  var seaCucumbers = inputMap.toMutableMap()
  var moves = 0
  loop@ while (true) {
    val newSeaCucumbers = runStep(seaCucumbers)
    moves++
    if (newSeaCucumbers == seaCucumbers) {
      break@loop
    } else {
      seaCucumbers = newSeaCucumbers
    }
  }

  return moves
}

fun runStep(seaCucumbers: MutableMap<Coord, Char>): MutableMap<Coord, Char> {
  var newSeaCucumbers = mutableMapOf<Coord, Char>()
  val eastFacing = seaCucumbers.filter { it.value == '>' }.keys
  val southFacing = seaCucumbers.filter { it.value == 'v' }.keys
  val (maxX, maxY) = Pair(seaCucumbers.keys.maxOf { it.x }, seaCucumbers.keys.maxOf { it.y })

  eastFacing.forEach { efc ->
    val nextMove = getNextMove(efc, Direction.EAST, seaCucumbers)
    newSeaCucumbers[nextMove] = '>'
  }
  val seaCucumberMidStep = newSeaCucumbers.toMutableMap()
  southFacing.forEach { sf -> seaCucumberMidStep[sf] = 'v' }
  newSeaCucumbers = fillEmptySpaces(seaCucumberMidStep, maxX, maxY)

  var newSeaCucumberMidStep = mutableMapOf<Coord, Char>()

  southFacing.forEach { sfc ->
    val nextMove = getNextMove(sfc, Direction.SOUTH, newSeaCucumbers)
    newSeaCucumberMidStep[nextMove] = 'v'
  }
  newSeaCucumbers.filter { it.value == '>' }.keys.forEach { efc ->
    newSeaCucumberMidStep[efc] = '>'
  }
  newSeaCucumberMidStep = fillEmptySpaces(newSeaCucumberMidStep, maxX, maxY)
  return newSeaCucumberMidStep
}

fun getNextMove(coord: Coord, direction: Direction, seaCucumbers: Map<Coord, Char>): Coord {
  when (direction) {
    Direction.EAST -> {
      var newEastCoord = Coord(coord.x + 1, coord.y)
      if (seaCucumbers[newEastCoord] == null) {
        newEastCoord = Coord(0, coord.y)
      }
      if (seaCucumbers[newEastCoord] == '.') {
        return newEastCoord
      }
    }
    Direction.SOUTH -> {
      var newSouthCoord = Coord(coord.x, coord.y + 1)
      if (seaCucumbers[newSouthCoord] == null) {
        newSouthCoord = Coord(coord.x, 0)
      }
      if (seaCucumbers[newSouthCoord] == '.') {
        return newSouthCoord
      }
    }
  }
  return coord
}

enum class Direction { EAST, SOUTH }

fun fillEmptySpaces(map: MutableMap<Coord, Char>, maxX: Int, maxY: Int): MutableMap<Coord, Char> {
  (0 .. maxY).forEach { y ->
    (0 .. maxX).forEach { x ->
      if (map[Coord(x, y)] == null) map[Coord(x, y)] = '.'
    }
  }
  return map
}

fun printMap(map: Map<Coord, Char>) {
  val (maxX, maxY) = Pair(map.keys.maxOf { it.x }, map.keys.maxOf { it.y })
  (0 .. maxY).forEach { y ->
    (0 .. maxX).forEach { x ->
      print(map[Coord(x, y)])
    }
    println()
  }
  println()
}
