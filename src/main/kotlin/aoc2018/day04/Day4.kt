package aoc2018.day04

import org.apache.commons.lang3.StringUtils
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.*


fun readInputAndParse(fileName: String): List<GuardStatus> {
    val statusList: MutableList<GuardStatus> = ArrayList<GuardStatus>()
    val reader: BufferedReader
    try {
        reader = BufferedReader(FileReader(fileName))
        var line = reader.readLine()
        while (line != null) {
            val `val` = line.split(" ").toTypedArray()
            val date = LocalDateTime.of(
                `val`[0].toInt(), `val`[1].toInt(), `val`[2].toInt(), `val`[3].toInt(), `val`[4].toInt(),
                0
            )
            var status: GuardStatus
            if (StringUtils.isNumeric(`val`[5])) {
                status = GuardStatus(date, `val`[5].toInt())
            } else {
                status = GuardStatus(date, `val`[5])
            }
            statusList.add(status)
            line = reader.readLine()
        }
        reader.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return statusList
}

fun sortInputByDate(list: List<GuardStatus>): List<GuardStatus> {
    Collections.sort(list)
    return list
}

fun getGuardWithMostTimeAsleep(list: List<GuardStatus>): Int {
    val minutesAsleepMap: MutableMap<String, Int?> = HashMap()
    var guardId = 0
    for (i in list.indices) {
        val guardStatus: GuardStatus = list[i]
        if (guardStatus.id != 0) {
            guardId = guardStatus.id
        } else if (guardStatus.status.equals("awaken")) {
            val previous: GuardStatus = list[i - 1]
            val beginTime: LocalTime = previous.date.toLocalTime()
            val endTime: LocalTime = guardStatus.date.toLocalTime()
            val localDate: LocalDate = guardStatus.date.toLocalDate()
            val minuteDiff = beginTime.until(endTime, ChronoUnit.MINUTES).toInt()
            if (minutesAsleepMap["$guardId:$localDate"] != null) {
                val mins = minutesAsleepMap["$guardId:$localDate"]
                minutesAsleepMap["$guardId:$localDate"] = mins!! + minuteDiff
            } else {
                minutesAsleepMap["$guardId:$localDate"] = minuteDiff
            }
        }
    }
    val minutesSumPerGuard: MutableMap<String, Int> = HashMap()
    for ((key, value1) in minutesAsleepMap) {
        val id = key.split(":").toTypedArray()[0]
        val value = value1.toString()
        if (minutesSumPerGuard.containsKey(id)) {
            val prevSum = minutesSumPerGuard[id]!!
            minutesSumPerGuard[id] = prevSum + value.toInt()
        } else {
            minutesSumPerGuard[id] = value.toInt()
        }
    }
    var maxMins = 0
    for (value in minutesSumPerGuard.values) if (value > maxMins) maxMins = value
    val key = getKeyFromValue(minutesSumPerGuard, maxMins)
    return key?.toInt() ?: 0
}

fun <K, V> getKeyFromValue(map: Map<K, V>, value: V): K? {
    for ((key, value1) in map) if (value1 == value) return key
    return null
}

fun drawSleepPattern(id: Int, list: List<GuardStatus>): IntArray {
    val minutes = IntArray(60)
    for (i in list.indices) {
        val guardStatus: GuardStatus = list[i]
        if (guardStatus.id == id) {
            var j = i + 1
            while (j < list.size && list[j].id == 0) {
                val asleepTime: Int = list[j].date.toLocalTime().getMinute()
                val awakeTime: Int = list[j + 1].date.toLocalTime().getMinute()
                for (k in asleepTime until awakeTime) minutes[k] += 1
                j += 2
            }
        }
    }
    return minutes
}

fun buildSleepCountMatrix(list: List<GuardStatus>) {
    val idList: MutableList<Int> = ArrayList()
    for (status in list) {
        val id: Int = status.id
        if (id != 0 && !idList.contains(id)) idList.add(id)
    }
    val sleepMatrix = Array(61) { IntArray(idList.size) }
    for (i in 0..59) {
        for (j in idList.indices) {
            val minutes = drawSleepPattern(idList[j], list)
            sleepMatrix[i][j] += minutes[i]
            sleepMatrix[60][j] = idList[j].toString().toInt()
        }
    }
}

class GuardStatus : Comparable<GuardStatus?> {
    var date: LocalDateTime
    var id = 0
    var status: String? = null

    constructor(date: LocalDateTime, id: Int) {
        this.date = date
        this.id = id
    }

    constructor(date: LocalDateTime, status: String?) {
        this.date = date
        this.status = status
    }

    override operator fun compareTo(other: GuardStatus?): Int {
        val date = other!!.date
        return this.date.compareTo(date)
    }
}
