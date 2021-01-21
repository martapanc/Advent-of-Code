import util.not
import util.readInputLineByLine
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set

/**
 * Created by mdawg on 12/4/2015.
 */
class Day1(val path: String) {


    var VARS = mutableMapOf<String, Int>()
    var INSN = mutableListOf<String>()

    fun readInputAndRun(): Int? {
        for (line in readInputLineByLine(path)) {
            val strs = line.split(" ").toTypedArray()
            if (strs.size == 3) {
                try {
                    val i = strs[0].toInt()
                    VARS[strs[2]] = i
                    continue
                } catch (e: Exception) {
                }
            }
            INSN.add(line)
        }
        doVar("a")
        return VARS["a"]
    }

    fun doVar(name: String): String {
        if (VARS[name] != null) {
            return name
        } else {
            for (string in INSN) {
                val split = string.split(" ").toTypedArray()
                if (split[split.size - 1] == name) {
                    println(string)
                    val str = split[split.size - 1]
                    when {
                        split.size == 3 -> {
                            VARS[str] = getValue(split[0])
                        }
                        split[0] == "NOT" -> {
                            VARS[str] = not(getValue(split[1]))
                        }
                        else -> {
                            when (split[1]) {
                                "OR" -> VARS[str] = getValue(split[0]) or getValue(split[2])
                                "AND" -> VARS[str] = getValue(split[0]) and getValue(split[2])
                                "LSHIFT" -> VARS[str] = getValue(split[0]) shl getValue(split[2])
                                "RSHIFT" -> VARS[str] = getValue(split[0]) shr getValue(split[2])
                            }
                        }
                    }
                }
            }
        }
        return name
    }

    fun getValue(input: String): Int {
        return try {
            input.toInt()
        } catch (e: Exception) {
            doVar(input)
            VARS[input]!!
        }
    }
}