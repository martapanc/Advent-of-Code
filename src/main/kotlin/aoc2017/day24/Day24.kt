package aoc2017.day24

import util.readInputLineByLine

fun readInputBridges(path: String): List<Bridge> {
    return readInputLineByLine(path)
        .map { it.split("/") }
        .map { Bridge(it[0].toInt(), it[1].toInt()) }
}

var maxStrength = 0
var maxStrengthOfLongest = 0
var maxDepth = 0

fun findStrongestBridge(list: List<Bridge>): Int {
    createBridgeTree(list, 0, Node(Bridge(0, 0), mutableListOf()))
    return maxStrength
}

fun findStrengthOfLongestBridge(list: List<Bridge>): Int {
    createBridgeTree(list, 0, Node(Bridge(0, 0), mutableListOf()))
    return maxStrengthOfLongest
}

fun createBridgeTree(list: List<Bridge>, start: Int, parentNode: Node, sumSoFar: Int = 0, depth: Int = 0): Node {
    val bridgesWithRightEnd = mutableListOf<Bridge>()
    for (item in list) {
        if (item.first == start || item.second == start) {
            bridgesWithRightEnd.add(item)
        }
    }
    if (bridgesWithRightEnd.isEmpty()) {
        if (parentNode.sum > maxStrength) maxStrength = parentNode.sum
        if (depth >= maxDepth && maxStrength >= maxStrengthOfLongest) {
            maxDepth = depth
            maxStrengthOfLongest = maxStrength
        }
        return parentNode
    }
    for (bridge in bridgesWithRightEnd) {
        val newStart = if (bridge.first == start) bridge.second else bridge.first
        parentNode.children.add(
            createBridgeTree(
                list.minus(bridge),
                newStart,
                Node(bridge, mutableListOf(), sumSoFar + bridge.first + bridge.second),
                sumSoFar + bridge.first + bridge.second,
                depth+1
            )
        )
    }
    return parentNode
}

data class Bridge(val first: Int, val second: Int)

data class Node(val bridge: Bridge, val children: MutableList<Node>, val sum: Int = 0)