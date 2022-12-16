package aoc2022.day15

import util.Coord
import util.readInputLineByLine

fun readInputToSensorList(path: String): List<Sensor> {
    val sensors = mutableListOf<Sensor>()
    readInputLineByLine(path).forEach { line ->
        val split = line.replace("Sensor at ", "")
            .replace(": closest beacon is at ", ",")
            .replace("x=", "").replace(", y=", ",")
            .split(",").map { it.toInt() }
        sensors.add(Sensor(Coord(split[0], split[1]), Coord(split[2], split[3])))
    }
    return sensors
}

fun part1(sensors: List<Sensor>, row: Int): Int {
    val covered = mutableSetOf<Coord>()
    sensors.forEach { sensor ->
        covered.addAll(getCellsCoveredBySensorAtRow(sensor.coord, sensor.closestBeacon, row))
        covered.remove(sensor.closestBeacon)
    }
    return covered.count { it.y == row }
}

fun part2(sensors: List<Sensor>, max: Int): Int {
    val covered = mutableSetOf<Coord>()
    sensors.forEach { sensor ->
        covered.addAll(getCellsCoveredBySensorInRange(sensor.coord, sensor.closestBeacon, max))
    }

    val all = mutableSetOf<Coord>()
    (0 .. max).forEach { y ->
        (0 .. max).forEach { x ->
            all.add(Coord(x, y))
        }
    }

    all.removeAll(covered)
    val beacon = all.first()
    return beacon.x * 4000000 + beacon.y
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

fun getCellsCoveredBySensorAtRow(sensor: Coord, beacon: Coord, row: Int): Set<Coord> {
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

fun getCellsCoveredBySensorInRange(sensor: Coord, beacon: Coord, max: Int, min: Int = 0): Set<Coord> {
    val coveredCoords = mutableSetOf<Coord>()
    val mhDistance = sensor.manhattanDistance(beacon)
    val minY = if (sensor.y - mhDistance > min) sensor.y - mhDistance else min
    val maxY = if (sensor.y + mhDistance < max) sensor.y + mhDistance else max
    (minY .. maxY).forEach { y ->
        val mhDiff = mhDistance - Coord(sensor.x, y).manhattanDistance(sensor)
        val minX = if (sensor.x - mhDiff > min) sensor.x - mhDiff else min
        val maxX = if (sensor.x + mhDiff < max) sensor.x + mhDiff else max
        (minX .. maxX).forEach { x -> coveredCoords.add(Coord(x, y)) }
    }
    return coveredCoords
}

data class Sensor(val coord: Coord, val closestBeacon: Coord)
