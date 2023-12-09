package aoc2023.day05

fun parse(lines: List<String>): Input {
    val mappings = mutableMapOf<MapType, List<Range>>()
    val seeds = mutableSetOf<Long>()
    for (line in lines) {
        if (line.isEmpty()) {
            continue
        }

        if (line.startsWith("seeds:")) {
            seeds.addAll(line
                .replace("seeds: ", "")
                .split(" ")
                .map { it.toLong() })
            continue
        }

        val split = line.split(": ")
        val numberGroups = split[1].split(" | ")
        val ranges = numberGroups.map {
            val group = it.split(" ").map { str -> str.toLong() }
            Range(group[0], group[1], group[2])
        }
        when {
            split[0].startsWith("seed-to-soil") -> {
                mappings[MapType.SEED_TO_SOIL] = ranges
            }
            split[0].startsWith("soil-to-fertilizer") -> {
                mappings[MapType.SOIL_TO_FERTILIZER] = ranges
            }
            split[0].startsWith("fertilizer-to-water") -> {
                mappings[MapType.FERTILIZER_TO_WATER] = ranges
            }
            split[0].startsWith("water-to-light") -> {
                mappings[MapType.WATER_TO_LIGHT] = ranges
            }
            split[0].startsWith("light-to-temperature") -> {
                mappings[MapType.LIGHT_TO_TEMPERATURE] = ranges
            }
            split[0].startsWith("temperature-to-humidity") -> {
                mappings[MapType.TEMPERATURE_TO_HUMIDITY] = ranges
            }
            split[0].startsWith("humidity-to-location") -> {
                mappings[MapType.HUMIDITY_TO_LOCATION] = ranges
            }
        }
    }
    return Input(seeds, mappings)
}

fun part1(input: Input): Long {
    val seedToLocation = mutableMapOf<Long, Long>()
    input.seeds.forEach { seed ->
        val soil = findMapping(seed, input.mappings[MapType.SEED_TO_SOIL]!!)
        val fertilizer = findMapping(soil, input.mappings[MapType.SOIL_TO_FERTILIZER]!!)
        val water = findMapping(fertilizer, input.mappings[MapType.FERTILIZER_TO_WATER]!!)
        val light = findMapping(water, input.mappings[MapType.WATER_TO_LIGHT]!!)
        val temperature = findMapping(light, input.mappings[MapType.LIGHT_TO_TEMPERATURE]!!)
        val humidity = findMapping(temperature, input.mappings[MapType.TEMPERATURE_TO_HUMIDITY]!!)
        val location  = findMapping(humidity, input.mappings[MapType.HUMIDITY_TO_LOCATION]!!)
        seedToLocation[seed] = location
    }
    return seedToLocation.minOf { it.value }
}

fun part2(mappings: Input): Long {
    return 0
}

fun findMapping(input: Long, ranges: List<Range>): Long {
    for (range in ranges) {
        if (range.sourceRangeStart <= input && input < range.sourceRangeStart + range.rangeLength) {
            val step = input - range.sourceRangeStart
            return range.destRangeStart + step
        } else {
            continue
        }
    }

    return input
}

data class Range(val destRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long)

data class Input(val seeds: Set<Long>, val mappings: Map<MapType, List<Range>>)
enum class MapType(val key: String) {
    SEED_TO_SOIL("seed-to-soil"),
    SOIL_TO_FERTILIZER("soil-to-fertilizer"),
    FERTILIZER_TO_WATER("fertilizer-to-water"),
    WATER_TO_LIGHT("water-to-light"),
    LIGHT_TO_TEMPERATURE("light-to-temperature"),
    TEMPERATURE_TO_HUMIDITY("temperature-to-humidity"),
    HUMIDITY_TO_LOCATION("humidity-to-location");

    companion object {
        fun byKey(key: String): MapType? =
            values().firstOrNull { key == it.key }
    }
}
