package aoc2016.day17

import aoc2020.day20.Coord
import java.math.BigInteger
import java.security.MessageDigest

fun navigateVault(passcode: String): String {
    val vaultMap = mutableListOf<Coord>()
    for (y in 0..3) for (x in 0..3) vaultMap.add(Coord(x, y))
    val deltaCoords = listOf(Coord(0, -1), Coord(0, 1), Coord(-1, 0), Coord(1, 0))

    var edge = mutableListOf(VaultRoom(Coord(0, 0), passcode))
    val edgeToPassword = mutableMapOf(Coord(0, 0) to passcode)
    while (!edgeToPassword.keys.contains(Coord(3, 3))) {
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
    return edgeToPassword[Coord(3, 3)]!!.replace(passcode, "")
}

data class VaultRoom(val coord: Coord, val passcode: String)

fun md5(input: String): String {
    val md = MessageDigest.getInstance("md5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0').substring(0, 4)
}