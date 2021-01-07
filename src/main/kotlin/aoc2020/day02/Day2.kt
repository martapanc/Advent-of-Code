package aoc2020.day02

import java.io.File
import java.io.InputStream

fun readInputFileToList(path: String): List<PasswordInfo> {
    val inputList = mutableListOf<PasswordInfo>()
    val inputStream: InputStream = File(path).inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    lineList
        .asSequence()
        .map { it.split("-", " ", ": ") }
        .mapTo(inputList) { PasswordInfo(it[0].toInt(), it[1].toInt(), it[2].single(), it[3]) }

    return inputList;
}

class PasswordInfo(var min: Int, var max: Int, var char: Char, var password: String)

fun isValidPassword(min: Int, max: Int, char: Char, password: String) : Boolean {
    val count = password.count {
        i -> i == char
    }
    return count in min..max
}

fun isValidPassword(passwordInfo: PasswordInfo) : Boolean {
    return isValidPassword(passwordInfo.min, passwordInfo.max, passwordInfo.char, passwordInfo.password)
}

fun countValidPasswords(passwordList: List<PasswordInfo>, validationMethod: (PasswordInfo) -> Boolean): Int {
    var count = 0
    passwordList
        .filter { validationMethod(it) }
        .forEach { _ -> count += 1 }
    return count;
}

// Part 2
fun isValidPassword2(passwordInfo: PasswordInfo) : Boolean {
    return isValidPassword2(passwordInfo.min, passwordInfo.max, passwordInfo.char, passwordInfo.password)
}

fun isValidPassword2(min: Int, max: Int, char: Char, password: String) : Boolean {
    return (password[min - 1] == char).xor(password[max - 1] == char)
}
