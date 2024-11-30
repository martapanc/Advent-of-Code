package aoc2016.day17

import util.Coord
import java.math.BigInteger
import java.security.MessageDigest

private val startCoord = Coord(0, 0)
private val vaultCoord = Coord(3, 3)

fun navigateVault(passcode: String): String {
    var (vaultMap, deltaCoords, edge) = initialize(passcode)
    val edgeToPassword = mutableMapOf(startCoord to passcode)

    while (!edgeToPassword.keys.contains(vaultCoord)) {
        val newEdge = mutableListOf<VaultRoom>()
        for (current in edge) {
            val hex = md5(current.passcode)
            for ((index, dir) in listOf('U', 'D', 'L', 'R').withIndex()) {
                val neighborCell = Coord(current.coord.x + deltaCoords[index].x, current.coord.y + deltaCoords[index].y)
                if ("bcdef".contains(hex[index]) && vaultMap.contains(neighborCell)) {
                    edgeToPassword[neighborCell] = current.passcode + dir
                    newEdge.add(VaultRoom(neighborCell, current.passcode + dir))
                }
            }
        }
        edge = newEdge
    }
    return edgeToPassword[vaultCoord]!!.replace(passcode, "")
}

fun findLongestPathToVault(passcode: String): Int {
    var (vaultMap, deltaCoords, edge) = initialize(passcode)
    var longestPathLength = 0

    while (edge.isNotEmpty()) {
        val newEdge = mutableListOf<VaultRoom>()
        for (current in edge) {
            val hex = md5(current.passcode)
            for ((index, dir) in listOf('U', 'D', 'L', 'R').withIndex()) {
                val neighborCell = Coord(current.coord.x + deltaCoords[index].x, current.coord.y + deltaCoords[index].y)
                // Update edge only if the neighbor cell is not the target, so that the path search can continue
                if (neighborCell != vaultCoord && "bcdef".contains(hex[index]) && vaultMap.contains(neighborCell)) {
                    newEdge.add(VaultRoom(neighborCell, current.passcode + dir))
                }
                // If the neighbor cell is the target and can be reached, it's the end of a path
                if (neighborCell == vaultCoord && "bcdef".contains(hex[index])) {
                    val path = (current.passcode + dir).replace(passcode, "")
                    if (path.length > longestPathLength) {
                        longestPathLength = path.length
                    }
                }
            }
        }
        edge = newEdge
    }
    return longestPathLength
}

private fun initialize(passcode: String): Triple<MutableList<Coord>, List<Coord>, List<VaultRoom>> {
    val vaultMap = mutableListOf<Coord>()
    for (y in 0..3) for (x in 0..3) vaultMap.add(Coord(x, y))
    val deltaCoords = listOf(Coord(0, -1), Coord(0, 1), Coord(-1, 0), Coord(1, 0))
    val edge = mutableListOf(VaultRoom(startCoord, passcode))
    return Triple(vaultMap, deltaCoords, edge)
}

data class VaultRoom(val coord: Coord, val passcode: String)

fun md5(input: String): String {
    val md = MessageDigest.getInstance("md5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0').substring(0, 4)
}
