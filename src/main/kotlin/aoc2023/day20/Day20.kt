package aoc2023.day20

fun parse(lines: List<String>): Day20Input {
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

    val moduleInputs = moduleNames.associateWith { HashSet<String>() }
    for ((name, module) in modules) {
        for (dest in module.destinations) {
            moduleInputs[dest]!!.add(name)
        }
    }
    return Day20Input(modules, moduleNames, moduleInputs)
}

fun part1(input: Day20Input): Long {
    val modules = input.modules

    val nameToIndex = input.moduleNames.withIndex().associateBy({ it.value }, { it.index })
    val moduleList = input.moduleNames.map { modules[it]!! }
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

fun part2(input: Day20Input): Long {
    val used = HashSet<String>()
    val order = ArrayList<String>()

    val moduleList = input.moduleNames.map { input.modules[it]!! }
    val nameToIndex = input.moduleNames.withIndex().associateBy({ it.value }, { it.index })

    fun dfs1(name: String) {
        if (name in used)
            return
        used += name
        for (dest in input.modules[name]!!.destinations) {
            dfs1(dest)
        }
        order += name
    }

    for (name in input.moduleNames) {
        dfs1(name)
    }

    val indexToComponent = IntArray(input.moduleNames.size)
    var componentCount = 0

    fun dfs2(name: String) {
        val index = nameToIndex[name]!!
        if (indexToComponent[index] > 0) {
            return
        }
        indexToComponent[index] = componentCount
        for (source in input.moduleInputs[name]!!) {
            dfs2(source)
        }
    }

    val keyNames = ArrayList<String>()
    for (name in order.reversed()) {
        val index = nameToIndex[name]!!
        if (indexToComponent[index] > 0)
            continue
        keyNames += name
        componentCount++
        dfs2(name)
    }

    fun component(name: String) = indexToComponent[nameToIndex[name]!!]

    fun withType(name: String) = "${input.modules[name]!!.type}$name"

    fun withComponent(names: Iterable<String>): List<String> =
        names.sortedBy { component(it) }.map { name -> "${withType(name)} ${component(name)}" }

    for (name in keyNames) {
        val module = input.modules[name]!!
        println("${component(name)} ${module.type}$name -> ${withComponent(module.destinations)} <- ${withComponent(input.moduleInputs[name]!!)}")
    }

    val aggregator = input.moduleInputs["rx"]!!.single()
    val feeders = input.moduleInputs[aggregator]!!.sortedBy { component(it) }

    check(input.moduleInputs[aggregator] == feeders.toSet())
    check(input.modules[aggregator]!!.type == ModuleType.CONJUNCTION)

    fun dfsRecursiveInputs(name: String) {
        if (name in used)
            return
        used += name
        for (source in input.moduleInputs[name]!!)
            dfsRecursiveInputs(name)
    }

    val feederToRecInputs = feeders.associateWith { feeder ->
        used.clear()
        dfsRecursiveInputs(feeder)
        used.toSet()
    }
    for ((name, inputs) in feederToRecInputs) {
        println("feeder $name, all inputs ${inputs.size} ${withComponent(inputs)}")
    }

    class FeederData(val stateMask: Long, val conjIdx: IntArray)

    var isFeeder = 0L
    val feederToData = feeders.associateWith { feeder ->
        isFeeder = isFeeder or (1L shl nameToIndex[feeder]!!)
        var mask = 0L
        val conjunctions = ArrayList<String>()
        for (name in feederToRecInputs[feeder]!!) {
            mask = mask or (1L shl nameToIndex[name]!!)
            if (input.modules[name]!!.type == ModuleType.CONJUNCTION) {
                conjunctions += name
            }
        }
//        check(conjunctions.size == 2)

        FeederData(mask, conjunctions.map { nameToIndex[it]!! }.toIntArray())
    }

    var flipState = 0L
    val conjState = LongArray(input.modules.size)
    val allInputs = LongArray(input.modules.size)

    data class FeederState(val state: Long, val c1: Long, val c2: Long)
    fun getFeederState(feeder: String): FeederState {
        val data = feederToData[feeder]!!
        return FeederState(flipState and data.stateMask, conjState[data.conjIdx[0]], conjState[data.conjIdx[1]])
    }
    val feederStates = feeders.associateWith { HashMap<FeederState, Int>() }
    val feederOnesAt = feeders.associateWith { ArrayList<Int>() }
    val feederCycle = HashMap<String, Int>()

    for ((src, mod) in moduleList.withIndex()) {
        for (name in mod.destinations) {
            val dst = nameToIndex[name]!!
            allInputs[dst] = allInputs[dst] or (1L shl src)
        }
    }

    val queueSource = IntArray(1024)
    val queueDest = IntArray(1024)
    val queuePulse = IntArray(1024)

    val rxIdx = nameToIndex["rx"]!!
    var count = 0
    var done = false
    var answer = 1L
    tailrec fun gcd(x: Long, y: Long): Long = if (y == 0L) x else gcd(y, x % y)

    fun lcm(x: Long, y: Long) = x * y / gcd(x, y)

    while(feederCycle.size < feeders.size && !done) {
        count++
        var queueHead = 0
        var queueTail = 0
        fun enqueue(source: Int, dest: Int, pulse: Int) {
            if (dest == rxIdx && pulse == 0)
                done = true
            queueSource[queueTail] = source
            queueDest[queueTail] = dest
            queuePulse[queueTail] = pulse
            queueTail++
        }
        fun enqueueAll(source: Int, pulse: Int) {
            if (pulse == 1 && ((1L shl source) and isFeeder) != 0L) {
                val feeder = input.moduleNames[source]
                feederOnesAt[feeder]!!.add(count)
            }
            val names = moduleList[source].destinations
            for (name in names) {
                enqueue(source, nameToIndex[name]!!, pulse)
            }
        }
        enqueueAll(0, 0)
        while (queueHead < queueTail) {
            val src = queueSource[queueHead]
            val dst = queueDest[queueHead]
            val pls = queuePulse[queueHead]
            queueHead++
            val module = moduleList[dst]
            val outputPulse = when (module.type) {
                ModuleType.FLIP_FLOP -> if (pls == 1) {
                    -1
                } else {
                    flipState = flipState xor (1L shl dst)
                    ((flipState shr dst) and 1).toInt()
                }
                ModuleType.CONJUNCTION -> {
                    val state = (conjState[dst] and (1L shl src).inv()) or (pls.toLong() shl src)
                    val all = allInputs[dst]
                    conjState[dst] = state
                    if (state and all == all) 0 else 1
                }
                ModuleType.OUTPUT -> -1
                else -> throw IllegalArgumentException()
            }
            if (outputPulse >= 0) {
                enqueueAll(dst, outputPulse)
            }
        }

        for (feeder in feeders) {
            if (feeder !in feederCycle) {
                val state = getFeederState(feeder)
                val feederStates = feederStates[feeder]!!
                if (state in feederStates) {
                    val previous = feederStates[state]!!
                    val length = count - previous
                    println("Feeder $feeder cycle at $count to $previous, len = $length, ones = ${feederOnesAt[feeder]}")
                    check(feederOnesAt[feeder] == listOf(length))
                    feederCycle[feeder] = length
                    answer = lcm(answer, length.toLong())
                } else {
                    feederStates[state] = count
                }
            }
        }
    }

    return answer
}

data class Module(val name: String, val type: ModuleType, val destinations: List<String>)

enum class ModuleType {
    FLIP_FLOP, CONJUNCTION, BROADCAST, OUTPUT
}

data class Day20Input(
    val modules: Map<String, Module>,
    val moduleNames: List<String>,
    val moduleInputs: Map<String, HashSet<String>>
)