package aoc2020.day20

import util.Coord
import kotlin.math.sqrt

fun drawFirstLine(tilesList: List<JigsawTile>) {
    val tilesAndNeighbors: Map<Int, List<NeighborEdge>> = findTileNeighbors(tilesList)
    val tileIdToTile = mutableMapOf<Int, JigsawTile>()
    for (tile in tilesList) tileIdToTile[tile.tileId] = tile

    val topLeftId = 2273
    val rightNeighbor: NeighborEdge = (tilesAndNeighbors[topLeftId] ?: error(""))[1]
    for (tile in tilesAndNeighbors) {
        if (tile.value.contains(rightNeighbor) ||
            tile.value.contains(NeighborEdge(rightNeighbor.tileId, rightNeighbor.edge.reversed()))
        ) {
            print(tile.key)
        }
    }
}

fun createCompletePuzzle(tilesList: List<JigsawTile>) {
    val tileIdToTile = mutableMapOf<Int, JigsawTile>()
    for (tile in tilesList) tileIdToTile[tile.tileId] = tile
//    printTile(rotateOrFlip(tileIdToTile[1549]!!, flip = 'v'))
    printTile(rotateOrFlip(tileIdToTile[2243]!!, rotate = true, deg = 90, flip = 'z'))
}

fun printTile(tile: JigsawTile) {
    println("Tile " + tile.tileId + ":")
    for (y in 0..9) {
        for (x in 0..9) {
            print(tile.array[Coord(x, y)])
        }
        println()
    }
    println()
}

//                  #
//#    ##    ##    ###
// #  #  #  #  #  #
// #  #  #  #  #  #
//#    ##    ##    ###
//                  #
// #
//###    ##    ##    #
//   #  #  #  #  #  #
//   #  #  #  #  #  #
//###    ##    ##    #
// #

fun printSeaMonster(list: List<Coord>) {
    for (y in 0..6) {
        for (x in 0..19) {
            if (list.contains(Coord(x, y))) {
                print("#")
            } else {
                print(" ")
            }
        }
        println()
    }
}

fun printInputPuzzle(map: Map<Coord, Char>) {
    val side = sqrt(map.size.toDouble()).toInt()
    for (y in 0 until side) {
        for (x in 0 until side) print(map[Coord(x, y)])
        println()
    }
    println("")
}
