package aoc2021.day05

import java.awt.Point

fun getListOfPointPairs(inputList: List<String>): List<Pair<Point, Point>> {
    val pointPairs = mutableListOf<Pair<Point, Point>>()
    inputList.forEach { line ->
        val points = line.split(" -> ")
        val coords1 = points[0].split(",").map { it.toInt() }
        val coords2 = points[1].split(",").map { it.toInt() }
        pointPairs.add(
            Pair(
                Point(coords1[0], coords1[1]),
                Point(coords2[0], coords2[1])
            )
        )
    }
    return pointPairs
}

fun getOverlappingPoints(pointPairs: List<Pair<Point, Point>>, isPart2: Boolean = false): Int {
    val ventilationMap = mutableMapOf<Point, Int>()

    for (pair: Pair<Point, Point> in pointPairs) {
        var a: Point = pair.first
        var b: Point = pair.second

        if (pair.first.x == pair.second.x) {
            val x = pair.first.x
            if (b.y < a.y) {
                a = b.also { b = a }
            }
            val a: Point = if (pair.first.y < pair.second.y) pair.first else pair.second
            val b: Point = if (pair.first.y < pair.second.y) pair.second else pair.first

            for (y: Int in a.y..b.y) {
                updateIntersectionCount(ventilationMap, Point(x, y))
            }
        } else if (pair.first.y == pair.second.y) {
            val y = pair.first.y
            val a: Point = if (pair.first.x < pair.second.x) pair.first else pair.second
            val b: Point = if (pair.first.x < pair.second.x) pair.second else pair.first

            for (x: Int in a.x..b.x) {
                updateIntersectionCount(ventilationMap, Point(x, y))
            }
        } else if (isPart2) {
            when {
                // lines like / (1st quadrant)
                (b.x > a.x && b.y > a.y) || (b.x < a.x && b.y < a.y) -> {
                    if (b.x < a.x) {
                        a = b.also { b = a }
                    }
                    for (delta in 0..(b.x - a.x)) {
                        val key = Point(a.x + delta, a.y + delta)
                        updateIntersectionCount(ventilationMap, key)
                    }
                }
                // lines like \ (2nd quadrant)
                (b.x > a.x && b.y < a.y) || (b.x < a.x && b.y > a.y) -> {
                    if (b.x < a.x) {
                        a = b.also { b = a }
                    }
                    for (delta in 0..(b.x - a.x)) {
                        val key = Point(a.x + delta, a.y - delta)
                        updateIntersectionCount(ventilationMap, key)
                    }
                }
//                // lines like / (3rd quadrant)
//                b.x < a.x && b.y < a.y ->
//                    for (delta in 0..(a.x - b.x)) {
//                        val key = Point(a.x - delta, a.y - delta)
//                        updateIntersectionCount(ventilationMap, key)
//                    }
                // lines like \ (4th quadrant)
//                b.x < a.x && b.y > a.y ->
//                    for (delta in 0..(b.y - a.y)) {
//                        val key = Point(a.x - delta, a.y + delta)
//                        updateIntersectionCount(ventilationMap, key)
//                    }
            }
        }
    }

    return ventilationMap.filter { it.value >= 2 }.count()
}

private fun updateIntersectionCount(ventilationMap: MutableMap<Point, Int>, key: Point) {
    if (ventilationMap.containsKey(key)) {
        ventilationMap[key] = ventilationMap[key]!!.plus(1)
    } else {
        ventilationMap[key] = 1
    }
}