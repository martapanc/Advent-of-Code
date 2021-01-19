package aoc2020.day20

import java.io.File
import kotlin.math.sqrt

fun readInputToList(path: String): List<JigsawTile> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    val list = mutableListOf<JigsawTile>()
    var array = mutableMapOf<Coord, Char>()
    var tileId = 0
    var y = 0
    for (line in lineList)
        when {
            line == "" -> {
                list.add(JigsawTile(tileId, array, getListOfBorders(array)))
                array = mutableMapOf()
                y = 0
            }
            line.startsWith("Tile") -> tileId = line.replace("Tile ", "").replace(":", "").toInt()
            else -> {
                for ((index, x) in (0..9).withIndex()) {
                    array[Coord(x, y)] = line[index]
                }
                y++
            }
        }
    return list
}

fun findCornersCheckProduct(tilesList: List<JigsawTile>): Long {
    var checkProduct: Long = 1
    findTileNeighbors(tilesList).entries
        .asSequence()
        .filter { it.value.size == 2 }
        .forEach { checkProduct *= it.key }
    return checkProduct
}

fun findTileNeighbors(tilesList: List<JigsawTile>): Map<Int, List<NeighborEdge>> {
    val tileNeighborCounts = mutableMapOf<Int, List<NeighborEdge>>()
    for (tile in tilesList)
        tile.borderList.forEach { border ->
            for (candidateTile in tilesList)
                if (candidateTile.tileId != tile.tileId &&
                    (candidateTile.borderList.contains(border) || candidateTile.borderList.contains(border.reversed()))
                ) {
                    val list = tileNeighborCounts[tile.tileId]
                    val neighborsList = list?.toMutableList() ?: mutableListOf()
                    neighborsList.add(NeighborEdge(candidateTile.tileId, border))
                    tileNeighborCounts[tile.tileId] = neighborsList
                }
        }
    println(tileNeighborCounts)
    return tileNeighborCounts
}

private fun getListOfBorders(array: MutableMap<Coord, Char>): List<String> {
    var topHorizontal = ""
    var bottomHorizontal = ""
    var leftMost = ""
    var rightMost = ""
    (0..9).forEach { i ->
        topHorizontal += array[Coord(i, 0)]
        rightMost += array[Coord(9, i)]
        bottomHorizontal += array[Coord(i, 9)]
        leftMost += array[Coord(0, i)]
    }
    return listOf(topHorizontal, bottomHorizontal, leftMost, rightMost)
}

fun countHashesExcludingBorders(tilesList: List<JigsawTile>): Int {
    var borderCount = 0
    var innerCount = 0
    for (tile in tilesList) {
        val totalCount = tile.array.count { it.value == '#' }
        val currBorderCount = tile.borderList
            .mapIndexed { index, border ->
                if (index == 1 || index == 2) {
                    border.reversed().substring(0, border.length - 1).count { it == '#' }
                } else {
                    border.substring(0, border.length - 1).count { it == '#' }
                }
            }
            .sum()
        borderCount += currBorderCount
        innerCount += (totalCount - currBorderCount)
    }
    return innerCount
}

fun rotateOrFlip(tile: JigsawTile, rotate: Boolean = false, deg: Int = 0, flip: Char = '0'): JigsawTile {
    var array = tile.array
    var editedArray = mutableMapOf<Coord, Char>()
    if (rotate) {
        when (deg) {
            90 -> editedArray = rotate90DegClockwise(array).toMutableMap()
            180 -> {
                editedArray = rotate90DegClockwise(array).toMutableMap()
                editedArray = rotate90DegClockwise(editedArray).toMutableMap()
            }
            270 -> {
                editedArray = rotate90DegClockwise(array).toMutableMap()
                editedArray = rotate90DegClockwise(editedArray).toMutableMap()
                editedArray = rotate90DegClockwise(editedArray).toMutableMap()
            }
        }
        array = editedArray
    }
    if (flip == 'v') {
        editedArray = flipVertically(array).toMutableMap()
    }
    if (flip == 'h') {
        editedArray = flipHorizontally(array).toMutableMap()
    }
    return JigsawTile(tile.tileId, editedArray, tile.borderList)
}

private fun rotate90DegClockwise(array: Map<Coord, Char>): Map<Coord, Char> {
    val rotatedArray = mutableMapOf<Coord, Char>()
    for (coord in array.entries) {
        rotatedArray[Coord(sqrt(array.keys.size.toDouble()).toInt() - coord.key.y, coord.key.x)] =
            array[coord.key] ?: error("")
    }
    return rotatedArray
}

private fun flipVertically(array: Map<Coord, Char>): Map<Coord, Char> {
    val flippedArray = mutableMapOf<Coord, Char>()
    for (coord in array.entries) {
        flippedArray[Coord(9 - coord.key.x, coord.key.y)] = array[coord.key] ?: error("")
    }
    return flippedArray
}

private fun flipHorizontally(array: Map<Coord, Char>): Map<Coord, Char> {
    val flippedArray = mutableMapOf<Coord, Char>()
    for (coord in array.entries) {
        flippedArray[Coord(coord.key.x, 9 - coord.key.y)] = array[coord.key] ?: error("")
    }
    return flippedArray
}

fun readImageInput(path: String): Map<Coord, Char> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    val inputMap = mutableMapOf<Coord, Char>()
    var x = 0
    for ((y, line) in lineList.withIndex()) {
        line.forEach { char ->
            inputMap[Coord(x, y)] = char
            x++
        }
        x = 0
    }
    return inputMap
}

fun flipSeaMonster(coords: List<Coord>, horizontally: Boolean): List<Coord> {
    val flippedSeaMonster = mutableListOf<Coord>()
    for (c in coords) {
        if (horizontally) {
            flippedSeaMonster.add(Coord(19 - c.x, c.y))
        } else {
            flippedSeaMonster.add(Coord(c.x, 2 - c.y))
        }
    }
    return flippedSeaMonster
}

fun findSeaMonsters(
    map: Map<Coord, Char>,
    inputSize: Int,
    rotateTimes: Int = 0,
    flipHorizontally: Boolean = false
): Int {
    var rotatedMap = map
    var i = 0
    while (i++ < rotateTimes) {
        rotatedMap = rotate90DegClockwise(rotatedMap)
    }
    var monsterCount = 0
    var seaMonsterCoords = listOf(
        Coord(0, 1), Coord(1, 2), Coord(4, 2), Coord(5, 1),
        Coord(6, 1), Coord(7, 2), Coord(10, 2), Coord(11, 1),
        Coord(12, 1), Coord(13, 2), Coord(16, 2), Coord(17, 1),
        Coord(18, 1), Coord(19, 1), Coord(18, 0)
    )
    if (flipHorizontally) {
        seaMonsterCoords = flipSeaMonster(seaMonsterCoords, true)
    }
    val mapCopy = rotatedMap.toMutableMap()
    for (y in 0 until inputSize)
        for (x in 0..(inputSize - 2)) {
            var seaMonsterMatch = true
            for (c in seaMonsterCoords) {
                if (rotatedMap[Coord(x + c.x, y + c.y)] != '#') {
                    seaMonsterMatch = false
                    break
                }
            }
            if (seaMonsterMatch) {
                monsterCount++
                for (c in seaMonsterCoords) {
                    mapCopy[Coord(x + c.x, y + c.y)] = '@'
                }
            }
        }
    printInputPuzzle(mapCopy)
    return monsterCount
}

data class JigsawTile(var tileId: Int, var array: Map<Coord, Char>, var borderList: List<String>)
data class NeighborEdge(var tileId: Int, var edge: String)

data class Coord(var x: Int, var y: Int) {

    fun neighbors(allowNegative: Boolean = false): List<Coord> =
        listOf(
            Coord(x, y - 1),
            Coord(x - 1, y),
            Coord(x + 1, y),
            Coord(x, y + 1)
        ).filter { allowNegative || it.x >= 0 && it.y >= 0 }

    operator fun plus(other: Coord) = Coord(x + other.x, y + other.y)
    operator fun not() = Coord(-x, -y)
}