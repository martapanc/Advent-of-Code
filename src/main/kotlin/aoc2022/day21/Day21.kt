package aoc2022.day21

import org.apache.commons.lang3.StringUtils.isNumeric
import util.readInputLineByLine

fun readInputToMonkeyJobs(path: String): Map<String, MonkeyJob> {
    val map = mutableMapOf<String, MonkeyJob>()
    readInputLineByLine(path).map { line ->
        val split = line.split(": ")
        if (isNumeric(split[1])) {
            map[split[0]] = Value(split[1].toLong())
        } else {
            val op = split[1].split(" ")
            map[split[0]] = Operation(op[0], op[2], Op.fromSign(op[1].toCharArray().first())!!)
        }
    }
    return map
}

fun part1(monkeyJobs: Map<String, MonkeyJob>): Long {
    val monkeyJob = expandMonkeyJob(monkeyJobs["root"]!!, monkeyJobs) as Value
    return monkeyJob.num
}

fun part2(monkeyJobs: MutableMap<String, MonkeyJob>, initialNum: Long = 1L): Long {
    var myNumber = initialNum
    var lookingForMatch = true
    val rootSecondJob = (monkeyJobs["root"] as Operation).second // Moving this outside as it doesn't vary
    val secondResult = expandMonkeyJob(monkeyJobs[rootSecondJob]!!, monkeyJobs) as Value

    while (lookingForMatch) {
        monkeyJobs["humn"] = Value(myNumber)
        val rootFirstJob = (monkeyJobs["root"] as Operation).first
        val firstResult = expandMonkeyJob(monkeyJobs[rootFirstJob]!!, monkeyJobs) as Value
        if (firstResult.num == secondResult.num) {
            lookingForMatch = false
        } else {
            myNumber++
        }
    }
    return myNumber
}

fun expandMonkeyJob(job: MonkeyJob, map: Map<String, MonkeyJob>): MonkeyJob {
    if (job is Value) {
        return job
    }
    val op = job as Operation
    val firstMonkeyJob = expandMonkeyJob(map[op.first]!!, map) as Value
    val secondMonkeyJob = expandMonkeyJob(map[op.second]!!, map) as Value
    return parseAndExec(firstMonkeyJob, secondMonkeyJob, job.op)
}

fun parseAndExec(first: Value, second: Value, op: Op): Value {
    val firstNum = first.num
    val secondNum = second.num
    return when(op) {
        Op.ADD -> Value(firstNum + secondNum)
        Op.SUBTRACT -> Value(firstNum - secondNum)
        Op.MULTIPLY -> Value(firstNum * secondNum)
        Op.DIVIDE -> Value(firstNum / secondNum)
    }
}

abstract class MonkeyJob {
    abstract fun areBothNumbers(): Boolean
}

class Operation(val first: String, val second: String, val op: Op) : MonkeyJob() {
    override fun toString(): String = "$first ${op.sign} $second"

    override fun areBothNumbers(): Boolean = isNumeric(first) && isNumeric(second)
}

class Value(val num: Long) : MonkeyJob() {
    override fun areBothNumbers(): Boolean = false

    override fun toString(): String = "$num"
}

enum class Op(val sign: Char) {
    ADD('+'), SUBTRACT('-'), MULTIPLY('*'), DIVIDE('/');

    companion object {
        fun fromSign(sign: Char): Op? = Op.values().firstOrNull() { sign == it.sign }
    }
}
