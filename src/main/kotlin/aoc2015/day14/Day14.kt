package aoc2015.day14

import util.readInputLineByLine

fun readListOfReindeers(path: String): List<RacerReindeer> {
    val reindeerList = mutableListOf<RacerReindeer>()
    for (line in readInputLineByLine(path)) {
        val split = line.split(" ")
        reindeerList.add(RacerReindeer(split[0], split[3].toInt(), split[6].toInt(), split[13].toInt()))
    }
    return reindeerList
}

fun runReindeerRace(reindeers: List<RacerReindeer>, raceTime: Int = 2503): Int? {
    val distanceList = mutableListOf<Int>()
    for (racer in reindeers) {
        val sprintDistance = racer.sprintTime * racer.speed
        val div = raceTime / (racer.sprintTime + racer.restTime)
        val mod = raceTime % (racer.sprintTime + racer.restTime)
        val diff = if (mod <= racer.sprintTime) {
            racer.speed * mod
        } else racer.speed * racer.sprintTime
        distanceList.add(sprintDistance * div + diff)
    }
    return distanceList.maxOrNull()
}

data class RacerReindeer(val name: String, val speed: Int, val sprintTime: Int, val restTime: Int)
