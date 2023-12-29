package aoc2023.day19

fun parse(lines: List<String>): Day19Input {
    val workflows = mutableMapOf<String, List<Instruction>>()
    val ratings = mutableListOf<Rating>()
    var ratingsReached = false
    lines.forEach { line ->
        if (line.isEmpty()) {
            ratingsReached = true
        } else if (ratingsReached) {
            ratings.add(Rating(line))
        } else {
            val workflow = Workflow(line)
            workflows[workflow.name] = workflow.instructions
        }
    }
    return Day19Input(workflows, ratings)
}

fun part1(input: Day19Input): Long {
    var total = 0L
    for (rating in input.ratings) {
        var currentWorkflow = input.workflows["in"]!!
        var ii = 0
        main@while (ii < currentWorkflow.size) {
            if (checkToFunct(currentWorkflow[ii].check, rating)) {
                val result = currentWorkflow[ii].result
                if (result == "A" || result == "R") {
                    if (result == "A") {
                        total += rating.sum()
                    }
                    break@main
                }
                currentWorkflow = input.workflows[result]!!
                ii = 0
            } else {
                ii++
            }
        }

    }
    return total
}

fun part2(input: Day19Input): Long {
    return 0
}

data class Workflow(val input: String) {
    val name: String by lazy { parseName() }
    val instructions: List<Instruction> by lazy { parseInstructions() }

    private fun parseInstructions(): List<Instruction> {
        return input.split("{")[1]
            .replace("}", "")
            .split(",").map { Instruction(it) }
    }

    private fun parseName(): String {
        return input.split("{")[0]
    }
}

data class Instruction(val input: String) {
    val check: String? by lazy { parseCheck() }
    val result: String by lazy { parseResult() }

    private fun parseResult(): String {
        if (input.contains(":")) {
            return input.split(":")[1]
        }
        return input
    }

    private fun parseCheck(): String? {
        if (input.contains(":")) {
            return input.split(":")[0]
        }
        return null
    }

}
fun checkToFunct(check: String?, rating: Rating): Boolean {
    if (check == null) {
        return true
    }
    val variable = when (check[0]) {
        'x' -> Rating::x
        'm' -> Rating::m
        'a' -> Rating::a
        's' -> Rating::s
        else -> throw IllegalArgumentException()
    }
    val num = check.substring(2).toInt()
    return if (check.contains(">")) {
        variable(rating) > num
    } else {
        variable(rating) < num
    }
}

data class Rating(val input: String) {
    private val ratings: Map<Char, Int> by lazy { parseRatings() }
    fun x() = ratings['x']!!
    fun m() = ratings['m']!!
    fun a() = ratings['a']!!
    fun s() = ratings['s']!!

    fun sum(): Int {
        return x() + m() + a() + s()
    }

    private fun parseRatings(): Map<Char, Int> {
        val map = mutableMapOf<Char, Int>()
        for (rating in input
            .replace("{", "")
            .replace("}", "")
            .split(",")) {
            val split = rating.split("=")
            map[split[0][0]] = split[1].toInt()
        }
        return map
    }
}

data class Day19Input(val workflows: Map<String, List<Instruction>>, val ratings: List<Rating>)