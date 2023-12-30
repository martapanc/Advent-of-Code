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
    val moduleNames = listOf("broadcaster") + modules.values.flatMap { it.destinations }.distinct()

    val nameToIndex = moduleNames.withIndex().associateBy ( {it.value}, {it.index})
    val moduleList = moduleNames.map { modules[it]!! }
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
    val queueSource = IntArray(1024)
    val queueDest = IntArray(1024)
    val queuePulse = IntArray(1024)

    repeat(1000) {
        lowPulses++
        var queueHead = 0
        var queueTail = 0

        fun enqueue(source: Int, dest: Int, pulse: Int) {
            queueSource[queueTail] = source
            queueDest[queueTail] = dest
            queuePulse[queueTail] = pulse
            queueTail++
        }

        fun enqueueAll(source: Int, pulse: Int) {
            val names = moduleList[source].destinations
            for (name in names) {
                enqueue(source, nameToIndex[name]!!, pulse)
            }
            when (pulse) {
                0 -> lowPulses += names.size
                1 -> highPulses += names.size
                else -> throw IllegalArgumentException()
            }
        }

        enqueueAll(0, 0)

        while (queueHead < queueTail) {
            val source = queueSource[queueHead]
            val dest = queueDest[queueHead]
            val pulse = queuePulse[queueHead]
            queueHead++
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
                enqueueAll(dest, outPulse)
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
