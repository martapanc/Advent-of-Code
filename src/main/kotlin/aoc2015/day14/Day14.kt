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

fun runReindeerRace(reindeers: List<RacerReindeer>, raceTime: Int = 1000): Int? {
    return getDistancesAfterSeconds(reindeers, raceTime).values.maxOrNull()
}

private fun getDistancesAfterSeconds(reindeers: List<RacerReindeer>, raceTime: Int): Map<String, Int> {
    val distanceMap = mutableMapOf<String, Int>()
    for (racer in reindeers) {
        val sprintDistance = racer.sprintTime * racer.speed
        val div = raceTime / (racer.sprintTime + racer.restTime)
        val mod = raceTime % (racer.sprintTime + racer.restTime)
        val diff = if (mod <= racer.sprintTime) {
            racer.speed * mod
        } else racer.speed * racer.sprintTime
        distanceMap[racer.name] = sprintDistance * div + diff
    }
    return distanceMap
}

fun findWinningScore(reindeers: List<RacerReindeer>, raceTime: Int = 1000): Int? {
    val reindeerNames = reindeers.map { it.name }
    val reindeerScores = mutableMapOf<String, Int>()
    for (name in reindeerNames) reindeerScores[name] = 0
    for (i in 1..raceTime) {
        val distanceMap = getDistancesAfterSeconds(reindeers, i)
        val winningReindeers = mutableListOf<String>()
        for (entry in distanceMap) {
            if (entry.value == distanceMap.values.maxOrNull()!!) winningReindeers.add(entry.key)
        }
        for (wr in winningReindeers) {
            reindeerScores[wr] = reindeerScores[wr]!! + 1
        }
    }
    return reindeerScores.values.maxOrNull()
}

data class RacerReindeer(val name: String, val speed: Int, val sprintTime: Int, val restTime: Int)
