package aoc2016.day15

import util.readInputLineByLine

fun readInputToList(path: String): List<Disc> {
    val discList = mutableListOf<Disc>()
    val regex = Regex("Disc #(\\d) has (\\d+) positions; at time=0, it is at position (\\d+).")
    readInputLineByLine(path)
        .map { regex.matchEntire(it)!! }
        .mapTo(discList) { Disc(it.groupValues[1].toInt(), it.groupValues[2].toInt(), it.groupValues[3].toInt()) }
    return discList
}

fun playGameOfDiscs(list: List<Disc>, isPart2: Boolean = false): Int {
    var time = 0
    val mutableList = list.toMutableList()
    if (isPart2)
        mutableList.add(Disc(7, 11, 0))
    while (!areDiscsOpen(mutableList, time)) {
        time++
    }
    return time
}

private fun areDiscsOpen(list: List<Disc>, time: Int): Boolean {
    for (disc in list) {
        if ((disc.posAtTime0 + time + disc.discId) % disc.positions != 0) {
            return false
        }
    }
    return true
}

data class Disc(val discId: Int, val positions: Int, val posAtTime0: Int)
