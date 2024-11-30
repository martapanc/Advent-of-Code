package aoc2023.day25

import java.io.File

fun parse(lines: List<String>): List<String> {
    val output = mutableListOf<String>()
    lines.forEach { line ->
        val split = line.split(":").map { it.trim() }
        val links = split[1].split(" ").map { it.trim() }
        for (link in links) {
            println("%s %s".format(split[0], link))
        }
    }
    println()
    return output
}

fun part1(input: List<String>): Long {
    return 0
}

fun part2(input: List<String>): Long {
    return 0
}

internal fun minimumCutPhase(graph: Collection<Vertex>): Triple<Vertex, Vertex, Int> {
    val remaining: MutableSet<Vertex> = HashSet(graph)
    val connected = LinkedHashMap<Vertex, Int>(remaining.size).apply {
        val firstVertex = remaining.firstOrNull() ?: error("Graph is empty")
        put(firstVertex, 0)
        remaining.remove(firstVertex)
    }

    while (remaining.isNotEmpty()) {
        val weighted = remaining.associateWith { v -> v.adjacentEdges.filter { it.first in connected }.sumOf { it.second.weight } }
        val (maxVertex, cutWeight) = weighted.maxByOrNull { it.value } ?: break
        remaining.remove(maxVertex)
        connected[maxVertex] = cutWeight
    }

    val (t, cutWeight) = connected.entries.lastOrNull() ?: error("Connected map is empty")
    val s = connected.entries.lastOrNull { it.key != t }?.key ?: error("Connected map has only one element")

    return Triple(s, t, cutWeight)
}

fun main() {
    /**
     * [Stoer–Wagner minimum cut algorithm.](https://en.wikipedia.org/wiki/Stoer%E2%80%93Wagner_algorithm)
     */
    val vertices = mutableMapOf<String, Vertex>()

    fun getOrPutVertex(id: String) = vertices.getOrPut(id) { Vertex(listOf(id)) }
    fun mergeVertices(a: Vertex, b: Vertex) {
        vertices.remove(a.id)!!.removeEdge(b.id)
        vertices.remove(b.id)!!.removeEdge(a.id)
        val union = a.adjacentEdges + b.adjacentEdges
        val adjacentBoth =  union.filter { it.first in a.adjacent intersect b.adjacent }.toSet()
        val adjacentXor = (union - adjacentBoth).associate { it.first to it.second.weight }
        val grouped= adjacentBoth.groupingBy(Pair<Vertex, *>::first).fold(0) { weight, (_, edge) -> weight + edge.weight }
        union.forEach { (vertex, edge) -> vertex.removeEdge(edge) }
        val merged = Vertex(buildSet { addAll(a.ids); addAll(b.ids) })
        sequenceOf(adjacentXor, grouped).flatMap { it.asSequence() }.forEach { (vertex, weight) ->
            merged.getOrAddEdge(vertex, weight=weight).second.also { vertex.getOrPutEdge(merged, it) }
        }
        vertices[merged.id] = merged
    }

    File("src/main/kotlin/aoc2023/day25/assets/input").useLines { file -> file.forEach { line ->
        val (from, toList) = line.split(":")
        val fromVertex = getOrPutVertex(from.trim())
        toList.split(" ").filterNot { it.isEmpty() }.map(::getOrPutVertex).forEach { toVertex ->
            fromVertex.getOrAddEdge(toVertex, weight=1).second.also { toVertex.getOrPutEdge(fromVertex, it) } }
    } }
    var minCut = Int.MAX_VALUE
    var bestCut: Pair<Vertex, Vertex>? = null
    val graphSize = vertices.size.toLong()
    while(1 < vertices.size) {

        val (cutS, cutT, cutWeight) = minimumCutPhase(vertices.values)
        if (cutWeight < minCut) {
            minCut = cutWeight
            bestCut = Pair(cutS, cutT)
        }

        mergeVertices(cutS, cutT)
    }

    val bestCutSizeOfT = bestCut!!.second.ids.size.toLong()
    println("Min-cut weight: $minCut / size of T: $bestCutSizeOfT")
    println("Product = ${bestCutSizeOfT * (graphSize - bestCutSizeOfT)}") // 583338
}

internal data class Vertex(val id: String): Comparable<Vertex> {
    private val edgesField = mutableMapOf<String, Pair<Vertex, Edge>>()
    lateinit var ids: Set<String>

    init {
        if (!::ids.isInitialized)
            ids = id.split(',').toSet()
    }

    val adjacent get() = edgesField.values.map(Pair<Vertex, *>::first).toSet()
    val adjacentEdges get() = edgesField.values.toSet()

    constructor(ids: Iterable<String>): this(ids.cs()) { this.ids = when (ids) {
        is Set -> ids
        else -> ids.toSet()
    } }

    override fun compareTo(other: Vertex) = this.id.compareTo(other.id)

    fun getOrAddEdge(to: Vertex, weight: Int) = edgesField.getOrPut(to.id) { Pair(to, Edge(this, to, weight)) }
    fun getOrPutEdge(to: Vertex, edge: Edge) = edge.also { assert (this in it.vertices) }.let {
        edgesField.getOrPut(to.id) { Pair(to, it) } }
    fun removeEdge(id: String) = edgesField.remove(id)
    fun removeEdge(edge: Edge) = edgesField.remove(edge.vertices.map(Vertex::id).single { it != id })!!
}

internal data class Edge(val vertices: Set<Vertex>, val weight: Int) {
    val id = vertices.sorted().joinToString("-", transform = Vertex::id)

    constructor(left: Vertex, right: Vertex, weight: Int): this(setOf(left, right), weight)

    init {
        assert (2 == vertices.size)
    }

    override fun toString() = "[${id.replace("-", "]-[")}]: $weight"

    override fun equals(other: Any?) = if (other is Edge) id == other.id else false

    override fun hashCode() = id.hashCode()
}

internal fun Iterable<String>.cs() = this.sorted().joinToString(",")