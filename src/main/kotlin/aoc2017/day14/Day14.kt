package aoc2017.day14

import aoc2017.day10.convertListToASCII
import aoc2017.day10.processListPart2
import aoc2020.day20.Coord
import java.math.BigInteger

fun defragmentDisk(input: String): Int {
    return generateHashList(input).sumBy { it -> it.count { it == '1' } }
}

fun countRegions(input: String): Int {
    val hashArray = generateHashList(input).map { it -> it.map { (it - '0') }.toTypedArray() }.toTypedArray()
    val neighborCoords = listOf(Coord(1, 0), Coord(-1, 0), Coord(0, 1), Coord(0, -1))
    // Start from 2 to avoid overlapping with 0s and 1s
    var groupCount = 2

    for ((rowId, row) in hashArray.withIndex())
        for ((colId, char) in row.withIndex()) {
            if (char == 1) {
                hashArray[rowId][colId] = groupCount
                val edge = mutableListOf(listOf(rowId, colId, groupCount))
                // Implement BFS: progressively assign counter to neighbours until they've been all visited
                while (edge.isNotEmpty()) {
                    val (baseX, baseY, group) = edge.removeAt(0)
                    for (coord in neighborCoords) {
                        val x = baseX + coord.x
                        val y = baseY + coord.y
                        try {
                            if (hashArray[x][y] == 1) {
                                hashArray[x][y] = (group)
                                edge.add(listOf(x, y, group))
                            }
                        } catch (_: Exception) { }
                    }
                }
                groupCount++
            }
        }
    return groupCount - 2
}

private fun printHashList(list: List<String>) {
    for (line in list) {
        line.forEach { char -> if (char == '1') print("#") else print(".") }
        println()
    }
}

private fun generateHashList(input: String): List<String> {
    return (0 until 128).map {
        val hash = processListPart2(convertListToASCII("$input-$it"))
        BigInteger(hash, 16).toString(2).padStart(128, '0')
    }
}