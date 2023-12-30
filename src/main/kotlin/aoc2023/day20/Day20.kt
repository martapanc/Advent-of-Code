package aoc2023.day20

fun parse(lines: List<String>): Map<String, Module> {
    val modules = mutableMapOf<String, Module>()
    lines.forEach { line ->
        val sepIndex = line.indexOf(" -> ")
        var name = line.substring(0, sepIndex)
        val destinations = line.substring(sepIndex + 4).split(",").map { it.trim() }
        val type = when {
            name == "broadcaster" -> ModuleType.BROADCAST
            name.startsWith("%") -> ModuleType.FLIP_FLOP
            name.startsWith("&") -> ModuleType.CONJUNCTION
            else -> throw IllegalArgumentException()
        }
        name = name.replace("%", "").replace("&", "")
        modules[name] = Module(name, type, destinations)
    }

    val moduleNames = listOf("broadcaster") + modules.values.flatMap { it.destinations }.distinct()
    moduleNames
        .filter { it !in modules }
        .forEach { modules[it] = Module(it, ModuleType.OUTPUT, emptyList()) }

    return modules
}

fun part1(modules: Map<String, Module>): Long {
    val nameToIndex = modules.keys.mapIndexed { index, value -> value to index }.toMap()
    val moduleList = modules.values.toList()
    var flipFlopState = 0L

    val conjState = LongArray(modules.size)
    val allInputs = LongArray(modules.size)

    for ((idx, module) in moduleList.withIndex()) {
        for (name in module.destinations) {
            val dest = nameToIndex[name]!!
            allInputs[dest] = allInputs[dest] or (1L shl idx)
        }
    }

    var lowPulses = 0L
    var highPulses = 0L
    val qSource = IntArray(1024)
    val qDest = IntArray(1024)
    val qPulse = IntArray(1024)

    repeat(1000) {
        println("%s %s".format(lowPulses, highPulses))
        lowPulses++
        var qh = 0
        var qt = 0

        fun enq(source: Int, dest: Int, pls: Int) {
            qSource[qt] = source
            qDest[qt] = dest
            qPulse[qt] = pls
            qt++
        }

        fun enqAll(source: Int, pulse: Int) {
            val names = moduleList[source].destinations
            for (name in names) {
                enq(source, nameToIndex[name]!!, pulse)
            }
            when (pulse) {
                0 -> lowPulses += names.size
                1 -> highPulses += names.size
                else -> throw IllegalArgumentException()
            }
        }

        enqAll(0, 0)

        while (qh < qt) {
            val source = qSource[qh]
            val dest = qDest[qh]
            val pulse = qPulse[qh]
            qh++
            val module = moduleList[dest]
            val outPulse = when (module.type) {
                ModuleType.FLIP_FLOP -> {
                    if (pulse == 1) {
                        -1
                    } else {
                        flipFlopState = flipFlopState xor (1L shl dest)
                        ((flipFlopState shr dest) and 1).toInt()
                    }
                }
                ModuleType.CONJUNCTION -> {
                    val state = (conjState[dest] and (1L shl source).inv()) or (pulse.toLong() shl source)
                    val all = allInputs[dest]
                    conjState[dest] = state
                    if (state and all == all) 0 else 1
                }
                ModuleType.OUTPUT -> -1
                else -> throw IllegalArgumentException()
            }
            if (outPulse >= 0) {
                enqAll(dest, outPulse)
            }
        }
    }
    return lowPulses * highPulses
}

fun part2(map: Map<String, Module>): Long {
    return 0
}


data class Module(val name: String, val type: ModuleType, val destinations: List<String>)

enum class ModuleType {
    FLIP_FLOP, CONJUNCTION, BROADCAST, OUTPUT
}
