package aoc2016.day22

import util.Coord
import util.readInputLineByLine
import kotlin.math.abs

fun readInputToMap(path: String): Map<Coord, Node> {
    val map = mutableMapOf<Coord, Node>()
    val regex = Regex("/dev/grid/node-x([0-9]+)-y([0-9]+)\\s+([0-9]+)T\\s+([0-9]+)T\\s+([0-9]+)T\\s+([0-9]+)%")
    for ((index, line) in readInputLineByLine(path).withIndex())
        if (index != 0 && index != 1) {
            val match = regex.matchEntire(line) ?: throw RuntimeException()
            val numericMatch = match.groupValues.subList(1, match.groupValues.size).map { it.toInt() }
            map[Coord(numericMatch[0], numericMatch[1])] = Node(numericMatch[2], numericMatch[3], numericMatch[4])
        }
    return map
}

fun countViableNodePairs(map: Map<Coord, Node>): Int {
    val viablePairs = mutableListOf<Pair<Node, Node>>()
    for (nodeEntry in map.entries)
        map.entries
            .filter { nodeEntry.key != it.key && nodeEntry.value.used != 0 && nodeEntry.value.used <= it.value.available }
            .mapTo(viablePairs) { Pair(nodeEntry.value, it.value) }
    return viablePairs.count()
}

fun computeFewestStepsToMoveData(map: Map<Coord, Node>): Long {
    val hiX = 31
    val hiY = 29
    var wallStart: Node? = null
    var unusedNode: Node? = null

    for (y in 0 until hiY) {
        for (x in 0 until hiX) {
            val node = map[Coord(x, y)]
            if (node!!.used == 0) {
                unusedNode = node
                unusedNode.x = x
                unusedNode.y = y
            } else if (node.size > 250) {
                if (wallStart == null) {
                    wallStart = map[Coord(x - 1, y)]
                    wallStart!!.x = x - 1
                    wallStart.y = y
                }
            }
        }
    }
    // Manhattan distance from the empty disk to the edge of the 'wall', from there to the goal, and then shifting the empty node around.
    var result = abs(unusedNode!!.x - wallStart!!.x) + abs(unusedNode.y - wallStart.y)
    result += abs(wallStart.x - hiX) + wallStart.y
    return (result + 5 * (hiX - 1)).toLong()
}

fun printNodes(map: Map<Coord, Node>) {
    for (y in 0..29) {
        for (x in 0..31) {
            val node = map[Coord(x, y)]!!
            print(node.used.toString().padStart(3) + "/" + node.size.toString().padStart(3) + " -- ")
        }
        println()
    }
}

data class Node(val size: Int, val used: Int, val available: Int, var x: Int = 0, var y: Int = 0)
