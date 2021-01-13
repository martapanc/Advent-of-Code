package aoc2018.day03

import java.util.*


fun computeOccupiedInches(id: Int, xBorder: Int, yBorder: Int, length: Int, height: Int): Item {
    val coordinates: MutableList<String> = ArrayList()
    for (x in xBorder + 1..xBorder + length) {
        for (y in yBorder + 1..yBorder + height) {
            coordinates.add("$x,$y")
        }
    }
    return Item(id, coordinates)
}

fun computeOverlappingCoordinates(coordinateLists: List<Item>): List<String> {
    val overlappingCoordinates: MutableList<String> = ArrayList()
    val allCoordinates: MutableList<String> = ArrayList()
    for (coordinateList in coordinateLists) {
        for (coordinate in coordinateList.coordinates) {
            if (!allCoordinates.contains(coordinate)) {
                allCoordinates.add(coordinate)
            } else {
                if (!overlappingCoordinates.contains(coordinate)) {
                    overlappingCoordinates.add(coordinate)
                }
            }
        }
    }
    findCoordinatesThatDontOverLap(coordinateLists, overlappingCoordinates)
    return overlappingCoordinates
}

private fun findCoordinatesThatDontOverLap(coordinateLists: List<Item>, overlappingCoordinates: List<String>) {
    for (coordinateList in coordinateLists) {
        var doesntOverlap = true
        for (coordinate in coordinateList.coordinates) {
            if (overlappingCoordinates.contains(coordinate)) {
                doesntOverlap = false
            }
        }
        if (doesntOverlap) {
            System.out.println("Doesn't overlap: " + coordinateList.id)
        }
    }
}

fun computeNumberOfOverlappingCoordinates(coordinatesList: List<Item>): Int {
    return computeOverlappingCoordinates(coordinatesList).size
}


data class Item(val id: Int, val coordinates: List<String>)