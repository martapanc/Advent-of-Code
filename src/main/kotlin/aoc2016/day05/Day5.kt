package aoc2016.day05

import java.math.BigInteger
import java.security.MessageDigest

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

fun md5(input: String): String {
    val md = MessageDigest.getInstance("md5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}