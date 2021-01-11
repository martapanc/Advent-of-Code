package aoc2015.day13

import aoc2019.day07.PermutationUtil
import util.readInputLineByLine

fun readInputToList(path: String): List<Neighbors> {
    val list = mutableListOf<Neighbors>()
    val regex = Regex("([A-Za-z]+) would (lose|gain) (\\d+) happiness units by sitting next to ([A-Za-z]+).")
    for (line in readInputLineByLine(path)) {
        val match = regex.matchEntire(line)
        val personA = Person.valueOf(match!!.groupValues[1].first().toString())
        val personB = Person.valueOf(match.groupValues[4].first().toString())
        val gainOrLose = match.groupValues[2]
        val happinessChange = match.groupValues[3].toInt()
        list.add(Neighbors(personA, personB, if (gainOrLose == "lose") -happinessChange else happinessChange))
    }
    return list
}

fun findMostEfficientArrangement(list: List<Neighbors>, includeMe: Boolean = false): Int {
    val values = Person.values().map { it.toString().first() }.toMutableList()
    if (includeMe) values.add('X')
    val permutations = PermutationUtil.generatePermutations(values.joinToString(""))
    var maxValue = 0
    val happinessMap = mutableMapOf<Pair<Char, Char>, Int>()
    for (item in list) {
        val p1 = item.personA.name.first()
        val p2 = item.personB.name.first()
        happinessMap[Pair(p1, p2)] = item.happinessChange
        happinessMap[Pair('X', p2)] = 0
        happinessMap[Pair(p1, 'X')] = 0
    }
    val size = if (includeMe) 8 else 7
    for (permutation in permutations) {
        var currentHappinessValue = 0
        for (i in permutation.indices) {
            val current = permutation[i]
            val leftPerson = permutation[if (i == 0) size else i - 1]
            val rightPerson = permutation[if (i == size) 0 else i + 1]
            currentHappinessValue += happinessMap[Pair(current, leftPerson)]!!
            currentHappinessValue += happinessMap[Pair(current, rightPerson)]!!
        }
        if (currentHappinessValue > maxValue)
            maxValue = currentHappinessValue
    }
    return maxValue
}

data class Neighbors(val personA: Person, val personB: Person, val happinessChange: Int)

enum class Person { A, B, C, D, E, F, G, M }
