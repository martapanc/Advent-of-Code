package aoc2022.day15

import util.readInputLineByLine
import kotlin.math.abs

fun readInputToSensorList(path: String): List<Sensor> {
    val sensors = mutableListOf<Sensor>()
    readInputLineByLine(path).forEach { line ->
        val split = line.replace("Sensor at ", "")
            .replace(": closest beacon is at ", ",")
            .replace("x=", "").replace(", y=", ",")
            .split(",").map { it.toLong() }
        sensors.add(Sensor(Coord(split[0], split[1]), Coord(split[2], split[3])))
    }
    return sensors
}

fun part1(sensors: List<Sensor>, row: Long): Int {
    val covered = mutableSetOf<Coord>()
    sensors.forEach { sensor ->
        covered.addAll(getCellsCoveredBySensorAtRow(sensor.coord, sensor.closestBeacon, row))
        covered.remove(sensor.closestBeacon)
    }
    return covered.count { it.y == row }
}

fun part2(sensors: List<Sensor>): Int {
    return 0
}

fun getCellsCoveredBySensor(sensor: Coord, beacon: Coord): Set<Coord> {
    val coveredCoords = mutableSetOf<Coord>()
    val mhDistance = sensor.manhattanDistance(beacon)
    (sensor.y - mhDistance .. sensor.y + mhDistance).forEach { y ->
        val mhDiff = mhDistance - Coord(sensor.x, y).manhattanDistance(sensor)
        (sensor.x - mhDiff .. sensor.x + mhDiff).forEach { x -> coveredCoords.add(Coord(x, y)) }
    }
    return coveredCoords
}

fun getCellsCoveredBySensorAtRow(sensor: Coord, beacon: Coord, row: Long): Set<Coord> {
    val coveredCoords = mutableSetOf<Coord>()
    val mhDistance = sensor.manhattanDistance(beacon)
    val minY = sensor.y - mhDistance
    val maxY = sensor.y + mhDistance
    if (row in minY..maxY) {
        val mhDiff = mhDistance - Coord(sensor.x, row).manhattanDistance(sensor)
        (sensor.x - mhDiff .. sensor.x + mhDiff).forEach { x -> coveredCoords.add(Coord(x, row)) }
    }
    return coveredCoords
}

data class Coord(val x: Long, val y: Long)

fun Coord.manhattanDistance(coord: Coord): Long = abs(this.x - coord.x) + abs(this.y - coord.y)

data class Sensor(val coord: Coord, val closestBeacon: Coord)
