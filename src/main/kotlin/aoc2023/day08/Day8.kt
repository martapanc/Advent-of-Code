package aoc2023.day08

import util.lcm

fun parse(lines: List<String>): Network {
    val input = lines[0]
    val nodes = mutableMapOf<String, Node>()
    (2 until lines.size).forEach { i ->
        val split = lines[i].split(" = ")
        val directions = split[1]
            .replace("(", "")
            .replace(")", "")
            .trim()
            .split(", ")
        nodes[split[0]] = Node(directions[0], directions[1])
    }
    return Network(input, nodes)
}

fun part1(network: Network): Int {
    var count = 0
    var zzzWasReached = false
    var current = "AAA"
    var i = 0

    while (!zzzWasReached) {
        if (i == network.input.length) {
            i = 0
        }
        val direction = network.input[i]
        current = if (direction == 'L') {
            network.nodes[current]!!.left
        } else {
            network.nodes[current]!!.right
        }
        count++
        i++
        if (current == "ZZZ") {
            zzzWasReached = true
        }
    }

    return count
}

fun part2(network: Network): Long {
    val nodesEndingWithA = network.nodes.keys.filter { it.endsWith("A") }
    val map = mutableMapOf<String, Long>()

    nodesEndingWithA.forEach {
        var zWasReached = false
        var count = 0L
        var i = 0
        var current = it
        while (!zWasReached) {
            if (i == network.input.length) {
                i = 0
            }
            val direction = network.input[i]
            current = if (direction == 'L') {
                network.nodes[current]!!.left
            } else {
                network.nodes[current]!!.right
            }
            count++
            i++
            if (current[2] == 'Z') {
                zWasReached = true
            }
        }
        map[it] = count
    }
    return map.values.reduce { acc, num -> lcm(acc, num) }
}

data class Network(val input: String, val nodes: Map<String, Node>)

data class Node(val left: String, val right: String)
