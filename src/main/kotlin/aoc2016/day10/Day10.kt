package aoc2016.day10

import util.readInputLineByLine

private val valueRegex = Regex("value (\\d+) goes to bot (\\d+)")
private val giveRegex = Regex("bot (\\d+) gives low to (\\w+) (\\d+) and high to (\\w+) (\\d+)")

fun readInputAndRun(path: String, output: Set<Int> = setOf(17, 61)): Int {
    val (bots, instructions) = getBotsAndActions(path)
    while (output !in bots.values) {
        loop(bots, instructions)
    }
    return bots.filter { it.value == output }.keys.first()
}

fun readInputAndRunPart2(path: String): Int {
    val (bots, actions) = getBotsAndActions(path)
    while (bots.any { it.value.size == 2 } && actions.isNotEmpty()) {
        loop(bots, actions)
    }
    return (-3..-1)
        .map { bots[it].orEmpty().sum() }
        .reduce { a, b -> a * b }
        .toInt()
}

private fun getBotsAndActions(path: String):
        Pair<MutableMap<Int, MutableSet<Int>>, MutableMap<Int, HiLoPair>> {
    val bots = mutableMapOf<Int, MutableSet<Int>>()
    val actions = mutableMapOf<Int, HiLoPair>()

    val (values, gives) = readInputLineByLine(path).partition { valueRegex.matches(it) }

    values.forEach { command ->
        valueRegex.matchEntire(command)?.let { match ->
            val (value, id) = match.destructured
            bots.getOrPut(id.toInt()) { mutableSetOf() } += value.toInt()
        }
    }

    gives.forEach { command ->
        giveRegex.matchEntire(command)?.let { match ->
            val (id, lowType, lowId, highType, highId) = match.destructured
            actions[id.toInt()] = HiLoPair(
                if (lowType == "output") -lowId.toInt() - 1 else lowId.toInt(),
                if (highType == "output") -highId.toInt() - 1 else highId.toInt()
            )
        }
    }
    return Pair(bots, actions)
}

private fun loop(bots: MutableMap<Int, MutableSet<Int>>, instructions: MutableMap<Int, HiLoPair>) {
    val current = bots.filter { it.value.size == 2 }
    current
        .map { bot -> instructions.getOrDefault(bot.key, HiLoPair(0, 0)) to bot.value }
        .forEach { (action, value) ->
            val min = value.minOrNull() ?: Int.MIN_VALUE
            val max = value.maxOrNull() ?: Int.MAX_VALUE
            bots.getOrPut(action.first) { mutableSetOf() } += min
            bots.getOrPut(action.second) { mutableSetOf() } += max
        }

    current.keys
        .filter { it >= 0 }
        .forEach { id ->
            bots.remove(id)
            instructions.remove(id)
        }
}

data class HiLoPair(var first: Int, var second: Int)