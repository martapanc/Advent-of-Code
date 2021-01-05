fun main(args: Array<String>) {
    val answer1 = simulate().first
    println("Answer 1: $answer1")

    var boost = 0
    while (true) {
        val (unitsLeft, winner) = simulate(++boost)
        if (winner == "Immune System") {
            println("Answer 2: $unitsLeft")
            break
        }
    }
}

fun simulate(boost: Int = 0): Pair<Int, String> {
    val regex = Regex("(\\d+) units each with (\\d+) hit points( \\([a-z ,;]+\\))?" +
            " with an attack that does (\\d+) ([a-z]+) damage at initiative (\\d+)")

    val input = Helper.getResourceAsStream("input")
        .bufferedReader()
        .useLines { lines ->

            var army = ""
            lines.mapNotNull { line ->
                when {
                    line.isBlank() -> null
                    line.endsWith(":") -> {
                        army = line.substring(0, line.length - 1)
                        null
                    }
                    else -> {
                        val matchResult = regex.matchEntire(line) ?: throw RuntimeException()

                        var weaknesses = setOf<String>()
                        var immunities = setOf<String>()
                        val s = matchResult.groupValues[3]
                        if (s.isNotEmpty()) {
                            s.trim(' ', '(', ')')
                                .split("; ")
                                .forEach {
                                    if (it.startsWith("weak to ")) {
                                        weaknesses = it.substring(8).split(", ").toSet()
                                    } else if (it.startsWith("immune to ")) {
                                        immunities = it.substring(10).split(", ").toSet()
                                    }
                                }
                        }

                        Group(
                            army,
                            matchResult.groupValues[1].toInt(),
                            matchResult.groupValues[2].toInt(),
                            weaknesses,
                            immunities,
                            matchResult.groupValues[4].toInt() + if ("Immune System" == army) boost else 0,
                            matchResult.groupValues[5],
                            matchResult.groupValues[6].toInt()
                        )
                    }
                }
            }.toMutableList()
        }

    while (input.map { it.army }.distinct().count() > 1) {
        val targets = mutableMapOf<Int, Int>()

        // target selection
        input
            .sortedWith(compareBy({ -it.unitCount * it.attackDamage }, { -it.initiative }))
            .forEach {
                val index = input.indexOf(it)
                input.filter { other -> other.army != it.army }
                    .filter { other -> input.indexOf(other) !in targets.values }
                    .filter { other -> it.attackType !in other.immunities }
                    .sortedWith(
                        compareBy(
                            { other ->
                                val normalDamage = it.unitCount * it.attackDamage
                                val actualDamage = when (it.attackType) {
                                    in other.weaknesses -> 2 * normalDamage
                                    in other.immunities -> throw RuntimeException()
                                    else -> normalDamage
                                }
                                -actualDamage
                            },
                            { other -> -other.unitCount * other.attackDamage },
                            { other -> -other.initiative }
                        )
                    )
                    .firstOrNull()
                    ?.let { other ->
                        targets[index] = input.indexOf(other)
                    }
            }

        // attacking
        var anyUnitsKilled = false
        input.sortedBy { -it.initiative }
            .forEach {
                val index = input.indexOf(it)
                if (it.unitCount <= 0) {
                    return@forEach
                }

                val targetIndex = targets[index] ?: return@forEach
                val target = input[targetIndex]

                val normalDamage = it.unitCount * it.attackDamage
                val actualDamage = when (it.attackType) {
                    in target.weaknesses -> 2 * normalDamage
                    in target.immunities -> throw RuntimeException()
                    else -> normalDamage
                }

                val unitsKilled = actualDamage / target.unitHp
                if (unitsKilled > 0) {
                    target.unitCount -= unitsKilled
                    anyUnitsKilled = true
                }
            }

        if (!anyUnitsKilled) {
            return -1 to ""
        }

        input.removeAll { it.unitCount <= 0 }
    }

    return (input.sumBy { it.unitCount }) to input.first().army
}

data class Group(
    val army: String,
    var unitCount: Int,
    val unitHp: Int,
    val weaknesses: Set<String>,
    val immunities: Set<String>,
    val attackDamage: Int,
    val attackType: String,
    val initiative: Int
)

object Helper {
    fun getResourceAsStream(name: String) = javaClass.getResourceAsStream(name)
        ?: throw IllegalArgumentException("Resource not found: $name")
}