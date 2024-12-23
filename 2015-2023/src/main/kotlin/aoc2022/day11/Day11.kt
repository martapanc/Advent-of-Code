package aoc2022.day11

import util.lcm
import util.readInputLineByLine

fun readInputToMonkeys(path: String): List<Monkey> {
    val monkeys = mutableListOf<Monkey>()
    val inputList = readInputLineByLine(path)
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

fun part1(monkeys: List<Monkey>): Long = solve(monkeys, 20)

fun part2(monkeys: List<Monkey>): Long = solve(monkeys, 10000, true)

fun solve(monkeys: List<Monkey>, times: Int, part2: Boolean = false): Long {
    val divisorsLCM = lcm(monkeys.map { it.testDivBy })
    repeat(times) {
        monkeys.forEach { monkey ->
            monkey.startingItems.forEach { item ->
                val newWorryLevel = parseOpAndExec(item, monkey.operation)
                val worryLevel = if (part2) newWorryLevel % divisorsLCM else newWorryLevel / 3
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
