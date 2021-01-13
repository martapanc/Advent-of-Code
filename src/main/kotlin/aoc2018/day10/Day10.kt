package aoc2018.day10

import java.awt.Point
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*
import java.util.function.Consumer
import java.util.function.Function
import java.util.stream.Collectors
import kotlin.math.abs

fun readInput(inputFile: String): List<MovingPoint> {
    val pointList: MutableList<MovingPoint> = ArrayList<MovingPoint>()
    val reader: BufferedReader
    try {
        reader = BufferedReader(FileReader(inputFile))
        var line = reader.readLine()
        while (line != null) {
            val input = line.trim { it <= ' '}.split(Regex("\\s+")).toTypedArray().map { s: String -> s.toInt() }.toIntArray()
            pointList.add(MovingPoint(input[0], input[1], input[2], input[3]))
            line = reader.readLine()
        }
        reader.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return pointList
}

private fun getListOfCurrentPoints(movingPoints: List<MovingPoint>, seconds: Int): List<Point?> {
    return movingPoints.stream().map(
        Function<MovingPoint, Point?> { p: MovingPoint ->
            Point(
                p.positionX + p.speedX * seconds,
                p.positionY + p.speedY * seconds
            )
        }
    ).collect(Collectors.toList())
}

fun computeCloseCoordinates(movingPoints: List<MovingPoint>, time: Int) {
    var timeVar = time
    var points = getListOfCurrentPoints(movingPoints, 0)
    while (abs(points[0]!!.x - points[1]!!.x) > 80) {
        points = getListOfCurrentPoints(movingPoints, timeVar)
        timeVar += 1
    }
    println(timeVar)
    println(points)
}

fun compute(movingPoints: List<MovingPoint>, seconds: Int) {
    val points = getListOfCurrentPoints(movingPoints, seconds)
    val minX = points.minByOrNull { it!!.x }!!.x
    val minY = points.minByOrNull { it!!.y }!!.y
    points.forEach(Consumer { p: Point? -> p!!.setLocation(p.getX() - minX, p.getY() - minY) })
    val maxX = points.maxByOrNull { it!!.x }!!.x
    val maxY = points.maxByOrNull { it!!.y }!!.y
    val matrix = Array(maxY) { arrayOfNulls<String>(maxX) }
    for (y in 0 until maxY) {
        for (x in 0 until maxX) {
            matrix[y][x] = "."
        }
    }
//    points.forEach(Consumer { p: Point? -> matrix[p!!.y][p.x] = "#" })
//    for (y in 0 until maxY) {
//        for (x in 0 until maxX) {
//            print(matrix[y][x].toString() + " ")
//        }
//        println()
//    }
//    println()
}


class MovingPoint(val positionX: Int, val positionY: Int, val speedX: Int, val speedY: Int) {
    override fun toString(): String {
        return "{" + positionX +
                ", " + positionY +
                ", " + speedX +
                ", " + speedY +
                '}'
    }
}