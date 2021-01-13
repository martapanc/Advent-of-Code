package aoc2018.day05

import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


fun computeAlchemicalReduction(inputString: String): String {
    var input = inputString
    var output = ""
    var k = 0
    while (k < 100000) {
        for (i in 0 until input.length - 1) {
            val thisAscii = input[i].toInt()
            val nextAscii = input[i + 1].toInt()
            if (thisAscii + 32 == nextAscii || thisAscii - 32 == nextAscii) {
                output = charRemoveAt(input, i)
                output = charRemoveAt(output, i)
                input = output
                break
            }
        }
        k++
    }
    return if (output.isNotEmpty()) output else input
}

fun computeAlchemicalReductionLength(input: String): Int {
    val output = computeAlchemicalReduction(input)
    return output.length
}

fun charRemoveAt(str: String, p: Int): String {
    return str.substring(0, p) + str.substring(p + 1)
}

fun readInputFile(filepath: String): String {
    val reader: BufferedReader
    var line = ""
    try {
        reader = BufferedReader(FileReader(filepath))
        line = reader.readLine()
        reader.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return line
}

fun removeAllLetter(input: String, letter: String): String {
    return input.replace(letter.toLowerCase(), "").replace(letter.toUpperCase(), "")
}
