package aoc2022.day15

import util.Coord

fun solve(sensors: List<Sensor>, max: Int): Long {
    val sumOfAll = max.toLong() * (max.toLong() + 1) / 2
    (0..max).forEach { row ->
        val covered = mutableSetOf<RangeCovered>()
        sensors.forEach { sensor ->
            val range = getCellsCoveredBySensorAtRowMinMax(sensor.coord, sensor.closestBeacon, row, max)
            if (range != null) {
                covered.add(range)
            }
        }
        val fullCoveredRange = getFullCoveredRange(covered)
        val fullRangeSum = fullCoveredRange.max.toLong() * (fullCoveredRange.max + 1) / 2
        if (fullRangeSum != sumOfAll) {
            return (fullCoveredRange.max + 1).toLong() * 4000000 + row
        }
    }
    return 0
}


fun getCellsCoveredBySensorAtRowMinMax(sensor: Coord, beacon: Coord, row: Int, max: Int): RangeCovered? {
    val mhDistance = sensor.manhattanDistance(beacon)
    if (row < sensor.y - mhDistance || row > sensor.y + mhDistance) {
        return null
    }

    val mhDiff = mhDistance - Coord(sensor.x, row).manhattanDistance(sensor)
    val minX = if (sensor.x - mhDiff > 0) sensor.x - mhDiff else 0
    val maxX = if (sensor.x + mhDiff < max) sensor.x + mhDiff else max

    return RangeCovered(minX, maxX)
}

fun getFullCoveredRange(ranges: Set<RangeCovered>): RangeCovered {
    val sortedRanges = ranges.sortedWith(compareBy({ it.min }, { it.max }))
    val fullRange = sortedRanges[0]
    for (index in 1 until sortedRanges.size) {
        val currRange = sortedRanges[index]
        if (fullRange.min <= currRange.min && fullRange.max >= currRange.max) continue

        if (fullRange.min > currRange.min && fullRange.max < currRange.max) {
            fullRange.min = currRange.min
            fullRange.max = currRange.max
        } else if (currRange.min <= fullRange.max) {
            fullRange.max = currRange.max
        } else { // currRange.min > fullRange.max, meaning there's a gap between the 2, and we found our beacon
            break
        }
    }
    return fullRange
}

data class RangeCovered(var min: Int, var max: Int)
