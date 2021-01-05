package aoc2015.day12

import org.json.JSONArray
import org.json.JSONObject
import java.io.File

fun readJsonAndComputeSum(path: String, ignoreRed: Boolean = false): Int {
    val jsonString: String = File(path).readText(Charsets.UTF_8)
    val answer = JSONObject(jsonString)
    return recursiveJson(answer, ignoreRed)
}

fun recursiveJson(jsonObject: JSONObject, ignoreRed: Boolean = false): Int {
    var accumulator = 0
    val stringValues = jsonObject.keySet()
        .filter { jsonObject[it] is String }
        .map { jsonObject[it] as String }
    if (ignoreRed && stringValues.any { it == "red" }) {
        return 0
    }
    loop@ for (key in jsonObject.keySet()) {
        when (val value = jsonObject[key]) {
            is Int -> accumulator += value
            is String -> continue@loop
            is JSONObject -> accumulator += recursiveJson(value, ignoreRed)
            is JSONArray -> accumulator += recursiveJson(value, ignoreRed)
        }
    }
    return accumulator
}

fun recursiveJson(jsonArray: JSONArray, ignoreRed: Boolean = false): Int {
    var accumulator = 0
    loop@ for (item in jsonArray) {
        when (item) {
            is Int -> accumulator += item
            is String -> continue@loop
            is JSONObject -> accumulator += recursiveJson(item, ignoreRed)
            is JSONArray -> accumulator += recursiveJson(item, ignoreRed)
        }
    }
    return accumulator
}