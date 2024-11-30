package aoc2017.day13

import util.readInputLineByLine

fun readInputToMap(path: String): List<Layer> {
    return readInputLineByLine(path)
        .map { it.split(": ") }
        .map { Layer(it[0].toInt(), it[1].toInt()) }
}

fun computeTripSeverity(layers: List<Layer>): Int {
    return layers.filter { it.caughtAt(0) }.sumBy { it.getSeverity() }
}

fun computeTripSeverityPart2(layers: List<Layer>): Int {
    return generateSequence(0, Int::inc)
        .filter { time -> layers.none { it.caughtAt(time) } }
        .first()
}

data class Layer(val depth: Int, val range: Int) {

    private fun getLayerPositionAtTime(second: Int): Int {
        return (second + depth) % ((range - 1) * 2)
    }

    fun caughtAt(second: Int): Boolean = getLayerPositionAtTime(second) == 0

    fun getSeverity(): Int = depth * range
}