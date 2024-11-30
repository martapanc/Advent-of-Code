package aoc2020.day24

import util.Coord
import java.io.File

fun readInputToList(path: String): List<List<HexNeighbors>> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    val inputList = mutableListOf<List<HexNeighbors>>()
    for (line in lineList) {
        val hexNeighbors = mutableListOf<HexNeighbors>()
        var i = 0
        while (i < line.length) {
            if (line[i] == 'e' || line[i] == 'w') {
                hexNeighbors.add(HexNeighbors.valueOf(line[i].toString().toUpperCase()))
                i++
            } else {
                hexNeighbors.add(HexNeighbors.valueOf(line.substring(i, i + 2).toUpperCase()))
                i += 2
            }
        }
        inputList.add(hexNeighbors)
    }
    return inputList
}

fun findTilesAndCountBlackOnes(input: List<List<HexNeighbors>>): Int {
    return getTiles(input).values.count { it == TileColor.BLACK }
}

private fun getTiles(input: List<List<HexNeighbors>>): Map<Coord, TileColor> {
    val tilesMap = mutableMapOf<Coord, TileColor>()
    tilesMap[Coord(0, 0)] = TileColor.WHITE

    val deltaMap = getDeltaMap()
    for (instructionList in input) {
        var currentTile = Coord(0, 0)
        for (instruction in instructionList) {
            val delta = deltaMap[instruction]
            val nextTileCoord = Coord(currentTile.x + delta!!.x, currentTile.y + delta.y)
            if (!tilesMap.containsKey(nextTileCoord)) {
                tilesMap[nextTileCoord] = TileColor.WHITE
            }
            currentTile = nextTileCoord
        }

        if (tilesMap.containsKey(currentTile)) {
            tilesMap[currentTile] = if (tilesMap[currentTile] == TileColor.WHITE) TileColor.BLACK else TileColor.WHITE
        } else {
            tilesMap[currentTile] = TileColor.BLACK
        }
    }
    return tilesMap
}

fun playGameOfTiles(input: List<List<HexNeighbors>>, days: Int): Int {
    var tilesMap = extendTileMap(getTiles(input))
    for (day in 1..days) {
        val mapCopy = tilesMap.toMutableMap()
        for (tile in tilesMap.entries) {
            val blackNeighbors = getNeighborTiles(tile.key, tilesMap).count { it == TileColor.BLACK }
            when (tile.value) {
                TileColor.BLACK -> {
                    if (blackNeighbors == 0 || blackNeighbors > 2) {
                        mapCopy[tile.key] = TileColor.WHITE
                    } else mapCopy[tile.key] = TileColor.BLACK
                }
                TileColor.WHITE -> {
                    if (blackNeighbors == 2) {
                        mapCopy[tile.key] = TileColor.BLACK
                    } else mapCopy[tile.key] = TileColor.WHITE
                }
            }
        }
        tilesMap = extendTileMap(mapCopy)
    }
    return tilesMap.values.count { it == TileColor.BLACK }
}

private fun extendTileMap(tilesMap: Map<Coord, TileColor>): MutableMap<Coord, TileColor> {
    val extendedMap = tilesMap.toMutableMap()
    val minX = tilesMap.keys.mapTo(mutableSetOf()) { it.x }.minOrNull()!!
    val maxX = tilesMap.keys.mapTo(mutableSetOf()) { it.x }.maxOrNull()!!
    val minY = tilesMap.keys.mapTo(mutableSetOf()) { it.y }.minOrNull()!!
    val maxY = tilesMap.keys.mapTo(mutableSetOf()) { it.y }.maxOrNull()!!
    for (y in minY - 1..maxY + 1)
        for (x in minX - 1..maxX + 1)
            if (tilesMap[Coord(x, y)] == null) {
                extendedMap[Coord(x, y)] = TileColor.WHITE
            }
    return extendedMap
}

private fun getDeltaMap(): MutableMap<HexNeighbors, Coord> {
    val deltaMap = mutableMapOf<HexNeighbors, Coord>()
    deltaMap[HexNeighbors.NW] = Coord(0, 1)
    deltaMap[HexNeighbors.NE] = Coord(1, 1)
    deltaMap[HexNeighbors.E] = Coord(1, 0)
    deltaMap[HexNeighbors.SE] = Coord(0, -1)
    deltaMap[HexNeighbors.SW] = Coord(-1, -1)
    deltaMap[HexNeighbors.W] = Coord(-1, 0)
    return deltaMap
}

private fun getNeighborTiles(tile: Coord, map: Map<Coord, TileColor>): List<TileColor> {
    val deltaCoords = getDeltaMap().values
    val neighbors = mutableListOf<TileColor>()
    for (delta in deltaCoords) {
        val neighborCoord = Coord(tile.x + delta.x, tile.y + delta.y)
        var neighborTile = map[neighborCoord]
        if (neighborTile == null) {
            neighborTile = TileColor.WHITE
        }
        neighbors.add(neighborTile)
    }
    return neighbors
}

enum class HexNeighbors { E, NE, SE, W, NW, SW }

enum class TileColor { WHITE, BLACK }
