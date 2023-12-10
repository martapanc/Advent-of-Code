package aoc2023.day06

fun parse(lines: List<String>): List<RaceData> {
    val races = mutableListOf<RaceData>()
    val times = lines[0]
        .replace("Time:", "")
        .trim()
        .split("\\s+".toRegex())
        .map { it.toInt() }
    val distances = lines[1]
        .replace("Distance:", "")
        .trim()
        .split("\\s+".toRegex())
        .map { it.toInt() }

    times.indices.forEach { i ->
        races.add(RaceData(times[i], distances[i]))
    }
    return races
}

fun part1(races: List<RaceData>): Int {
    val results = mutableListOf<Int>()
    races.forEach { race ->
        var winningPossibilities = 0
        (0 .. race.time).forEach{t ->
            val distance = computeDistance(race.time, t)
            if (distance > race.distance) {
                winningPossibilities++
            }
        }
        results.add(winningPossibilities)
    }
    return results.reduce { acc, elem ->
        acc * elem
    }
}

fun part2(races: List<RaceData>): Long {
    return 0
}

fun computeDistance(time: Int, initSpeed: Int): Int {
    val availTime = time - initSpeed
    return availTime * initSpeed
}

data class RaceData (val time: Int, val distance: Int)