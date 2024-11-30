package aoc2020.day16

import java.io.File

data class TicketField(var name: String, var range1: Pair<Int, Int>, var range2: Pair<Int, Int>)

data class TicketValidation(var validTickets: List<List<Int>>, var ticketScanningErrorRate: Int)

fun readInputFieldsToMap(path: String): Map<String, TicketField> {
    val map = mutableMapOf<String, TicketField>()
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    for (line in lineList) {
        val split = line.split(": ")
        val ranges = split[1].split(" or ")
        val range1 = ranges[0].split("-")
        val range2 = ranges[1].split("-")
        map[split[0]] = TicketField(
            split[0],
            Pair(range1[0].toInt(), range1[1].toInt()),
            Pair(range2[0].toInt(), range2[1].toInt())
        )
    }
    return map
}

fun readInputTicketsToList(path: String): List<List<Int>> {
    val list = mutableListOf<List<Int>>()
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    for (line in lineList) {
        val currList = mutableListOf<Int>()
        line.split(",").mapTo(currList) { it.toInt() }
        list.add(currList)
    }
    return list
}

fun computeTicketScanningErrorRate(ticketList: List<List<Int>>, ranges: Map<String, TicketField>): TicketValidation {
    var ticketScanningErrorRate = 0
    val validRanges = mutableListOf<Pair<Int, Int>>()
    val validTicketsList = mutableListOf<List<Int>>()
    for (range in ranges.values) {
        validRanges.add(range.range1)
        validRanges.add(range.range2)
    }
    for (ticket in ticketList) {
        var isValidTicket = true
        for (value in ticket) {
            val valueBelongsToValidRange = validRanges.any { value in it.first..it.second }
            if (!valueBelongsToValidRange) {
                isValidTicket = false
                ticketScanningErrorRate += value
            }
        }
        if (isValidTicket) {
            validTicketsList.add(ticket)
        }
    }
    return TicketValidation(validTicketsList, ticketScanningErrorRate)
}

fun computeDepartureValuesChecksum(
    myTicket: List<Int>, ticketList: List<List<Int>>, ranges: Map<String, TicketField>,
    checksumIndices: List<String>
): Long {
    val validTickets = computeTicketScanningErrorRate(ticketList, ranges).validTickets
    var indexToPossibleNames = buildMapOfPossibleFieldsPerIndex(validTickets, ranges)
    indexToPossibleNames = reducePossibleNamesBasedOnCertainOnes(indexToPossibleNames)
    return matchExactTicketAndComputeChecksum(indexToPossibleNames, myTicket, checksumIndices)
}

private fun buildMapOfPossibleFieldsPerIndex(
    validTickets: List<List<Int>>,
    ranges: Map<String, TicketField>
): MutableMap<Int, List<String>> {
    val fieldIndexToPossibleNames = mutableMapOf<Int, List<String>>()

    for (i in validTickets[0].indices) {
        val listOfValuesAtPositionX = validTickets.map { it[i] }
        val listOfValidFields = ranges.keys.toMutableList()
        for (value in listOfValuesAtPositionX) {
            if (listOfValidFields.size == 1) {
                break
            }
            for (range in ranges.values) {
                if (value !in range.range1.first..range.range1.second
                    && value !in range.range2.first..range.range2.second
                ) {
                    listOfValidFields.remove(range.name)
                    break
                }
            }
        }
        fieldIndexToPossibleNames[i] = listOfValidFields
    }
    return fieldIndexToPossibleNames
}

private fun reducePossibleNamesBasedOnCertainOnes(indexToPossibleNames: MutableMap<Int, List<String>>): MutableMap<Int, List<String>> {
    var reducedPossibleNames = indexToPossibleNames
    var mapCopy = reducedPossibleNames.toMutableMap()
    var sumOfVariableFields = reducedPossibleNames.values.sumBy { it.size }

    while (sumOfVariableFields > reducedPossibleNames.size) {
        val namesFoundList = reducedPossibleNames.values
            .filter { it.size == 1 }
            .map { it[0] }

        for (field in reducedPossibleNames.entries) {
            for (nameFound in namesFoundList) {
                if (field.value.size != 1 && field.value.contains(nameFound)) {
                    val list = mapCopy[field.key]!!.toMutableList()
                    list.remove(nameFound)
                    mapCopy[field.key] = list
                }
            }
        }
        reducedPossibleNames = mapCopy
        sumOfVariableFields = reducedPossibleNames.values.sumBy { it.size }
        mapCopy = reducedPossibleNames.toMutableMap()
    }
    return reducedPossibleNames
}

private fun matchExactTicketAndComputeChecksum(
    indexToPossibleNames: MutableMap<Int, List<String>>,
    myTicket: List<Int>,
    checksumIndices: List<String>
): Long {
    var departureValuesChecksum = 1L
    val orderOfValues = mutableMapOf<String, Long>()
    for (i in indexToPossibleNames.entries) {
        orderOfValues[i.value[0]] = myTicket[i.key].toLong()
    }
    for (field in checksumIndices) {
        departureValuesChecksum *= orderOfValues[field]!!
    }
    return departureValuesChecksum
}
