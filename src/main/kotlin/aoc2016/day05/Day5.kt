package aoc2016.day05

import java.math.BigInteger
import java.security.MessageDigest
import javax.xml.stream.events.Characters

fun findPassword(input: String): String {
    var outputPassword = ""
    var i = 1
    while (outputPassword.length < 8) {
        val hash = md5(input + i)
        if (hash.substring(0, 5) == "00000") outputPassword += hash[5]
        i++
    }
    return outputPassword
}

fun findPasswordPart2(input: String): String {
    val outputPassword = mutableListOf('-', '-', '-', '-', '-', '-', '-', '-')
    var charCount = 0
    var i = 1
    while (charCount < 8) {
        val hash = md5(input + i)
        if (hash.substring(0, 5) == "00000") {
            val position = hash[5]
            val value = hash[6]
            if (listOf('0', '1', '2', '3', '4', '5', '6', '7').contains(position)) {
                val index = Character.getNumericValue(position)
                if (outputPassword[index] == '-') {
                    outputPassword[index] = value
                    charCount++
                }
            }
        }
        i++
    }
    return outputPassword.joinToString("")
}

fun md5(input: String): String {
    val md = MessageDigest.getInstance("md5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}