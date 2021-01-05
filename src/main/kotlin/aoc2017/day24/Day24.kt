package aoc2017.day24

import util.readInputLineByLine

fun readInputBridges(path: String): List<Bridge> {
    return readInputLineByLine(path)
        .map { it.split("/") }
        .map { Bridge(it[0].toInt(), it[1].toInt()) }
}

fun findStrongestBridge(list: List<Bridge>): Int {
    val node = createBridgeTree(list, 0, Node(Bridge(0, 0), mutableListOf()))
    return -1
}

fun createBridgeTree(list: List<Bridge>, start: Int, parentNode: Node): Node {
    if (list.size == 1) {
        return Node(list[0], mutableListOf())
    }
    val bridgesWithRightEnd = mutableListOf<Bridge>()
    for (item in list) {
        if (item.first == start || item.second == start) {
            bridgesWithRightEnd.add(item)
        }
    }
    if (bridgesWithRightEnd.isEmpty()) {
        return parentNode
    }
    for (bridge in bridgesWithRightEnd) {
        val newStart = if (bridge.first == start) bridge.second else bridge.first
        parentNode.children.add(createBridgeTree(list.minus(bridge), newStart, Node(bridge, mutableListOf())))
    }
    return parentNode
}

data class Bridge(val first: Int, val second: Int)

data class Node(val bridge: Bridge, val children: MutableList<Node>)