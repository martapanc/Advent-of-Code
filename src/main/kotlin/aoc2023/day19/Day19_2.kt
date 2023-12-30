package aoc2023.day19

fun main() {
    val input = listOf<String>()
    val ii = input.indexOf("")
    data class Cond(val value: Char, val op: Char, val comparator: Int)
    data class Rule(val condition: Cond?, val destination: String)
    data class WF(val rules: List<Rule>)
    val workflows = HashMap<String, WF>()
    for (i in 0..<ii) {
        val s = input[i]
        val oi = s.indexOf("{")
        val name = s.substring(0, oi)
        val rules = s.substring(oi).removeSurrounding("{", "}").split(",").map { rs ->
            val ci = rs.indexOf(":")
            val cond = if (ci >= 0) {
                val v = rs[0]
                val op = rs[1]
                val w = rs.substring(2, ci).toInt()
                Cond(v, op, w)
            } else null
            val dest = rs.substring(ci + 1)
            Rule(cond, dest)
        }
        workflows[name] = WF(rules)
    }
    fun compute(current: String, rangeMap0: Map<Char, IntRange>): Long {
        if (current == "R") return 0
        var result = 0L
        if (current == "A") {
            result = 1
            for (range in rangeMap0.values) result *= range.last - range.first + 1
            return result
        }
        val workflow = workflows[current]!!
        val rangeMap = rangeMap0.toMutableMap()
        for (rule in workflow.rules) {
            val condition = rule.condition
            if (condition == null) {
                result += compute(rule.destination, rangeMap)
                break
            } else {
                val currentRange = rangeMap[condition.value]!!
                val (rt, rf) = when (condition.op) {
                    '<' -> currentRange.first..minOf(currentRange.last, condition.comparator - 1) to maxOf(currentRange.first, condition.comparator)..currentRange.last
                    '>' -> maxOf(currentRange.first, condition.comparator + 1)..currentRange.last to currentRange.first..minOf(currentRange.last, condition.comparator)
                    else -> error("!")
                }
                if (!rt.isEmpty()) {
                    val rm1 = rangeMap.toMutableMap()
                    rm1[condition.value] = rt
                    result += compute(rule.destination, rm1)
                }
                if (rf.isEmpty()) break
                rangeMap[condition.value] = rf
            }
        }
        return result
    }
    val rm = HashMap<Char, IntRange>()
    for (v in "xmas") rm[v] = 1..4000
    println(compute("in", rm))
}