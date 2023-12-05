package aoc2023.day05

fun parse(lines: List<String>) {
    val mappings = mutableMapOf<MapType, List<Range>>()

    lines.forEach { line ->
        when {
            line.startsWith("seeds:") -> {}
            line.startsWith("seed-to-soil") -> {}
            line.startsWith("soil-to-fertilizer") -> {}
            line.startsWith("fertilizer-to-water") -> {}
            line.startsWith("water-to-light") -> {}
            line.startsWith("light-to-temperature") -> {}
            line.startsWith("temperature-to-humidity") -> {}
            line.startsWith("humidity-to-location") -> {}
        }
    }
}

fun part1(list: List<String>): Int {
    return 0
}

fun part2(list: List<String>): Int {
    return 0
}

data class Range(val destRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long)

enum class MapType {
    SEED_TO_SOIL,
    SOIL_TO_FERTILIZER,
    FERTILIZER_TO_WATER,
    WATER_TO_LIGHT,
    LIGHT_TO_TEMPERATURE,
    TEMPERATURE_TO_HUMIDITY,
    HUMIDITY_TO_LOCATION
}
