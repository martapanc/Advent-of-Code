package aoc2022.day16

//fun part1(list: List<String>): Int {
//    return 0
//}
//
//fun part2(list: List<String>): Int {
//    return 0
//}

data class ValveNode(
    val name: String,
    val flowRate: Int,
    val neighbors: List<String>,
    val distanceMap: MutableMap<String, Int> = mutableMapOf()
) {
    companion object {
        fun of(input: String): ValveNode = ValveNode(
            input.drop(6).split(" has ")[0],
            input.split(";")[0].split("=")[1].toInt(),
            input.split("valves ", "valve ")[1].split(", ")
        )
    }

    fun computeDistances(getNode: (String) -> ValveNode) = apply {
        distanceMap[name] = 0
        ArrayDeque<ValveNode>().let { queue ->
            queue.add(this)
            val visited = mutableSetOf<String>()
            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()
                val distance = current.distanceMap[name]!!
                visited.add(current.name)
                current.neighbors.filter { it !in visited }.forEach { n ->
                    val neighbor = getNode(n)
                    neighbor.distanceMap[name] = distance + 1
                    queue.addLast(neighbor)
                }
            }
        }
        distanceMap.remove(name)
    }
}

private fun List<ValveNode>.computeAllDistances() =
    filter {
        it.flowRate > 0
    }.forEach { r ->
        r.computeDistances { node ->
            this.first { it.name == node }
        }
    }

private fun ValveNode.restOf(opened: Set<String>, timeLeft: Int) =
    distanceMap.filter { (key, timeNeeded) -> key !in opened && timeNeeded + 1 <= timeLeft }

private fun List<ValveNode>.maxPath(opened: Set<String>, node: ValveNode, timeLeft: Int, sum: Int, open: Int): Int = when {
    timeLeft < 0 -> 0
    timeLeft == 0 -> sum
    timeLeft == 1 -> sum + open
    node.distanceMap.all { (key, _) -> key in opened } -> sum + timeLeft * open
    else -> node.restOf(opened, timeLeft)
        .map { (nNode, distance) ->
            val nextNode = first { it.name == nNode }
            maxPath(
                opened + node.name,
                nextNode,
                timeLeft - (distance + 1),
                sum + (distance + 1) * open,
                open + nextNode.flowRate
            )
        }.plus(sum + timeLeft * open)
        .max()
}

private fun allSelections(maximum: Int): Sequence<List<Int>> = sequence {
    if (maximum == 0) {
        yield(emptyList())
    } else {
        yieldAll(allSelections(maximum - 1).map { it + maximum })
        yieldAll(allSelections(maximum - 1))
    }
}

private fun List<ValveNode>.makeAllNonFlow(these: List<ValveNode>) = map { v ->
    if (v in these) {
        v.copy(flowRate = 0, distanceMap = mutableMapOf())
    } else
        v.copy(distanceMap = mutableMapOf())
}

private fun List<ValveNode>.partition(): Sequence<Pair<List<ValveNode>, List<ValveNode>>> {
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

private fun List<ValveNode>.maxPathWithElephantHelp(timeLeft: Int): Int = partition().map { (a, b) ->
    val startNodeYou = a.first { it.name == "AA" }
    val startNodeElephant = b.first { it.name == "AA" }
    val you = a.maxPath(emptySet(), startNodeYou, timeLeft, 0, 0)
    val elephant =  b.maxPath(emptySet(), startNodeElephant, timeLeft, 0, 0)
    you + elephant
}.max()

fun part1(input: List<String>): Int {
    val nodes = input.map { ValveNode.of(it) }
    nodes.computeAllDistances()
    val startNode = nodes.find { it.name == "AA" }!!
    return nodes.maxPath(emptySet(), startNode, 30, 0, 0)
}

fun part2(input: List<String>): Int {
    val nodes = input.map { ValveNode.of(it) }
    return nodes.maxPathWithElephantHelp( 26)
}
