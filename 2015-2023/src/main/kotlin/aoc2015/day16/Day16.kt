package aoc2015.day16

import util.readInputLineByLine

fun readInputToList(path: String): List<Map<Compound, Int?>> {
    val list = mutableListOf<Map<Compound, Int?>>()
    for (line in readInputLineByLine(path)) {
        val split = line.replace(":", "").split(",", " ")
        val auntSueMap = mutableMapOf<Compound, Int?>()
        for (value in Compound.values()) auntSueMap[value] = null
        auntSueMap[Compound.valueOf(split[2])] = split[3].toInt()
        auntSueMap[Compound.valueOf(split[5])] = split[6].toInt()
        auntSueMap[Compound.valueOf(split[8])] = split[9].toInt()
        list.add(auntSueMap)
    }
    return list
}

fun findTheRightAuntSue(list: List<Map<Compound, Int?>>, isPart2: Boolean = false): Int {
    val mfcsamOutput = mapOf(
        Compound.children to 3,
        Compound.cats to 7,
        Compound.samoyeds to 2,
        Compound.pomeranians to 3,
        Compound.akitas to 0,
        Compound.vizslas to 0,
        Compound.goldfish to 5,
        Compound.trees to 3,
        Compound.cars to 2,
        Compound.perfumes to 1,
    )
    var auntSueNumber = 0
    for ((index, auntSue) in list.withIndex()) {
        if (!isPart2 && Compound.values().all { auntSue[it] == null || auntSue[it]!! == mfcsamOutput[it]!! }) {
            auntSueNumber = index + 1
            break
        }

        if (isPart2 &&
            listOf(Compound.cats, Compound.trees)
                .all { auntSue[it] == null || auntSue[it]!! > mfcsamOutput[it]!! } &&
            listOf(Compound.pomeranians, Compound.goldfish)
                .all { auntSue[it] == null || auntSue[it]!! < mfcsamOutput[it]!! } &&
            listOf(
                Compound.children, Compound.samoyeds, Compound.akitas,
                Compound.vizslas, Compound.cars, Compound.perfumes
            )
                .all { auntSue[it] == null || auntSue[it]!! == mfcsamOutput[it]!! }
        ) {
            auntSueNumber = index + 1
            break
        }

    }
    return auntSueNumber
}

enum class Compound {
    children, cats, samoyeds, pomeranians, akitas, vizslas, goldfish, trees, cars, perfumes
}
