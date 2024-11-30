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
    val vaccineGroups = units.vaccineGroups.sortedWith(compareBy({ -it.effectivePower }, { -it.initiative }))
    val coronaGroups = units.coronaGroups.sortedWith(compareBy({ -it.effectivePower }, { -it.initiative }))
    var attackMap = mutableMapOf<String, Attack>()

    attackersChooseDefenders(vaccineGroups, coronaGroups, attackMap, 'v', 'c')
    attackersChooseDefenders(coronaGroups, vaccineGroups, attackMap, 'c', 'v')

    attackMap = attackMap.toSortedMap(compareByDescending { attackMap[it]!!.initiative })
    var anyUnitsKilled = false
    for (attack in attackMap.entries) {
        val attackerId = Character.getNumericValue(attack.key[1])
        val defenderId = Character.getNumericValue(attack.value.target[1])
        var defender: Group
        var attacker: Group
        if (attack.value.target[0] == 'v') {
            attacker = coronaGroups[attackerId]
            defender = vaccineGroups[defenderId]
        } else {
            attacker = vaccineGroups[attackerId]
            defender = coronaGroups[defenderId]
        }
        val kills: Int = attacker.effectivePower * attack.value.powerMultiplier / defender.hp
        if (kills > 0) {
            anyUnitsKilled = true
        }
        when {
            attack.value.target[0] == 'v' -> vaccineGroups[defenderId].killUnits(kills)
            else -> coronaGroups[defenderId].killUnits(kills)
        }
    }
    // Prevent the fight from running forever
    if (!anyUnitsKilled) {
        return Units(listOf(), coronaGroups)
    }
    val vaccineGroupsCopy = vaccineGroups.toMutableList()
    val coronaGroupsCopy = coronaGroups.toMutableList()
    vaccineGroups
        .filter { it.size == 0 }
        .forEach { vaccineGroupsCopy.remove(it) }
    coronaGroups
        .filter { it.size == 0 }
        .forEach { coronaGroupsCopy.remove(it) }
    return Units(vaccineGroupsCopy, coronaGroupsCopy)
}

private fun attackersChooseDefenders(attackerGroups: List<Group>, defenderGroups: List<Group>, attackMap: MutableMap<String, Attack>, attackerId: Char, defenderId: Char) {
    val candidateDefenders = defenderGroups.indices.toMutableList()
    var attackerIndex = 0
    while (candidateDefenders.isNotEmpty() && attackerIndex < attackerGroups.size) {
        val attacker = attackerGroups[attackerIndex]
        val candidates = mutableListOf<Attack>()
        for (defenderIndex in candidateDefenders) {
            val defender = defenderGroups[defenderIndex]
            val attack = Attack("$defenderId$defenderIndex", 0, 0, defender.effectivePower, attacker.initiative, defender.initiative)
            attack.powerMultiplier = when {
                defender.weaknesses.contains(attacker.attackType) -> 2
                defender.immunities.contains(attacker.attackType) -> 0
                else -> 1
            }
            attack.totalDamage = attacker.effectivePower * attack.powerMultiplier
            if (attack.totalDamage > 0)
                candidates.add(attack)
        }
        // If all possible targets are immune, the group cannot attack
        if (candidates.isNotEmpty()) {
            val chosenAttack =
                candidates.sortedWith(compareBy({ -it.totalDamage }, { -it.targetEP }, { -it.initiative }))[0]
            attackMap["$attackerId$attackerIndex"] = chosenAttack
            candidateDefenders.remove(Character.getNumericValue(chosenAttack.target[1]))
        }
        attackerIndex++
    }
}

fun playGameOfCorona(path: String): Int {
    return playGameOfCorona(path, 0).first
}

fun playGameOfCorona(path: String, boost: Int): Pair<Int, Boolean> {
    var roundUnits = playRound(boostVaccine(readInputToList(path), boost))
    while (roundUnits.coronaGroups.isNotEmpty() && roundUnits.vaccineGroups.isNotEmpty()) {
        roundUnits = playRound(roundUnits)
    }
    return if (roundUnits.vaccineGroups.isEmpty()) Pair(roundUnits.coronaGroups.sumBy { it.size }, false)
    else Pair(roundUnits.vaccineGroups.sumBy { it.size }, true)
}

fun playGameOfCoronaWithBoosts(path: String): Int {
    var boost = 0
    var roundResult = playGameOfCorona(path, boost)
    while (!roundResult.second) {
        roundResult = playGameOfCorona(path, ++boost)
    }
    return roundResult.first
}

fun boostVaccine(units: Units, boostValue: Int): Units {
    val boostedVaccineGroups = mutableListOf<Group>()
    for (group in units.vaccineGroups) {
        group.boost(boostValue)
        boostedVaccineGroups.add(group)
    }
    return Units(boostedVaccineGroups, units.coronaGroups)
}

private fun parseItems(toReplace: String, string: String) = string.replace(toReplace, "").split(",")

data class Attack(var target: String, var powerMultiplier: Int = 1, var totalDamage: Int, var targetEP: Int = 0, val initiative: Int, val targetInitiative: Int)

data class Group(var size: Int, val hp: Int, val weaknesses: List<String>, val immunities: List<String>, var attackPoints: Int, val attackType: String, val initiative: Int, val boost: Int = 0) {
    var effectivePower = size * attackPoints

    fun killUnits(kills: Int) {
        size = if (kills < size) size - kills else 0
        effectivePower = size * attackPoints
    }

    fun boost(boost: Int) {
        attackPoints += boost
        effectivePower = size * attackPoints
    }
}

data class Units(val vaccineGroups: List<Group>, val coronaGroups: List<Group>)
