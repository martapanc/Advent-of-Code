package aoc2018.day22

import aoc2020.day20.Coord


class Day22(private val target: Coord, private val depth: Int) {

    private val erosionMap = mutableMapOf<Coord, Int>()
    private val entranceRegion = Coord(0, 0)

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
}

