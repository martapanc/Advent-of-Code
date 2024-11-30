package aoc2017.day12

import util.readInputLineByLine

fun readInputToMap(path: String): Map<Int, Set<Int>> {
    val programs = mutableMapOf<Int, Set<Int>>()
    for (line in readInputLineByLine(path)) {
        val split = line.split(" <-> ")
        val rightSplit = split[1].split(", ").map { it.toInt() }
        programs[split[0].toInt()] = rightSplit.toMutableSet()
    }
    return programs
}

fun findConnectedPrograms(programNodes: Map<Int, Set<Int>>, isPart1: Boolean = true): Int {
    if (isPart1)
        return findConnectedProgramsRec(0, programNodes).size

    val visited = mutableSetOf<Int>()

    return programNodes.keys
        .asSequence()
        .filter { it !in visited }
        .map {
            findConnectedProgramsRec(it, programNodes)
                .apply { visited.addAll(this) }
        }.count()
}

fun findConnectedProgramsRec(
    nodeId: Int,
    programNodes: Map<Int, Set<Int>>,
    visited: MutableSet<Int> = mutableSetOf()
): Set<Int> {
    if (nodeId !in visited) {
        visited.add(nodeId)
        programNodes.getValue(nodeId).forEach { findConnectedProgramsRec(it, programNodes, visited) }
    }
    return visited
}

