package aoc2022.day25

import kotlin.math.pow

fun part1(snafuList: List<String>): String {
    val decimal = convertListAndSum(snafuList)
    return decimalToSnafu(decimal)
}

fun convertListAndSum(snafuList: List<String>): Long {
    return snafuList.sumOf { snafuToDecimal(it) }
}

fun snafuToDecimal(snafu: String): Long {
    var decimal = 0L
    val chars = snafu.toCharArray()
    (chars.indices).forEach { index ->
        val exp = chars.size - 1 - index
        val posValue = 5.toDouble().pow(exp).toLong()
        val char = chars[index]
        val multiplier: Long = if (char == '-') -1 else if (char == '=') -2 else char.digitToInt().toLong()
        decimal += posValue * multiplier
    }
    return decimal
}

fun decimalToSnafu(decimal: Long): String {
    var base5 = ""
    var num = decimal
    var rem: Long
    while (num > 0) {
        rem = num % 5
        num /= 5
        if (rem > 2) {
            num += 1
            base5 += if (rem == 3L) "=" else "-"
        } else {
            base5 += rem
        }
    }
    return base5.reversed()
}

fun decimalToBase5(decimal: Long): String {
    var base5 = ""
    var num = decimal
    var rem: Long
    while (num > 0) {
        rem = num % 5
        num /= 5
        base5 += rem
    }
    return base5.reversed()
}
