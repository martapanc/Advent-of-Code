package aoc2019.day17

import aoc2019.day09.processParameterMode
import java.awt.Point
import java.util.*

fun processInput(numbers: ArrayList<Long>): Map<Point, String> {
    var number = 0
    var relativeBase = 0
    val map: MutableMap<Point, String> = HashMap()
    var x = 0
    var y = 0
    while (number < numbers.size) {
        val opCode = Math.toIntExact(numbers[number])
        if (opCode == 99) {
            break
        }
        val output = processParameterMode(numbers, number, opCode, 0, relativeBase)
        if (output.code!!.isNotEmpty())
            when (output.code) {
                "35" -> map[Point(x++, y)] = "#"
                "46" -> map[Point(x++, y)] = "."
                "10" -> {
                    x = 0
                    y++
                }
            }
        if (output.relativeBase != 0) {
            relativeBase = output.relativeBase
        }
        number += output.index
    }
    return map
}

fun printMap(map: Map<Point, String>) {
    val maxX = map.keys.stream().mapToInt { p: Point -> p.x }.max().orElse(-1)
    val maxY = map.keys.stream().mapToInt { p: Point -> p.y }.max().orElse(-1)
    for (y in 0 until maxY) {
        for (x in 0 until maxX) {
            val p = map[Point(x, y)]
            print(p ?: " ")
        }
        println()
    }
}

fun getIntersections(map: Map<Point, String>): List<Point> {
    return map.entries
        .filter { it.value == "#" }
        .map { it.key }
        .filter { current: Point ->
            "#" == map[Point(current.x, current.y - 1)]
                    && "#" == map[Point(current.x, current.y + 1)]
                    && "#" == map[Point(current.x - 1, current.y)]
                    && "#" == map[Point(current.x + 1, current.y)]
        }
}

fun multiplyCoordinatesAndSum(list: List<Point>): Int {
    return list.map { p: Point -> p.x * p.y }.sum()
}
