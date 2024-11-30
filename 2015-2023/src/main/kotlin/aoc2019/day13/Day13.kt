package aoc2019.day13

import aoc2019.day09.processParameterMode
import aoc2019.day13.Tile.Companion.valueOf
import java.awt.Point
import java.util.*

fun processInput(numbers: ArrayList<Long>): Map<Point, Tile?> {
    val tileMap: MutableMap<Point, Tile?> = HashMap()
    var i = 0
    var relativeBase = 0
    val outputBuilder = StringBuilder()
    while (i < numbers.size) {
        val opCode = Math.toIntExact(numbers[i])
        if (opCode == 99) {
            break
        }
        val output = processParameterMode(numbers, i, opCode, 0, relativeBase)
        if (output.code != "") {
            outputBuilder.append(output.code).append(",")
        }
        if (output.relativeBase != 0) {
            relativeBase = output.relativeBase
        }
        i += output.index
    }
    val outputs = outputBuilder.toString().split(",").toTypedArray()
    i = 0
    while (i < outputs.size-1) {
        val x = outputs[i].toInt()
        val y = outputs[i + 1].toInt()
        val tileId = outputs[i + 2].toInt()
        if (valueOf(tileId) != null)
            tileMap[Point(x, y)] = valueOf(tileId)
        i += 3
    }
    return tileMap
}

fun printTileMap(tileMap: Map<Point, Tile?>) {
    val minX = tileMap.keys.stream().mapToInt { p: Point -> p.x }.min().orElse(-1)
    val maxX = tileMap.keys.stream().mapToInt { p: Point -> p.x }.max().orElse(-1)
    val minY = tileMap.keys.stream().mapToInt { p: Point -> p.y }.min().orElse(-1)
    val maxY = tileMap.keys.stream().mapToInt { p: Point -> p.y }.max().orElse(-1)
    for (y in minY..maxY) {
        for (x in minX..maxX) {
            val key = Point(x, y)
            if (tileMap.containsKey(key)) {
                print(tileMap[key]!!.symbol)
            } else {
                print(" ")
            }
        }
        println()
    }
}

fun countTilesOfType(tileType: Tile, processInput: Map<Point, Tile>): Int {
    return processInput.values.stream().filter { tile: Tile -> tile == tileType }.count()
        .toInt()
}
