package aoc2018.day15

import java.util.*

typealias Caves = Array<CharArray>
typealias Path = List<GameCoord>

class Day15Part2(private val input: List<String>) {

    private var caves: Caves = parseCaves()
    private var fighters: List<Fighter> = Fighter.findFighters(caves)

    fun playGamePart2(): Int =
        generateSequence(4, Int::inc).map { ap ->
            caves = parseCaves()
            fighters = Fighter.findFighters(caves)

            val result = goFightGoblins(ap)
            if (result.second.filter { it.team == Team.Elf }.none { it.dead }) {
                result.second.filterNot { it.dead }.sumBy { it.hp } * result.first
            } else {
                null
            }
        }.filterNotNull().first()

    private fun goFightGoblins(elfAttackPoints: Int = 3): Pair<Int, List<Fighter>> {
        fighters.filter { it.team == Team.Elf }.forEach { it.ap = elfAttackPoints }
        var rounds = 0
        while (round()) {
            rounds++
        }
        return Pair(rounds, fighters)
    }

    private fun round(): Boolean {
        fighters.sorted().asSequence().filterNot { it.dead }.forEach { fighter ->
            if (!keepFighting()) {
                return false
            }
            var target: Fighter? = fighter.inRange(fighters).firstOrNull()
            if (target == null) {
                val path = fighter.findPathToBestEnemyAdjacentSpot(fighters, caves)
                if (path.isNotEmpty()) {
                    fighter.moveTo(path.first(), caves)
                }

                target = fighter.inRange(fighters).firstOrNull()
            }

            target?.let { fighter.attack(it, caves) }
        }
        return true
    }

    private fun keepFighting(): Boolean =
        fighters.filterNot { it.dead }.distinctBy { it.team }.count() > 1

    private fun parseCaves(): Caves =
        input.map { it.toCharArray() }.toTypedArray()
}

private enum class Team(val logo: Char) {
    Elf('E'),
    Goblin('G');

    companion object {
        fun byLogo(logo: Char): Team? =
            values().firstOrNull { logo == it.logo }
    }
}

private data class Fighter(
    val team: Team,
    var location: GameCoord,
    var hp: Int = 200,
    var ap: Int = 3,
    var dead: Boolean = false
) : Comparable<Fighter> {

    fun inRange(others: List<Fighter>): List<Fighter> =
        others.filter {
            it != this &&
                    !this.dead &&
                    !it.dead &&
                    it.team != this.team &&
                    this.location.distanceTo(it.location) == 1
        }
            .sortedWith(compareBy({ it.hp }, { it.location }))

    fun attack(target: Fighter, caves: Caves) {
        target.hp -= this.ap
        if (target.hp <= 0) {
            target.dead = true
            caves[target.location.y][target.location.x] = '.'
        }
    }

    fun moveTo(place: GameCoord, caves: Caves) {
        caves[location.y][location.x] = '.'
        location = place
        caves[location.y][location.x] = team.logo
    }

    fun findPathToBestEnemyAdjacentSpot(fighters: List<Fighter>, caves: Caves): Path =
        pathToAnyEnemy(enemyAdjacentOpenSpots(fighters, caves), caves)

    private fun enemyAdjacentOpenSpots(fighters: List<Fighter>, caves: Caves): Set<GameCoord> =
        fighters
            .filterNot { it.dead }
            .filterNot { it.team == team }
            .flatMap { it.location.cardinalNeighbors().filter { neighbor -> caves[neighbor.y][neighbor.x] == '.' } }
            .toSet()

    private fun pathToAnyEnemy(enemies: Set<GameCoord>, caves: Caves): Path {
        val seen: MutableSet<GameCoord> = mutableSetOf(location)
        val paths: Deque<Path> = ArrayDeque()

        location.cardinalNeighbors()
            .filter { caves[it.y][it.x] == '.' }
            .forEach { paths.add(listOf(it)) }

        while (paths.isNotEmpty()) {
            val path: Path = paths.removeFirst()
            val pathEnd: GameCoord = path.last()

            if (pathEnd in enemies) {
                return path
            }

            if (pathEnd !in seen) {
                seen.add(pathEnd)
                pathEnd.cardinalNeighbors()
                    .filter { caves[it.y][it.x] == '.' }
                    .filterNot { it in seen }
                    .forEach { paths.add(path + it) }
            }
        }
        return emptyList()
    }

    override fun compareTo(other: Fighter): Int =
        location.compareTo(other.location)

    companion object {
        fun findFighters(caves: Caves): List<Fighter> =
            caves.mapIndexed { y, row ->
                row.mapIndexed { x, spot ->
                    Team.byLogo(spot)?.let {
                        Fighter(it, GameCoord(x, y))
                    }
                }
            }.flatten().filterNotNull()
    }
}
