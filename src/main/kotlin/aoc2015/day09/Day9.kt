package aoc2015.day09

import util.readInputLineByLine
import java.util.*

fun readInputToListDay9(path: String): Map<Trip, Int> {
    val trips = mutableMapOf<Trip, Int>()
    readInputLineByLine(path)
        .map { it.split(" to ", " = ") }
        .forEach { trips[Trip(it[0].trim(), it[1].trim())] = it[2].trim().toInt() }
    return trips
}

fun findShortestTripPermutation(map: Map<Trip, Int>, longest: Boolean = false): Int? {
    val placeList = mutableSetOf<String>()
    for (place in map.keys) {
        placeList.add(place.origin)
        placeList.add(place.destination)
    }
    permute(placeList.toList(), 0, map)

    val distances = readInputLineByLine("src/main/kotlin/aoc2015/day09/tripDistances")[0].split(" ").map { it.toInt() }
    return if (longest) distances.maxOrNull() else distances.minOrNull()
}

private fun permute(arr: List<String>, k: Int, map: Map<Trip, Int>) {
    for (i in k until arr.size) {
        Collections.swap(arr, i, k)
        permute(arr, k + 1, map)
        Collections.swap(arr, k, i)
    }
    if (k == arr.size - 1) {
        print(" " + findTotalDistance(arr, map))
    }
}

fun findTotalDistance(array: List<String>, map: Map<Trip, Int>): Int {
    var length = 0
    for (i in 0 until array.size - 1) {
        val trip = Trip(array[i], array[i + 1])
        val inverse = Trip(array[i + 1], array[i])
        length += if (map.containsKey(trip)) {
            map[trip]!!
        } else {
            map[inverse]!!
        }
    }
    return length
}

data class Trip(val origin: String, val destination: String)