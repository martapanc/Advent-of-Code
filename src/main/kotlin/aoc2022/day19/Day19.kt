package aoc2022.day19

import util.readInputLineByLine

fun readInputToBlueprint(path: String): List<Blueprint> {
    return readInputLineByLine(path).map { Blueprint.fromInputString(it) }
}

data class MiningWork(
    val ore: Int = 0,
    val clay: Int = 0,
    val obsidian: Int = 0,
    val geode: Int = 0,

    val oreRobots: Int = 1,
    val clayRobots: Int = 0,
    val obsidianRobots: Int = 0,
    val geodeRobots: Int = 0
) {
    private fun possibleNextStates(blueprint: Blueprint, canBuyNext: Int): List<Pair<MiningWork, Int>> {
        val oreRobotCost = blueprint.oreRobotCost
        val obsidianRobotCost = blueprint.obsidianRobotCost
        val geodeRobotCost = blueprint.geodeRobotCost
        val clayRobotCost = blueprint.clayRobotCost

        val maxOreCost = listOf(oreRobotCost, clayRobotCost, obsidianRobotCost.oreQnt, geodeRobotCost.oreQnt).max()
        val maxClayCost = obsidianRobotCost.clayQnt
        val maxObsidianCost = geodeRobotCost.obsidianQnt

        if (ore / geodeRobotCost.oreQnt > 0 && obsidian / geodeRobotCost.obsidianQnt > 0) {
            return listOf(build(blueprint, Robot.GEODE) to 15)
        }

        val possibleBuilds = mutableListOf<Robot>()
        if (maxObsidianCost > obsidianRobots && canBuyNext and Robot.OBSIDIAN.bit != 0 &&
            ore / obsidianRobotCost.oreQnt > 0 && clay / obsidianRobotCost.clayQnt > 0
        ) {
            possibleBuilds += Robot.OBSIDIAN
        }
        if (maxClayCost > clayRobots && ore / clayRobotCost > 0 && canBuyNext and Robot.CLAY.bit != 0) {
            possibleBuilds += Robot.CLAY
        }
        if (maxOreCost > oreRobots && ore / oreRobotCost > 0 && canBuyNext and Robot.ORE.bit != 0) {
            possibleBuilds += Robot.ORE
        }

        val stopBuying = possibleBuilds.fold(canBuyNext) { current, acc -> acc.bit xor current }

        return listOf(build(blueprint) to stopBuying) + possibleBuilds.map { build(blueprint, it) to 15 }
    }

    private fun build(blueprint: Blueprint, robot: Robot? = null): MiningWork = with(mine()) {
        when (robot) {
            null -> this
            Robot.ORE -> copy(
                oreRobots = oreRobots + 1,
                ore = ore - blueprint.oreRobotCost
            )

            Robot.CLAY -> copy(
                clayRobots = clayRobots + 1,
                ore = ore - blueprint.clayRobotCost
            )

            Robot.OBSIDIAN -> copy(
                obsidianRobots = obsidianRobots + 1,
                ore = ore - blueprint.obsidianRobotCost.oreQnt,
                clay = clay - blueprint.obsidianRobotCost.clayQnt
            )

            Robot.GEODE -> copy(
                geodeRobots = geodeRobots + 1,
                ore = ore - blueprint.geodeRobotCost.oreQnt,
                obsidian = obsidian - blueprint.geodeRobotCost.obsidianQnt
            )
        }
    }

    private fun mine() = copy(
        ore = ore + oreRobots,
        clay = clay + clayRobots,
        obsidian = obsidian + obsidianRobots,
        geode = geode + geodeRobots
    )

    fun maxMinedGeodesIn(minutes: Int, blueprint: Blueprint, canBuyNext: Int = 15): Int {
        if (minutes == 0) return geode
        val nextStates = possibleNextStates(blueprint, canBuyNext)
        return nextStates.maxOf { (it, canBuy) -> it.maxMinedGeodesIn(minutes - 1, blueprint, canBuy) }
    }
}

fun part1(blueprints: List<Blueprint>): Int {
    return blueprints.fold(0) { acc, blueprint ->
        val currResult = MiningWork().maxMinedGeodesIn(24, blueprint)
        blueprint.id * currResult + acc
    }
}

fun part2(blueprints: List<Blueprint>): Long {
    return blueprints.take(3).fold(1L) { acc, blueprint ->
        acc * MiningWork().maxMinedGeodesIn(32, blueprint)
    }
}

data class Blueprint(
    val id: Int, val oreRobotCost: Int, val clayRobotCost: Int, val obsidianRobotCost: ObsidianRobotCost,
    val geodeRobotCost: GeodeRobotCost
) {

    companion object {
        fun fromInputString(input: String): Blueprint {
            val split = Regex("\\d+").findAll(input).map { it.value.toInt() }.toList()
            return Blueprint(
                split[0], split[1], split[2], ObsidianRobotCost(split[3], split[4]), GeodeRobotCost(split[5], split[6])
            )
        }
    }

}

data class ObsidianRobotCost(val oreQnt: Int, val clayQnt: Int)
data class GeodeRobotCost(val oreQnt: Int, val obsidianQnt: Int)

enum class Robot(val bit: Int) { ORE(1), CLAY(2), OBSIDIAN(4), GEODE(8) }
