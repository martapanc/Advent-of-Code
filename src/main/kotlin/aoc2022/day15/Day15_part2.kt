package aoc2022.day15

fun solve(sensors: List<Sensor>, max: Long): Long {
    (0 .. max).forEach { row ->
        val covered = mutableSetOf<Coord>()
        sensors.forEach { sensor ->
            covered.addAll(getCellsCoveredBySensorAtRowMinMax(sensor.coord, sensor.closestBeacon, row, max))
        }
        if (covered.size.toLong() != max + 1) {
            val sumOfAllXs = covered.sumOf { it.x }
            val sumOfFirstNnumbers = max * (max + 1) / 2
            return (sumOfFirstNnumbers - sumOfAllXs) * 4000000 + row
        }
    }
    return 0
}


fun getCellsCoveredBySensorAtRowMinMax(sensor: Coord, beacon: Coord, row: Long, max: Long): Set<Coord> {
    val mhDistance = sensor.manhattanDistance(beacon)
    val minY = sensor.y - mhDistance
    val maxY = sensor.y + mhDistance
    if (row < minY || row > maxY) {
        return setOf()
    }

    val coveredCoords = mutableSetOf<Coord>()
    val mhDiff = mhDistance - Coord(sensor.x, row).manhattanDistance(sensor)
    val minX = if (sensor.x - mhDiff > 0) sensor.x - mhDiff else 0
    val maxX = if (sensor.x + mhDiff < max) sensor.x + mhDiff else max
    (minX .. maxX).forEach { x -> coveredCoords.add(Coord(x, row)) }
    return coveredCoords
}
