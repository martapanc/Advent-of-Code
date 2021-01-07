package aoc2020.day07

import java.io.File
import java.io.InputStream

data class BagQuantity(var quantity: Int, var color: String)

fun readInputToMap(path: String): Map<String, List<BagQuantity>> {
    val inputStream: InputStream = File(path).inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val inputMap = mutableMapOf<String, List<BagQuantity>>()
    for (line in lineList) {
        val data = line
            .replace(".", "")
            .replace(" bags", "")
            .replace(" bag", "")
            .split(" contain ")
        val bagList = mutableListOf<BagQuantity>()
        for (bagString in data[1].split(", ")) {
            if (bagString == "no other") {
                bagList.add(BagQuantity(0, "None"))
            } else {
                val bagAndQuantity = bagString.split(Regex("\\s"), 2)
                bagList.add(BagQuantity(bagAndQuantity[0].toInt(), bagAndQuantity[1]))
            }
        }
        inputMap[data[0]] = bagList
    }
    return inputMap
}

fun buildMapFromContainedBags(inputMap: Map<String, List<BagQuantity>>): Map<String, List<String>?> {
    val outputMap: MutableMap<String, MutableList<String>?> = HashMap()
    for (entry in inputMap.entries) {
        entry.value
            .filter { it.color != "None" }
            .forEach {
                if (outputMap.containsKey(it.color)) {
                    val list = outputMap[it.color]?.toMutableList()
                    list?.add(entry.key)
                    outputMap[it.color] = list?.toMutableList()
                } else {
                    val newList = mutableListOf(entry.key)
                    outputMap[it.color] = newList
                }
            }
    }
    return outputMap
}

fun findContainingBags(bagMap: Map<String, List<String>?>, start: String): Int {
    var bagList = mutableSetOf<String>()

    val startList: List<String>? = bagMap[start]
    if (startList != null) bagList.addAll(startList)

    var copyBagList = mutableSetOf<String>()
    while (copyBagList != bagList) {
        val tempBagList = bagList.toMutableSet()
        copyBagList = bagList.toMutableSet()
        bagList
            .mapNotNull { bagMap[it] }
            .forEach { tempBagList.addAll(it) }
        bagList = tempBagList.toMutableSet()
    }
    return bagList.size
}

fun findContainedIndividualBags(bagMap: Map<String, List<BagQuantity>>, start: String): Int {
    var count = 0
    var bagQuantitiesMap = mutableMapOf<String, Int>()
    for (bag in bagMap[start] ?: error("")) {
        bagQuantitiesMap[bag.color] = bag.quantity
        count += bag.quantity
    }

    var newBagQuantitiesMap = mutableMapOf<String, Int>()
    while (bagQuantitiesMap.isNotEmpty()) {
        for (bag in bagQuantitiesMap.entries) {
            val containedBags: List<BagQuantity>? = bagMap[bag.key]
            for (containedBag in containedBags!!) {
                if (newBagQuantitiesMap.containsKey(containedBag.color)) {
                    val qnt = newBagQuantitiesMap[containedBag.color]!!
                    newBagQuantitiesMap[containedBag.color] = qnt + (containedBag.quantity * bag.value)
                } else if (containedBag.quantity != 0) {
                    newBagQuantitiesMap[containedBag.color] = containedBag.quantity * bag.value
                }
            }
        }
        for (bagQuantity in newBagQuantitiesMap.values) count += bagQuantity
        bagQuantitiesMap = newBagQuantitiesMap.toMutableMap()
        newBagQuantitiesMap = mutableMapOf()
    }
    return count
}
