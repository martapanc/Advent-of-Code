package aoc2020.day04

import java.io.File
import java.io.InputStream

fun readInputFile(path: String): List<String> {
    val inputStream: InputStream = File(path).inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val passportDataList = mutableListOf<String>()
    var passportData = ""
    for (line in lineList) {
        if (line.isNotEmpty()) {
            passportData += if (passportData.isBlank()) { line } else { " $line" }
        } else {
            passportDataList.add(passportData)
            passportData = ""
        }
    }
    return passportDataList
}

fun arePassportKeysValid(passportData: String): Boolean {
    return listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid").all { passportData.contains(it) }
}

fun isYearValid(dataMap: Map<String, String>, key: String, min: Int, max: Int): Boolean {
    val year: Int? = dataMap[key]?.toIntOrNull()
    return !(year == null || year < min || year > max)
}

fun isHeightValid(height: String): Boolean {
    if (height.endsWith("in")) {
        val heightVal = height.replace("in", "").toInt()
        if (heightVal < 59 || heightVal > 76) {
            return false
        }
    } else if (height.endsWith("cm")) {
        val heightVal = height.replace("cm", "").toInt()
        if (heightVal < 150 || heightVal > 193) {
            return false
        }
    } else {
        return false
    }
    return true
}

fun isStringValid(dataMap: Map<String, String>, key: String, regex: Regex): Boolean {
    val stringVal: String? = dataMap[key]
    return stringVal != null && stringVal.matches(regex)
}

fun isPassportDataValid(passportData: String): Boolean {
    if (!arePassportKeysValid(passportData)) {
        return false
    }
    val dataMap = mutableMapOf<String, String>()
    passportData.split(" ")
        .asSequence()
        .map { it.split(":") }
        .forEach { dataMap[it[0]] = it[1] }

    //byr (Birth Year) - four digits; at least 1920 and at most 2002.
    if (!isYearValid(dataMap, "byr", 1920, 2002)) {
        return false
    }

    //iyr (Issue Year) - four digits; at least 2010 and at most 2020.
    if (!isYearValid(dataMap, "iyr", 2010, 2020)) {
        return false
    }

    //eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
    if (!isYearValid(dataMap, "eyr", 2020, 2030)) {
        return false
    }

    //hgt (Height) - a number followed by either cm or in:
    // If cm, the number must be at least 150 and at most 193.
    // If in, the number must be at least 59 and at most 76.
    val height: String = dataMap["hgt"] ?: return false
    if (!isHeightValid(height)) {
        return false
    }

    //hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
    if (!isStringValid(dataMap, "hcl", Regex("""#[0-9a-fA-F]{6}"""))) {
        return false
    }

    //ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
    if (!isStringValid(dataMap, "ecl", Regex("""amb|blu|brn|gry|grn|hzl|oth"""))) {
        return false
    }

    //pid (Passport ID) - a nine-digit number, including leading zeroes.
    if (!isStringValid(dataMap, "pid", Regex("""[0-9]{9}"""))) {
        return false
    }

    return true
}

fun countValidPassports(dataList: List<String>, validationMethod: (String) -> Boolean): Int {
    return dataList.count { validationMethod(it) }
}