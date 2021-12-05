package aoc2021.day05

import java.awt.Point

fun getListOfPointPairs(inputList: List<String>): List<Pair<Point, Point>> {
    val pointPairs = mutableListOf<Pair<Point, Point>>()

    inputList.forEach { line ->
        val points = line.split(" -> ")
        val coords1 = points[0].split(",").map { it.toInt() }
        val coords2 = points[1].split(",").map { it.toInt() }
        pointPairs.add(Pair(
            Point(coords1[0], coords1[1]),
            Point(coords2[0], coords2[1]))
        )
    }
    return pointPairs
}

fun getOverlappingPoints(pointPairs: List<Pair<Point, Point>>, isPart2: Boolean = false): Int {
    val ventilationMap = mutableMapOf<Point, Int>()

    for (pair: Pair<Point, Point> in pointPairs) {
        if (pair.first.x == pair.second.x) {
            val x = pair.first.x
            val a: Point = if (pair.first.y < pair.second.y) pair.first else pair.second
            val b: Point = if (pair.first.y < pair.second.y) pair.second else pair.first

            for (y: Int in a.y..b.y) {
                val key = Point(x, y)
                if (ventilationMap.containsKey(key)) {
                    ventilationMap[key] = ventilationMap[key]!!.plus(1)
                } else {
                    ventilationMap[key] = 1
                }
            }

        } else if (pair.first.y == pair.second.y) {
            val y = pair.first.y
            val a: Point = if (pair.first.x < pair.second.x) pair.first else pair.second
            val b: Point = if (pair.first.x < pair.second.x) pair.second else pair.first

            for (x: Int in a.x..b.x) {
                val key = Point(x, y)
                if (ventilationMap.containsKey(key)) {
                    ventilationMap[key] = ventilationMap[key]!!.plus(1)
                } else {
                    ventilationMap[key] = 1
                }
            }
        } else if (isPart2) {
            val a: Point = pair.first
            val b: Point = pair.second

            if (b.x > a.x && b.y > a.y) {
                // lines like / (1st quadrant)
                for (d in 0..(b.x - a.x)) {
                    val key = Point(a.x + d, a.y + d)
                    if (ventilationMap.containsKey(key)) {
                        ventilationMap[key] = ventilationMap[key]!!.plus(1)
                    } else {
                        ventilationMap[key] = 1
                    }
                }
            }

            else if (b.x < a.x && b.y < a.y) {
                // lines like / (3rd quadrant)
                for (d in 0..(a.x - b.x)) {
                    val key = Point(a.x - d, a.y - d)
                    if (ventilationMap.containsKey(key)) {
                        ventilationMap[key] = ventilationMap[key]!!.plus(1)
                    } else {
                        ventilationMap[key] = 1
                    }
                }

            } else if (b.x < a.x && b.y > a.y) {
                // lines like \ (4th quadrant)
                for (d in 0..(b.y - a.y)) {
                    val key = Point(a.x - d, a.y + d)
                    if (ventilationMap.containsKey(key)) {
                        ventilationMap[key] = ventilationMap[key]!!.plus(1)
                    } else {
                        ventilationMap[key] = 1
                    }
                }

            } else if (b.x > a.x && b.y < a.y) {
                // lines like \ (2nd quadrant)
                for (d in 0..(b.x - a.x)) {
                    val key = Point(a.x + d, a.y - d)
                    if (ventilationMap.containsKey(key)) {
                        ventilationMap[key] = ventilationMap[key]!!.plus(1)
                    } else {
                        ventilationMap[key] = 1
                    }
                }
            }
        }
    }

    return ventilationMap.filter { it.value >= 2 }.count()
}