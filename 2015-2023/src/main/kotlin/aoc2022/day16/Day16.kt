package aoc2022.day16

import util.readInputLineByLine

fun readInputToValves(path: String): List<Valve> {
    return readInputLineByLine(path).map { Valve.fromInputString(it) }
}

fun part1(valves: List<Valve>): Int {
    valves.computeAllDistances()
    val start = valves.find { it.name == "AA" }!!
    return valves.maxPath(emptySet(), start, 30, 0, 0)
}

fun part2(valves: List<Valve>): Int = valves.maxPathWithElephantHelp(26)

data class Valve(
    val name: String,
    val flowRate: Int,
    val neighbors: List<String>,
    val distanceMap: MutableMap<String, Int> = mutableMapOf()
) {
    companion object {
        fun fromInputString(input: String): Valve = Valve(
            input.drop(6).split(" has ")[0],
            input.split(";")[0].split("=")[1].toInt(),
            input.split("valves ", "valve ")[1].split(", ")
        )
    }

    fun computeDistances(getNeighborValve: (String) -> Valve) = apply {
        distanceMap[name] = 0
        ArrayDeque<Valve>().let { queue ->
            queue.add(this)
            val visited = mutableSetOf<String>()
            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()
                val distance = current.distanceMap[name]!!
                visited.add(current.name)
                current.neighbors.filter { it !in visited }.forEach { n ->
                    val neighbor = getNeighborValve(n)
                    neighbor.distanceMap[name] = distance + 1
                    queue.addLast(neighbor)
                }
            }
        }
        distanceMap.remove(name)
    }
}

private fun List<Valve>.computeAllDistances() =
    filter { it.flowRate > 0 }.forEach { valve ->
        valve.computeDistances { neighborValve ->
            this.first { it.name == neighborValve }
        }
    }

private fun Valve.restOf(opened: Set<String>, timeLeft: Int) =
    distanceMap.filter { (key, timeNeeded) -> key !in opened && timeNeeded + 1 <= timeLeft }

private fun List<Valve>.maxPath(opened: Set<String>, valve: Valve, timeLeft: Int, sum: Int, open: Int): Int {
    return when {
        timeLeft < 0 -> 0
        timeLeft == 0 -> sum
        timeLeft == 1 -> sum + open
        valve.distanceMap.all { (key, _) -> key in opened } -> sum + timeLeft * open
        else -> valve.restOf(opened, timeLeft)
            .map { (nValve, distance) ->
                val nextValve = first { it.name == nValve }
                maxPath(
                    opened + valve.name,
                    nextValve,
                    timeLeft - (distance + 1),
                    sum + (distance + 1) * open,
                    open + nextValve.flowRate
                )
            }.plus(sum + timeLeft * open).max()
    }
}

private fun allSelections(maximum: Int): Sequence<List<Int>> = sequence {
    if (maximum == 0) {
        yield(emptyList())
    } else {
        yieldAll(allSelections(maximum - 1).map { it + maximum })
        yieldAll(allSelections(maximum - 1))
    }
}

private fun List<Valve>.makeAllNonFlow(valves: List<Valve>) = map { valve ->
    if (valve in valves) {
        valve.copy(flowRate = 0, distanceMap = mutableMapOf())
    } else {
        valve.copy(distanceMap = mutableMapOf())
    }
}

private fun List<Valve>.maxPathWithElephantHelp(timeLeft: Int): Int {
    return partition().map { (a, b) ->
        val myStartValve = a.first { it.name == "AA" }
        val elephantStartValve = b.first { it.name == "AA" }
        val me = a.maxPath(emptySet(), myStartValve, timeLeft, 0, 0)
        val elephant = b.maxPath(emptySet(), elephantStartValve, timeLeft, 0, 0)
        me + elephant
    }.max()
}

private fun List<Valve>.partition(): Sequence<Pair<List<Valve>, List<Valve>>> {
    val relevant = filter { it.flowRate > 0 }
    return allSelections(relevant.size).map { ind ->
        val listLeft = relevant.filterIndexed { i, _ -> i + 1 in ind }
        val listRight = relevant - listLeft.toSet()
        val left = makeAllNonFlow(listLeft)
        val right = makeAllNonFlow(listRight)
        left.computeAllDistances()
        right.computeAllDistances()
        left to right
    }
}
