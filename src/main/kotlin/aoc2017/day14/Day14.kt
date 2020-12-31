package aoc2017.day14

import aoc2017.day10.convertListToASCII
import aoc2017.day10.processListPart2
import java.math.BigInteger

fun defragmentDisk(input: String): Int {
    return generateHashList(input).sumBy { it -> it.count { it == '1' } }
}

fun generateHashList(input: String): List<String> {
    return (0 until 128).map {
        val hash = processListPart2(convertListToASCII("$input-$it"))
        BigInteger(hash, 16).toString(2).padStart(128, '0')
    }
}