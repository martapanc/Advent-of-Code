package aoc2020.day19

import java.io.File

// The required depth is 13 for the large input - setting it to 20 as it doesn't affect the performance significantly
var depthLimit = 20

fun readRulesToMap(path: String): Map<String, String> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    val inputMap = mutableMapOf<String, String>()
    lineList
        .map { it.split(": ") }
        .forEach { inputMap[it[0]] = it[1] }
    return inputMap
}

fun readMessagesToList(path: String): List<String> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    return lineList
}

fun findRegex(ruleMap: Map<String, String>): String {
    return recursivelyReplace("0", ruleMap, 0, false)
}

fun recursivelyReplace(rule: String, map: Map<String, String>, depth: Int, limitDepth: Boolean): String {
    val ruleValue = map[rule]
    if (ruleValue == "a" || ruleValue == "b") {
        return ruleValue
    }
    if (limitDepth && depth > depthLimit) {
        return ""
    }
    var regex = if (ruleValue!!.contains("|")) "(" else ""
    for ((index, split) in ruleValue.split("|").withIndex()) {
        val rules = split.trim().split(" ")
        for (r in rules) {
            val recursivelyReplace = recursivelyReplace(r, map, depth + 1, limitDepth)
            regex = "$regex$recursivelyReplace"
        }
        if (ruleValue.contains("|") && index == 0) {
            regex += "|"
        }
    }
    if (ruleValue.contains("|")) regex += ")"
    return regex
}

fun countValidMessages(messages: List<String>, regexString: String): Int {
    return messages.count { it.matches(Regex(regexString)) }
}

// To avoid infinite loops, limit the depth an infinite rule can reach
fun countValidMessagesV2(messages: List<String>, ruleMap: Map<String, String>): Int {
    val regex = recursivelyReplace("0", ruleMap, 0, true)
    return messages.count { it.matches(Regex(regex)) }
}

// All rules but 0, 8, 11 are finite
fun testsWithNewRules(rules: Map<String, String>) {
    val listOfRulesThatFinish = mutableListOf<String>()
    val sortedRules = rules.toSortedMap(compareBy<String> { it.length }.thenBy { it })
    for (rule in sortedRules.keys) {
        print("Rule $rule started at " + System.currentTimeMillis())
        try {
            println(" : " + recursivelyReplace(rule, rules, 0, false))
            listOfRulesThatFinish.add(rule)
        } catch (error: StackOverflowError) {
            println(" : stack overflow")
            continue
        }
    }
    println(listOfRulesThatFinish)
}
