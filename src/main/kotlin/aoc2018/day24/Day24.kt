package aoc2018.day24

import util.readInputLineByLine

fun readInputToList(path: String): Pair<List<Group>, List<Group>> {
    val immuneSystemGroups = mutableListOf<Group>()
    val infectionGroups = mutableListOf<Group>()
    val lines = readInputLineByLine(path)
    val iterator = lines.iterator()
    var line = iterator.next()
    var infectionSectionReached = false
    while (iterator.hasNext()) {
        if (line != "" && line != "Immune System:" && line != "Infection:") {
            val size = line.substringBefore(" ").toInt()
            val hp = line.substringAfter("each with ").substringBefore(" hit points").toInt()
            val weaknessesImmunities = line.substringAfter("(").substringBefore(")")
            val (weaknesses, immunities) = parseWeaknessesAndImmunities(weaknessesImmunities)
            val attackPointsAndType = line.substringAfter("attack that does ").substringBefore(" damage").split(" ")
            val initiative = line.substringAfter("initiative ").toInt()

            val currentGroup = Group(size, hp, weaknesses, immunities, attackPointsAndType[0].toInt(), attackPointsAndType[1], initiative)
            if (infectionSectionReached) {
                infectionGroups.add(currentGroup)
            } else {
                immuneSystemGroups.add(currentGroup)
            }
        } else if (line == "Infection:") {
            infectionSectionReached = true
        }
        line = iterator.next()
    }
    return Pair(immuneSystemGroups, infectionGroups)
}

fun parseWeaknessesAndImmunities(input: String): Pair<List<String>, List<String>> {
    val weaknesses = mutableListOf<String>()
    val immunities = mutableListOf<String>()
    val immuneTo = "immune to"
    val weakTo = "weak to"
    when {
        input.isEmpty() -> return Pair(listOf(), listOf())
        input.contains(immuneTo) && input.contains(weakTo) -> {
            val parts = input.split(";")
            if (parts[0].contains(immuneTo)) {
                immunities += parseItems(immuneTo, parts[0])
                weaknesses += parseItems(weakTo, parts[1])
            } else {
                immunities += parseItems(immuneTo, parts[1])
                weaknesses += parseItems(weakTo, parts[0])
            }
        }
        input.contains(immuneTo) -> immunities += parseItems(immuneTo, input)
        input.contains(weakTo) -> weaknesses += parseItems(weakTo, input)
    }
    return Pair(weaknesses.map(String::trim), immunities.map(String::trim))
}

private fun parseItems(toReplace: String, string: String) = string.replace(toReplace, "").split(",")

data class Group(
    val size: Int,
    val hp: Int,
    val weaknesses: List<String>,
    val immunities: List<String>,
    val attackPoints: Int,
    val attackType: String,
    val initiative: Int
)


