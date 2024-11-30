package aoc2018.day08

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*
import java.util.stream.IntStream
import java.util.stream.Stream

fun sumMetadata(inputFile: String): Long {
    val input = readInput(inputFile)
    return if (input != null) {
        val res = parse(input)
        res.total.toLong()
    } else {
        -1
    }
}

fun getRootScore(inputFile: String): Long {
    val input = readInput(inputFile)
    return if (input != null) {
        val res = parse(input)
        res.score.toLong()
    } else {
        -1
    }
}

private fun parse(input: IntArray): Result {
    val children = input[0]
    val metas = input[1]
    var input2 = Arrays.copyOfRange(input, 2, input.size)
    val scores: MutableList<Int> = ArrayList()
    var totals = 0
    for (i in 0 until children) {
        val res = parse(input2)
        totals += res.total
        scores.add(res.score)
        input2 = res.data
    }
    val currMetas = Arrays.copyOfRange(input2, 0, metas)
    val restOfInput = Arrays.copyOfRange(input2, metas, input2.size)
    totals += currMetas.sum()
    return if (children == 0) {
        Result(totals, currMetas.sum(), restOfInput)
    } else {
        var sc = 0
        for (j in currMetas) {
            if (j > 0 && j <= scores.size) {
                sc += scores[j - 1]
            }
        }
        Result(totals, sc, restOfInput)
    }
}

private fun readInput(inputFile: String): IntArray? {
    val reader: BufferedReader
    return try {
        reader = BufferedReader(FileReader(inputFile))
        val line = reader.readLine()
        val input = line.split(" ").toTypedArray().map { it.toInt() }.toIntArray()
        reader.close()
        input
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

class Result(val total: Int, val score: Int, val data: IntArray)