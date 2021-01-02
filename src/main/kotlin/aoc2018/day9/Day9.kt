package aoc2018.day9

import com.ginsberg.cirkle.circular
import java.util.*
import kotlin.math.absoluteValue

fun playMarbleGame(highestMarble: Int, players: Int): Int? {
    val scoreMap = mutableMapOf<Int, Int>()
    val marbleList = mutableListOf(0, 1).circular()
    var currentMarble = 1
    var nextMarble = 2
    for (i in 1..players) scoreMap[i] = 0
    while (currentMarble < highestMarble) {
        if (nextMarble % 23 == 0) {
            val playerNum = if (nextMarble % players == 0) players else nextMarble % players
            var marbleIndex = marbleList.indexOf(currentMarble) - 7
            scoreMap[playerNum] = scoreMap[playerNum]!!.plus(nextMarble).plus(marbleList.removeAt(marbleIndex))
            nextMarble++
            if (marbleIndex < 0) {
                marbleIndex++
            }
            currentMarble = marbleList[marbleIndex]
        } else {
            marbleList.add(marbleList.indexOf(currentMarble) + 2, nextMarble)
            if (marbleList[0] != 0) {
                marbleList.add(marbleList[0])
                marbleList.removeAt(0)
            }
            currentMarble = nextMarble
            nextMarble = currentMarble + 1
        }
    }
    return scoreMap.values.maxOrNull()
}

fun playMarbleGameV2(highestMarble: Int, players: Int): Long? {
    val scoreMap = LongArray(players)
    val marbleList = LinkedList<Int>().also { it.add(0) }

    for (marble in 1..highestMarble) {
        if (marble % 23 == 0) {
            scoreMap[marble % players] += marble + with(marbleList) {
                shift(-7)
                removeFirst().toLong()
            }
            marbleList.shift(1)
        } else {
            with(marbleList) {
                shift(1)
                addFirst(marble)
            }
        }
    }
    return scoreMap.maxOrNull()
}

private fun <T> LinkedList<T>.shift(n: Int) {
    if (n < 0) repeat(n.absoluteValue) {
        addLast(removeFirst())
    } else repeat(n) {
        addFirst(removeLast())
    }
}
