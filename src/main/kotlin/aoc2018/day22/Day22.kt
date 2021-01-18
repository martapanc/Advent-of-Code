package aoc2018.day22

import aoc2020.day20.Coord
import java.util.*


class Day22(private val target: Coord, private val depth: Int) {

    private val erosionMap = mutableMapOf<Coord, Int>()
    private val entranceRegion = Coord(0, 0)

    fun computeCheapestPath(): Traversal? {
        val minCostSeen: MutableMap<Pair<Coord, Tool>, Int> = mutableMapOf(entranceRegion to Tool.Torch to 0)
        val candidatePaths = PriorityQueue<Traversal>().apply {
            add(Traversal(entranceRegion, Tool.Torch))
        }

        while (candidatePaths.isNotEmpty()) {
            val currentPath = candidatePaths.poll()
            if (currentPath.target == target && currentPath.toolHeld == Tool.Torch) {
                return currentPath
            }

            val nextSteps = mutableListOf<Traversal>()
            currentPath.target.neighbors().forEach { neighbor ->
                if (currentPath.toolHeld in neighbor.validTools()) {
                    nextSteps += currentPath.copy(target = neighbor, cost = currentPath.cost + 1)
                }
            }

            currentPath.target.validTools().minus(currentPath.toolHeld).forEach { tool ->
                nextSteps += Traversal(target = currentPath.target, toolHeld = tool, cost = currentPath.cost + 7)
            }

            nextSteps.forEach { step ->
                val key = Pair(step.target, step.toolHeld)
                if (key !in minCostSeen || minCostSeen.getValue(key) > step.cost) {
                    candidatePaths += step
                    minCostSeen[key] = step.cost
                }
            }
        }
        return null
    }

    fun computeTotalRiskLevel(): Int {
        var totalRiskLevel = 0
        for (y in entranceRegion.y..target.y) {
            for (x in entranceRegion.x..target.x) {
                val region = Coord(x, y)
                val erosion = computeRegionErosionLevel(region)
                totalRiskLevel += computeRegionType(erosion)
            }
        }
        return totalRiskLevel
    }

    fun computeRegionGeologicIndex(region: Coord): Int {
        if (region == Coord(0, 0) || region == target) return 0
        if (region.x == 0) return region.y * 48271
        if (region.y == 0) return region.x * 16807

        val region1 = Coord(region.x - 1, region.y)
        val erosionValue1 = if (erosionMap.containsKey(region1)) erosionMap[region1]
        else computeRegionErosionLevel(region1)
        erosionMap[region1] = erosionValue1!!

        val region2 = Coord(region.x, region.y - 1)
        val erosionValue2 = if (erosionMap.containsKey(region2)) erosionMap[region2]
        else computeRegionErosionLevel(region2)
        erosionMap[region2] = erosionValue2!!

        return erosionValue1 * erosionValue2
    }

    fun computeRegionErosionLevel(region: Coord): Int {
        return (computeRegionGeologicIndex(region) + depth) % 20183
    }

    private fun computeRegionType(erosion: Int): Int {
        return erosion % 3
    }

    data class Traversal(val target: Coord, val toolHeld: Tool, val cost: Int = 0) : Comparable<Traversal> {
        override fun compareTo(other: Traversal): Int {
            return this.cost.compareTo(other.cost)
        }
    }

    enum class Tool { Torch, ClimbingGear, Neither }
    private enum class Terrain(val terrainValue: Int, val tools: Set<Tool>) {
        Rocky(0, setOf(Tool.ClimbingGear, Tool.Torch)),
        Wet(1, setOf(Tool.ClimbingGear, Tool.Neither)),
        Narrow(2, setOf(Tool.Torch, Tool.Neither));

        companion object {
            val toolValues = arrayOf(Rocky, Wet, Narrow)
            fun getByErosionLevel(erosionLevel: Int): Terrain = toolValues.first { it.terrainValue == erosionLevel % 3 }
        }
    }

    private fun Coord.validTools(): Set<Tool> =
        when (this) {
            entranceRegion -> setOf(Tool.Torch)
            target -> setOf(Tool.Torch)
            else -> Terrain.getByErosionLevel(computeRegionErosionLevel(this)).tools
        }
}

