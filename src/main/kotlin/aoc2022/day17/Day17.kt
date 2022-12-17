package aoc2022.day17

import util.Coord
import util.readInputLineByLine

// ⌃ y
// |
// |
//-+-----> x

fun readInputToJetStreams(path: String): List<JetStream> =
    readInputLineByLine(path)[0].toCharArray().map { JetStream.fromSymbol(it)!! }

fun part1(jetStreams: List<JetStream>, rounds: Int = 2022): Int {
    return solve(jetStreams, rounds)
}

fun part2(jetStreams: List<JetStream>, rounds: Int): Int {
    return solve(jetStreams, rounds)
}

private fun solve(jetStreams: List<JetStream>, rounds: Int): Int {
    val map = mutableMapOf<Coord, Char>()
    var streamIndex = 0
    repeat(rounds) { index ->
        val highestRockCoord = (if (map.filter { it.value == '#' }.isNotEmpty())
            map.filter { it.value == '#' }.maxBy { it.key.y }.key.y else -1) + 4 // 3 units above bottom
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
    }
//    printTetrisGrid(map)
    return map.filter { it.value == '#' }.maxBy { it.key.y }.key.y + 1
}

fun List<Coord>.isPushed(direction: JetStream, map: Map<Coord, Char>): List<Coord> {
    if (direction == JetStream.LEFT && this.minBy { it.x }.x > 0 ||
        direction == JetStream.RIGHT && this.maxBy { it.x }.x < 6) {
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
        return listOf(Coord(2, initY), Coord(3, initY), Coord(4, initY), Coord(5, initY))
    }

    fun plus(): List<Coord> = listOf(Coord(3, initY + 2),
        Coord(2, initY + 1), Coord(3, initY + 1),  Coord(4, initY + 1),
                                   Coord(3, initY))

    fun l(): List<Coord> = listOf(Coord(4, initY + 2),
                                  Coord(4, initY + 1),
        Coord(2, initY), Coord(3, initY), Coord(4, initY),
    )

    fun i(): List<Coord> = listOf(
        Coord(2, initY + 3),
        Coord(2, initY + 2),
        Coord(2, initY + 1),
        Coord(2, initY),
    )

   fun square(): List<Coord> = listOf(
       Coord(2, initY + 1), Coord(3, initY + 1),
       Coord(2, initY), Coord(3, initY),
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
    val hiX = 7
    val loY = map.keys.minByOrNull { it.y }!!.y - 1
    val hiY = map.keys.maxByOrNull { it.y }!!.y + 1
    (loY .. hiY).forEach { y ->
        printMap[Coord(-1, y)] = '|'
        printMap[Coord(7, y)] = '|'
    }
    (loX .. hiX).forEach { x ->
        printMap[Coord(x, -1)] = '-'
    }
    printMap[Coord(-1, -1)] = '+'
    printMap[Coord(7, -1)] = '+'
    (hiY downTo loY).forEach { y ->
        (loX .. hiX).forEach { x ->
            print(printMap[Coord(x, y)] ?: '.')
        }
        println()
    }
}
