package aoc2022.day19

import util.readInputLineByLine

fun readInputToBlueprint(path: String): List<Blueprint> {
    return readInputLineByLine(path).map { Blueprint.fromInputString(it) }
}

fun part1(blueprints: List<Blueprint>): Int {
    val blueprintResult = mutableMapOf<Int, Int>()
    blueprints.forEach { blueprint ->
        val ore = Quantity(Material.ORE, 0)
        val clay = Quantity(Material.CLAY, 0)
        val obsidian = Quantity(Material.OBSIDIAN, 0)
        val geode = Quantity(Material.GEODE, 0)

        val oreRobot = Quantity(Material.ORE, 1)
        val clayRobot = Quantity(Material.CLAY, 0)
        val obsidianRobot = Quantity(Material.OBSIDIAN, 0)
        val geodeRobot = Quantity(Material.GEODE, 0)

        repeat(24) {
            var oreRobotBeingBuilt = false
            var clayRobotBeingBuilt = false
            var obsidianRobotBeingBuilt = false
            var geodeRobotBeingBuilt = false
            if (ore.qnt > 0 && obsidian.qnt > 0 &&
                blueprint.geodeRobotCost.oreCost.costPerUnit <= ore.qnt &&
                blueprint.geodeRobotCost.obsidianCost.costPerUnit <= obsidian.qnt) {
                geodeRobot.qnt++
                ore.qnt -= blueprint.geodeRobotCost.oreCost.costPerUnit
                obsidian.qnt -= blueprint.geodeRobotCost.obsidianCost.costPerUnit
                geodeRobotBeingBuilt = true
            }
            if (ore.qnt > 0 && clay.qnt > 0 &&
                blueprint.obsidianRobotCost.oreCost.costPerUnit <= ore.qnt &&
                blueprint.obsidianRobotCost.clayCost.costPerUnit <= clay.qnt) {
                obsidianRobot.qnt++
                ore.qnt -= blueprint.obsidianRobotCost.oreCost.costPerUnit
                clay.qnt -= blueprint.obsidianRobotCost.clayCost.costPerUnit
                obsidianRobotBeingBuilt = true
            }
            if (ore.qnt > 0 && blueprint.clayRobotCost.costPerUnit <= ore.qnt) {
                clayRobot.qnt++
                ore.qnt -= blueprint.clayRobotCost.costPerUnit
                clayRobotBeingBuilt = true
            }
            if (ore.qnt > 0 && blueprint.oreRobotCost.costPerUnit <= ore.qnt) {
                oreRobot.qnt++
                ore.qnt -= blueprint.oreRobotCost.costPerUnit
                oreRobotBeingBuilt = true
            }

            ore.qnt += if (oreRobotBeingBuilt) oreRobot.qnt - 1 else oreRobot.qnt
            clay.qnt += if (clayRobotBeingBuilt) clayRobot.qnt - 1 else clayRobot.qnt
            obsidian.qnt += if (obsidianRobotBeingBuilt) obsidianRobot.qnt - 1 else obsidianRobot.qnt
            geode.qnt += if (geodeRobotBeingBuilt) geodeRobot.qnt - 1 else geodeRobot.qnt
        }

        blueprintResult[blueprint.id] = geode.qnt
    }

    return blueprintResult.keys.sum() + blueprintResult.values.sum()
}

fun part2(blueprints: List<Blueprint>): Int {
    return 0
}

data class Blueprint(
    val id: Int,
    val oreRobotCost: Cost,
    val clayRobotCost: Cost,
    val obsidianRobotCost: ObsidianRobotCost,
    val geodeRobotCost: GeodeRobotCost) {

    companion object {
        fun fromInputString(input: String): Blueprint {
            val split = Regex("\\d+").findAll(input).map { it.value.toInt() }.toList()
            return Blueprint(split[0], Cost(split[1], Material.ORE), Cost(split[2], Material.ORE),
                ObsidianRobotCost(Cost(split[3], Material.ORE), Cost(split[4], Material.CLAY)),
                GeodeRobotCost(Cost(split[5], Material.ORE), Cost(split[6], Material.OBSIDIAN))
            )
        }
    }

}

data class Cost(val costPerUnit: Int, val material: Material)
data class Quantity(val material: Material, var qnt: Int)
data class ObsidianRobotCost(val oreCost: Cost, val clayCost: Cost)
data class GeodeRobotCost(val oreCost: Cost, val obsidianCost: Cost)

enum class Material { ORE, CLAY, OBSIDIAN, GEODE }
