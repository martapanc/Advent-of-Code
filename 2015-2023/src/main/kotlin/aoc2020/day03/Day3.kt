package aoc2020.day03

import java.io.File
import java.io.InputStream

// 11x11 area, (3r, 1d) = 33x = repeat 3 times
// 31x323 area, (3r, 1d) = 11x

fun readInputFileToMap(path: String): Map<Pair<Int, Int>, Boolean> {
    val inputMap = mutableMapOf<Pair<Int, Int>, Boolean>()
    val inputStream: InputStream = File(path).inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    var x = 0
    for ((y, line) in lineList.withIndex()) {
        line.forEach { char ->
            inputMap[Pair(x, y)] = char == '#'
            x++
        }
        x = 0
    }

    return inputMap;
}

fun countTreesInMap(inputMap: Map<Pair<Int, Int>, Boolean>, maxX: Int): Int {
    var count = 0
    var x = 0
    var y = 0

    while (x < maxX) {
        if (inputMap.containsKey(Pair(x, y))) {
            if (inputMap[Pair(x, y)] == true) {
                count++
            }
        }
        x += 3
        y++
    }
    return count
}

//fun main() {
////    println(countTreesInMap(readInputFileToMap("src/main/kotlin/aoc2020/day3/input0"), 33, 11))
//    val inputMap = readInputFileToMap("src/main/kotlin/aoc2020/day3/input")
////    println(inputMap)
//    println(countTreesInMap(inputMap, 450, 324))
//}