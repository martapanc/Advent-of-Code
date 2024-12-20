package aoc2022.day17

import util.Coord
import util.readInputLineByLine

// ⌃ y
// |
// |
//-+-----> x
const val BOARD_WIDTH = 7
const val INIT_X = 2
const val HEIGHT = 4

fun readInputToJetStreams(path: String): List<JetStream> =
    readInputLineByLine(path)[0].toCharArray().map { JetStream.fromSymbol(it)!! }

fun part1(jetStreams: List<JetStream>, rounds: Int = 2022): Int = solve(jetStreams, rounds)

fun part2(jetStreams: List<JetStream>): Long {
    val fullRounds = 1000000000000
    val roundPeriod = 1705
    val outputPeriod = 2597
    val div = fullRounds / roundPeriod
    val mod = (fullRounds % roundPeriod).toInt()
    return solve(jetStreams, mod) + div * outputPeriod
}

fun solve(jetStreams: List<JetStream>, rounds: Int): Int {
    val map = mutableMapOf<Coord, Char>()
    val printMap = map.toMutableMap()
    var streamIndex = 0
    repeat(rounds) { index ->
        val highestRockCoord = (if (map.filter { it.value == '#' }.isNotEmpty())
            map.filter { it.value == '#' }.maxBy { it.key.y }.key.y else -1) + HEIGHT // 3 units above bottom
        var currentRockPos = getRockType(index, Rock(highestRockCoord))
        var newPos = isPushedAndFalls(currentRockPos, jetStreams[streamIndex++ % jetStreams.size], map)
        rockRound@ while (currentRockPos != newPos) {
            currentRockPos = newPos
            val pushedPos = currentRockPos.isPushed(jetStreams[streamIndex++ % jetStreams.size], map)
            newPos = pushedPos.falls(map)
            if (pushedPos == newPos) {
                break@rockRound
            }
        }
        newPos.forEach { map[it] = '#' }
        val char = when (index % 5) {
            0 -> 'b'
            1 -> 'p'
            2 -> 'l'
            3 -> 'r'
            else -> 's'
        }
        newPos.forEach { printMap[it] = char }
    }
//    printTetrisGrid(printMap)
    return map.filter { it.value == '#' }.maxBy { it.key.y }.key.y + 1
}

fun List<Coord>.isPushed(direction: JetStream, map: Map<Coord, Char>): List<Coord> {
    if (direction == JetStream.LEFT && this.minBy { it.x }.x > 0 ||
        direction == JetStream.RIGHT && this.maxBy { it.x }.x < (BOARD_WIDTH - 1)) {
        val movedPos = this.move(direction)
        if (movedPos.none { map[it] == '#' }) {
            return movedPos
        }
    }
    return this
}

fun List<Coord>.falls(map: Map<Coord, Char>): List<Coord> {
    val downwardMoveCoords = this.moveDown()
    if (downwardMoveCoords.any { map[it] == '#' } || downwardMoveCoords.any { it.y == -1 }) {
        return this
    }
    return downwardMoveCoords
}

fun isPushedAndFalls(initCoords: List<Coord>, direction: JetStream, map: Map<Coord, Char>): List<Coord> =
    initCoords.isPushed(direction, map).falls(map)

fun List<Coord>.move(jetStream: JetStream): List<Coord> {
    if (jetStream == JetStream.RIGHT) {
        return this.map { Coord(it.x + 1, it.y) }
    }
    return this.map { Coord(it.x - 1, it.y) }
}

fun List<Coord>.moveDown(): List<Coord> = this.map { Coord(it.x, it.y - 1) }

private fun getRockType(index: Int, rock: Rock) = when (index % 5) {
    0 -> rock.bar()
    1 -> rock.plus()
    2 -> rock.l()
    3 -> rock.i()
    else -> rock.square()
}

data class Rock(val initY: Int) {

    fun bar(): List<Coord> {
        return listOf(Coord(INIT_X, initY), Coord(INIT_X + 1, initY), Coord(INIT_X + 2, initY), Coord(INIT_X + 3, initY))
    }

    fun plus(): List<Coord> = listOf(Coord(INIT_X + 1, initY + 2),
        Coord(INIT_X, initY + 1), Coord(INIT_X + 1, initY + 1),  Coord(INIT_X + 2, initY + 1),
                                   Coord(INIT_X + 1, initY))

    fun l(): List<Coord> = listOf(Coord(INIT_X + 2, initY + 2),
                                  Coord(INIT_X + 2, initY + 1),
        Coord(INIT_X, initY), Coord(INIT_X + 1, initY), Coord(INIT_X + 2, initY),
    )

    fun i(): List<Coord> = listOf(
        Coord(INIT_X, initY + 3),
        Coord(INIT_X, initY + 2),
        Coord(INIT_X, initY + 1),
        Coord(INIT_X, initY),
    )

   fun square(): List<Coord> = listOf(
       Coord(INIT_X, initY + 1), Coord(INIT_X + 1, initY + 1),
       Coord(INIT_X, initY), Coord(INIT_X + 1, initY),
   )
}

enum class JetStream(val symbol: Char) {
    RIGHT('>'), LEFT('<');

    companion object {
        fun fromSymbol(symbol: Char): JetStream? = values().firstOrNull() { symbol == it.symbol }
    }
}

fun printTetrisGrid(map: Map<Coord, Char>) {
    val printMap = map.toMutableMap()
    val loX = -1
    val hiX = BOARD_WIDTH
    val loY = map.keys.minByOrNull { it.y }!!.y - 1
    val hiY = map.keys.maxByOrNull { it.y }!!.y + 1
    (loY .. hiY).forEach { y ->
        printMap[Coord(-1, y)] = '|'
        printMap[Coord(BOARD_WIDTH, y)] = '|'
    }
    (loX .. hiX).forEach { x ->
        printMap[Coord(x, -1)] = '-'
    }
    printMap[Coord(-1, -1)] = '+'
    printMap[Coord(BOARD_WIDTH, -1)] = '+'
    (hiY downTo loY).forEach { y ->
        (loX .. hiX).forEach { x ->
            print(printMap[Coord(x, y)] ?: '.')
        }
        println()
    }
}
