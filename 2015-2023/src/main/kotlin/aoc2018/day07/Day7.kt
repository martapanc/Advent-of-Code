package aoc2018.day07

import util.readInputLineByLine

fun readInputNumbers(path: String): List<Step> {
    val stepList = mutableListOf<Step>()
    val regex = Regex("Step ([A-Z]) must be finished before step ([A-Z]) can begin.")
    for (line in readInputLineByLine(path)) {
        val matchResult = regex.matchEntire(line) ?: throw RuntimeException()
        stepList.add(Step(matchResult.groupValues[1], matchResult.groupValues[2]))
    }
    return stepList
}

fun completeAllSteps(list: List<Step>, workers: Int, offset: Int): Int {
    val allSteps = list.flatMap { listOf(it.first, it.second) }.distinct()
    val completed = sortSteps(allSteps, list).toMutableList()
    val running = mutableListOf<Pair<String, Int>>()

    completed.clear()
    var seconds = 0;

    while (completed.size < allSteps.size) {
        val done = running.filter { it.second == seconds }
        completed.addAll(done.map { it.first })
        running.removeAll(done)

        val canStart = (allSteps - completed - running.map { it.first })
            .filterNot { sec -> list.any { it.second == sec && !completed.contains(it.first) } }
            .sorted()

        running.addAll(canStart
            .take(workers - running.size)
            .map { Pair(it, seconds + offset + (it[0] - 64).toInt()) }
        )
        seconds++
    }
    return seconds - 1
}

fun determineStepOrder(list: List<Step>): String {
    val allSteps = list.flatMap { listOf(it.first, it.second) }.distinct()
    return sortSteps(allSteps, list).joinToString("")
}

private fun sortSteps(allSteps: List<String>, list: List<Step>): List<String> {
    val completed = mutableListOf<String>()

    while (completed.size < allSteps.size) {
        val canStart = (allSteps - completed)
            .filterNot { s -> list.any { it.second == s && !completed.contains(it.first) } }
            .sorted()
        completed.add(canStart.first())
    }
    return completed
}

data class Step(val first: String, val second: String)
