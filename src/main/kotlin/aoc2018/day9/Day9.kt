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

fun main() {
    val d = Day09(10, 1618)
    println(d.solvePart1())
}
class Day09(private val players: Int, private val highest: Int) {

    fun solvePart1(): Long =
        play(players, highest)

    fun solvePart2(): Long =
        play(players, highest * 100)

    private fun play(numPlayers: Int, highest: Int): Long {
        val scores = LongArray(numPlayers)
        val marbles = LinkedList<Int>().also { it.add(0) }

        (1..highest).forEach { marble ->
            when {
                marble % 23 == 0 -> {
                    scores[marble % numPlayers] += marble + with(marbles) {
                        shift(-7)
                        removeFirst().toLong()
                    }
                    marbles.shift(1)
                    println(marbles.reversed())
                }
                else -> {
                    with(marbles) {
                        shift(1)
                        addFirst(marble)
                    }
                }
            }
        }
        return scores.max()!!
    }

    private fun <T> LinkedList<T>.shift(n: Int): Unit =
        when {
            n < 0 -> repeat(n.absoluteValue) {
                addLast(removeFirst())
            }
            else -> repeat(n) {
                addFirst(removeLast())
            }
        }
}