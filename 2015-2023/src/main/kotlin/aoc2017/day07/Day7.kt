package aoc2017.day07

import util.readInputLineByLine
import kotlin.math.absoluteValue

fun readInputAndFindRoot(path: String): String {
    val leftSet = mutableSetOf<String>()
    val rightSet = mutableSetOf<String>()
    for (line in readInputLineByLine(path)) {
        val split = line.split(" -> ")
        val left = split[0].replace(Regex("\\(\\d+\\)"), "")
        leftSet += left.trim()
        if (split.size > 1) {
            rightSet.addAll(split[1].split(", "))
        }
    }
    return leftSet.subtract(rightSet).first()
}

fun readInputAndParseToTree(path: String): Int {
    val nodesByName = mutableMapOf<String, Node>()
    val parentChildPairs = mutableSetOf<Pair<Node, String>>()
    val rowRegex = """\w+""".toRegex()

    readInputLineByLine(path).forEach { it ->
        val groups = rowRegex.findAll(it).toList().map { it.value }
        val node = Node(groups[0], groups[1].toInt())
        nodesByName[node.nodeId] = node

        groups.drop(2).forEach {
            parentChildPairs.add(Pair(node, it))
        }
    }

    parentChildPairs.forEach { (parent, childName) ->
        with(nodesByName.getValue(childName)) {
            parent.children.add(this)
            this.parent = parent
        }
    }
    return nodesByName.values.firstOrNull { it.parent == null }!!.findImbalance()
}

data class Node(
    val nodeId: String,
    private val weight: Int,
    var parent: Node? = null,
    val children: MutableList<Node> = mutableListOf()
) {
    fun findImbalance(imbalance: Int? = null): Int {
        return if (imbalance != null && areChildrenBalanced) {
            weight - imbalance
        } else {
            val subtrees = children.groupBy { it.totalWeight }
            val imbalancedTree = subtrees.minByOrNull { it.value.size }!!.value.first()
            imbalancedTree.findImbalance(imbalance ?: subtrees.keys.reduce { a, b -> a - b }.absoluteValue)
        }
    }

    private val totalWeight: Int by lazy {
        weight + children.sumBy { it.totalWeight }
    }

    private val areChildrenBalanced: Boolean by lazy {
        children.map { it.totalWeight }.distinct().size == 1
    }
}