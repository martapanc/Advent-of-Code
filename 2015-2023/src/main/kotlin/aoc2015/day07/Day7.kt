package aoc2015.day07

import util.not
import util.readInputLineByLine
import java.util.*
import kotlin.collections.set


class Day7(val path: String) {

    private val values = mutableMapOf<String, Int>()
    private val instructions = ArrayList<String>()

    fun readInputAndRun(): Int? {
        for (line in readInputLineByLine(path)) {
            val split = line.split(" ").toTypedArray()
            if (split.size == 3) {
                try {
                    values[split[2]] = split[0].toInt()
                    continue
                } catch (e: Exception) {
                }
            }
            instructions.add(line)
        }
        findValueOfWire("a")
        return values["a"]
    }

    private fun findValueOfWire(wireId: String): String {
        if (values[wireId] != null) {
            return wireId
        } else {
            for (string in instructions) {
                val split = string.split(" ").toTypedArray()
                if (split[split.size - 1] == wireId) {
                    val str = split[split.size - 1]
                    when {
                        split.size == 3 -> values[str] = getValue(split[0])
                        split[0] == "NOT" -> values[str] = not(getValue(split[1]))
                        else -> {
                            when (split[1]) {
                                "OR" -> values[str] = getValue(split[0]) or getValue(split[2])
                                "AND" -> values[str] = getValue(split[0]) and getValue(split[2])
                                "LSHIFT" -> values[str] = getValue(split[0]) shl getValue(split[2])
                                "RSHIFT" -> values[str] = getValue(split[0]) shr getValue(split[2])
                            }
                        }
                    }
                }
            }
        }
        return wireId
    }

    fun getValue(input: String): Int {
        return try {
            input.toInt()
        } catch (e: Exception) {
            findValueOfWire(input)
            values[input]!!
        }
    }
}
