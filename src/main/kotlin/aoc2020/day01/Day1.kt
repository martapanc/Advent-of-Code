package aoc2020.day01

import java.io.File
import java.io.InputStream

fun readInputFileToMap(path: String): Map<Int, Int> {
    val inputMap = mutableMapOf<Int, Int>()
    val inputStream: InputStream = File(path).inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    lineList
        .asSequence()
        .map { it.toInt() }
        .forEach { inputMap[it] = 2020 - it }

    return inputMap;
}

fun findPairAndMultiply(inputMap: Map<Int, Int>) : Int {
    inputMap.forEach { input ->
        if (inputMap.containsKey(input.value)) {
            return input.key * input.value
        }
    }
    return 0
}

fun findTripletAndMultiply(inputMap: Map<Int, Int>) : Int {
    inputMap.forEach { input1 ->
        inputMap.forEach { input2 ->
            val candidateSecond = input2.key
            val candidateThird = input1.value - candidateSecond
            if (inputMap.containsKey(candidateThird)) {
                return input1.key * candidateSecond * candidateThird
            }
        }
    }
    return 0
}
