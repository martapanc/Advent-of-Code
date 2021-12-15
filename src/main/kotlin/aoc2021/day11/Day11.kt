package aoc2021.day11

import aoc2020.day20.Coord

fun countFlashingOctopi(octopi: Map<Coord, Int>, steps: Int = 100): Int {
  var flashCount = 0
  var octopiMap = octopi.toMutableMap()
  (1..steps).forEach { _ ->
    val newMap = octopiMap.toMutableMap()

    for (octopus in newMap) {
      newMap[octopus.key] = newMap[octopus.key]!! + 1
    }
    // Probs needs check that octopus flashes only once per round

    while (newMap.values.find { it > 9 } != null) {

      val octopiWithMaxEnergy = newMap.filter { it.value > 9 }
      for (octopus in octopiWithMaxEnergy) {
        val neighbors = getNeighborOctopi(octopi, currentOctopus = octopus.key)
        newMap[octopus.key] = 0
        flashCount++
        for (neighbor: Coord in neighbors) {
          newMap[neighbor] = newMap[neighbor]!! + 1
        }
      }
    }
//
//    for (octopus: Map.Entry<Coord, Int> in octopi) {
//      val neighbors = getNeighborOctopi(octopi, currentOctopus = octopus.key)
//      if (octopus.value == 9) {
//        for (neighbor: Coord in neighbors) {
//          newMap[neighbor] = newMap[neighbor]!! + 1
//        }
//      }

//    }

    octopiMap = newMap
  }

  return flashCount
}

private fun getNeighborOctopi(octopi: Map<Coord, Int>, currentOctopus: Coord): Set<Coord> {
  val neighborOctopi = mutableSetOf<Coord>()
  val (x, y) = Pair(currentOctopus.x, currentOctopus.y)
  val deltas = listOf(
    Coord(x - 1, y - 1), Coord(x, y - 1), Coord(x + 1, y - 1),   // NW N NE
    Coord(x - 1, y), Coord(x + 1, y),                                     // W  .  E
    Coord(x - 1, y + 1), Coord(x, y + 1), Coord(x + 1, y + 1)    // SW S SE
  )

  for (delta: Coord in deltas) {
    val neighbor = octopi[delta]
    if (neighbor != null) neighborOctopi.add(delta)
  }
  return neighborOctopi
}
