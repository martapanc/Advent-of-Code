package aoc2018.day24

import util.readInputLineByLine

fun readInputToList(path: String): Units {
    val vaccineGroups = mutableListOf<Group>()
    val coronaGroups = mutableListOf<Group>()
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

            val currentGroup = Group(
                size,
                hp,
                weaknesses,
                immunities,
                attackPointsAndType[0].toInt(),
                attackPointsAndType[1],
                initiative
            )
            if (infectionSectionReached) {
                coronaGroups.add(currentGroup)
            } else {
                vaccineGroups.add(currentGroup)
            }
        } else if (line == "Infection:") {
            infectionSectionReached = true
        }
        line = iterator.next()
    }
    return Units(vaccineGroups, coronaGroups)
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

fun playRound(units: Units): Units {
    val vaccineGroups = units.vaccineGroups
    val coronaGroups = units.coronaGroups

    val targetMap = mutableMapOf<Pair<String, String>, Int>()
    for ((vacId, vaccine) in vaccineGroups.withIndex()) {
        for ((covId, corona) in coronaGroups.withIndex()) {
            val vaccineToCorona = Pair("v$vacId", "c$covId")
            when {
                corona.weaknesses.contains(vaccine.attackType) -> targetMap[vaccineToCorona] = vaccine.effectivePower * 2
                corona.immunities.contains(vaccine.attackType) -> targetMap[vaccineToCorona] = 0
                else -> targetMap[vaccineToCorona] = vaccine.effectivePower
            }
            val coronaToVaccine = Pair("c$covId", "v$vacId")
            when {
                vaccine.weaknesses.contains(corona.attackType) -> targetMap[coronaToVaccine] = corona.effectivePower * 2
                vaccine.immunities.contains(corona.attackType) -> targetMap[coronaToVaccine] = 0
                else -> targetMap[coronaToVaccine] = corona.effectivePower
            }
        }
    }

    return Units(vaccineGroups, coronaGroups)
}

private fun parseItems(toReplace: String, string: String) = string.replace(toReplace, "").split(",")

data class Group(
    var size: Int,
    val hp: Int,
    val weaknesses: List<String>,
    val immunities: List<String>,
    val attackPoints: Int,
    val attackType: String,
    val initiative: Int
) {
    var effectivePower = size * attackPoints
}

data class Units(val vaccineGroups: List<Group>, val coronaGroups: List<Group>)
