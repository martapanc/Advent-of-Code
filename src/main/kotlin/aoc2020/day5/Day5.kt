package aoc2020.day5

import java.io.File
import kotlin.math.pow

fun readInputToList(path: String): List<String> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    return lineList.toList();
}

fun fromStringToRowAndColumn(input: String): Array<Int> {
    val binary = input
        .replace("F", "0")
        .replace("B", "1")
        .replace("L", "0")
        .replace("R", "1")
    val row = binaryToDecimal(binary.substring(0, 7).toInt())
    val column = binaryToDecimal(binary.substring(7, 10).toInt())
    return arrayOf(row, column, row * 8 + column)
}

fun binaryToDecimal(input: Int): Int {
    var num = input
    var decimalNumber = 0
    var i = 0
    var remainder: Int
    while (num != 0) {
        remainder = num % 10
        num /= 10
        decimalNumber += (remainder * 2.0.pow(i.toDouble())).toInt()
        ++i
    }
    return decimalNumber
}

fun findHighestSeatId(inputList: List<String>): Int? {
    val idList = inputList
        .map { fromStringToRowAndColumn(it) }
        .map { it[2] }
    return idList.maxOrNull()
}

fun mapSeats(inputList: List<String>) {
    val seatMap = mutableMapOf<Pair<Int, Int>, String>()
    for (y in 0..127) {
        for (x in 0..7) {
            seatMap[Pair(x, y)] = "\uD83D\uDCBA"
        }
    }
    for (seat in inputList.map { fromStringToRowAndColumn(it) }) {
        seatMap[Pair(seat[1], seat[0])] = "⛄️️"
    }
    seatMap[Pair(7, 83)] = "\uD83D\uDC22️" // My seat

    printSeatMap(seatMap)
}

private fun printSeatMap(seatMap: MutableMap<Pair<Int, Int>, String>) {
    print("     ")
    for (x in 0..7) {
        print("$x ")
    }
    println()
    for (y in 0..127) {
        if (y < 10) {
            print(" ")
        }
        if (y < 100) {
            print(" ")
        }
        print("$y ")
        for (x in 0..7) {
            print(seatMap[Pair(x, y)])
        }
        println()
    }
}