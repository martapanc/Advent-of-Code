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
    return input.ratings.sumOf { workFlow(input, it).toLong() }
}

private fun workFlow(input: Day19Input, rating: Rating): Int {
    var currentWorkflow = input.workflows["in"]!!
    var ii = 0
    main@ while (ii < currentWorkflow.size) {
        if (evalCheck(currentWorkflow[ii].condition, rating)) {
            val result = currentWorkflow[ii].dest
            if (result == "A" || result == "R") {
                if (result == "A") {
                    return rating.sum()
                }
                break@main
            }
            currentWorkflow = input.workflows[result]!!
            ii = 0
        } else {
            ii++
        }
    }
    return 0
}

fun part2(input: Day19Input, max: Int = 4000): Long {
    val ranges: Map<Char, Range> = mapOf(
        'x' to Range(1, 4000),
        'm' to Range(1, 4000),
        'a' to Range(1, 4000),
        's' to Range(1, 4000)
    )
    return countAccepted(input.workflows, ranges)
}

fun countAccepted(workflows: Map<String, List<Instruction>>, ranges0: Map<Char, Range>, current: Instruction = Instruction("in")): Long {
    if (current.condition == null && current.dest == "A") {
        var prod = 1L
        ranges0.values.forEach {
            prod *= it.size()
        }
        return prod
    }
    if (current.condition == null && current.dest == "R") {
        return 0L
    }

    val workflow = workflows[current.dest]!!
    val ranges = ranges0.toMutableMap()
    var combinations = 0L

    for (instruction in workflow) {
        val condition = instruction.condition
        if (condition == null)  {
            combinations += countAccepted(workflows, ranges, Instruction(instruction.dest))
            break
        } else {
            val variable = condition[0]
            val op = condition[1]
            val comparator = condition.substring(2).toInt()
            val currentRange = ranges[variable]!!
            val (rangeTrue, rangeFalse) = when (op) {
                '<' -> currentRange.low .. minOf(currentRange.high, comparator - 1) to maxOf(currentRange.low, comparator) .. currentRange.high
                '>' -> maxOf(currentRange.low, comparator + 1) .. currentRange.high to currentRange.low .. minOf(currentRange.high, comparator)
                else -> throw IllegalArgumentException()
            }

            if (!rangeTrue.isEmpty()) {
                val ranges1 = ranges.toMutableMap()
                ranges1[variable] = Range(rangeTrue.first, rangeTrue.last)
                combinations += countAccepted(workflows, ranges1, Instruction(instruction.dest))
            }
            if (rangeFalse.isEmpty()) {
                break
            }
            ranges[variable] = Range(rangeFalse.first, rangeFalse.last)
        }
    }

    return combinations
}

data class Range(val low: Int, val high: Int) {
    fun size(): Int = high - low + 1
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
    val condition: String? by lazy { parseCheck() }
    val dest: String by lazy { parseResult() }

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
fun evalCheck(check: String?, rating: Rating): Boolean {
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
    constructor(x: Int, m: Int, a: Int, s: Int) : this(buildString {
        append("{x=$x,m=$m,a=$a,s=$s}")
    })

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