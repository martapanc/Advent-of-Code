package aoc2022.day11

import util.readInputLineByLine
import kotlin.math.floor

fun readInputToMonkeys(path: String): List<Monkey> {
    val inputList = readInputLineByLine(path)
    val monkeys = mutableListOf<Monkey>()
    (inputList.indices step 7).forEach { index ->
        val startingItems = inputList[index + 1].trim().replace("Starting items: ", "").split(", ").map { it.toLong() }
        val operation = inputList[index + 2].trim().replace("Operation: new = ", "")
        val testDivBy = inputList[index + 3].trim().replace("Test: divisible by ", "").toLong()
        val ifTrue = inputList[index + 4].trim().replace("If true: throw to monkey ", "").toInt()
        val ifFalse = inputList[index + 5].trim().replace("If false: throw to monkey ", "").toInt()
        monkeys.add(Monkey(startingItems.toMutableList(), operation, testDivBy, ifTrue, ifFalse))
    }
    return monkeys
}

fun part1(monkeys: List<Monkey>): Long {
    repeat(20) {
        monkeys.forEach { monkey ->
            monkey.startingItems.forEach { item ->
                val worryLevel = floor((parseOpAndExec(item, monkey.operation) / 3).toDouble()).toLong()
                val targetMonkey = if ((worryLevel % monkey.testDivBy) == 0L) monkey.ifTrue else monkey.ifFalse
                monkeys[targetMonkey].startingItems.add(worryLevel)
                monkey.inspectedItemCount++
            }
            monkey.startingItems.clear()
        }
    }
    val busiestMonkeys = monkeys.sortedByDescending { it.inspectedItemCount }
    return busiestMonkeys[0].inspectedItemCount * busiestMonkeys[1].inspectedItemCount
}

fun part2(monkeys: List<Monkey>): Long {
    val divisorsLCM = lcm(monkeys.map { it.testDivBy })
    repeat(10000) {
        monkeys.forEach { monkey ->
            monkey.startingItems.forEach { item ->
                val worryLevel = (parseOpAndExec(item, monkey.operation) % divisorsLCM)
                val targetMonkey = if ((worryLevel % monkey.testDivBy) == 0L) monkey.ifTrue else monkey.ifFalse
                monkeys[targetMonkey].startingItems.add(worryLevel)
                monkey.inspectedItemCount++
            }
            monkey.startingItems.clear()
        }
    }
    val busiestMonkeys = monkeys.sortedByDescending { it.inspectedItemCount }
    return busiestMonkeys[0].inspectedItemCount * busiestMonkeys[1].inspectedItemCount
}

fun parseOpAndExec(value: Long, operation: String): Long {
    return if (operation.contains("*")) {
        if (operation.contains("* old")) {
            value * value
        } else {
            value * operation.split(" * ")[1].toLong()
        }
    } else {
        value + operation.split(" + ")[1].toLong()
    }
}

data class Monkey(
    val startingItems: MutableList<Long>,
    val operation: String,
    val testDivBy: Long,
    val ifTrue: Int,
    val ifFalse: Int,
    var inspectedItemCount: Long = 0
)

fun lcm(values: List<Long>): Long {
    var result = values[0]
    values.indices.forEach { index ->
        result = util.lcm(result, values[index])
    }
    return result
}
