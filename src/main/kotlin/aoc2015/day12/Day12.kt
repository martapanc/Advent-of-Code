package aoc2015.day12

import org.json.JSONArray
import org.json.JSONObject
import java.io.File

fun readJsonAndComputeSum(path: String): Int {
    val jsonString: String = File(path).readText(Charsets.UTF_8)
    val answer = JSONObject(jsonString)
    return recursiveJson(answer)
}

fun recursiveJson(jsonObject: JSONObject) : Int {
    var accumulator = 0
    loop@ for (key in jsonObject.keySet()) {
        when (val value = jsonObject[key]) {
            is Int ->  accumulator += value
            is String -> continue@loop
            is JSONObject -> accumulator += recursiveJson(value)
            is JSONArray  -> accumulator += recursiveJson(value)
        }
    }
    return accumulator
}

fun recursiveJson(jsonArray: JSONArray): Int {
    var accumulator = 0
    loop@ for (item in jsonArray) {
        when (item) {
            is Int -> accumulator += item
            is String -> continue@loop
            is JSONArray -> accumulator += recursiveJson(item)
            is JSONObject -> accumulator += recursiveJson(item)
        }
    }
    return accumulator
}