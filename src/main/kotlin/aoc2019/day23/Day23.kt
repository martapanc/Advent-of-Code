package aoc2019.day23

import aoc2019.commons.IntCodeProgram
import util.readInputLineByLine


fun runNetworkProgram(path: String): Output {
    val program = readInputLineByLine(path)[0].split(",").map { it.toLong() }
    var first = 0L

    val second = run {
        val networkInterfaceControllers = List(50) { i ->
            IntCodeProgram(program).also { it.input(i) }
        }

        var natX = 0L
        var natY = 0L
        var isNatActive = false
        var natHistory: Long? = null

        while (true) {
            for (nic in networkInterfaceControllers)
                nic.execute()

            val hasInput = BooleanArray(networkInterfaceControllers.size)

            for (nic in networkInterfaceControllers) {
                for ((destAddress, x, y) in nic.output.chunked(3)) {
                    val address = destAddress.toInt()
                    if (address == 255) {
                        if (!isNatActive)
                            first = y
                        natX = x
                        natY = y
                        isNatActive = true
                    } else {
                        networkInterfaceControllers[address].input(x)
                        networkInterfaceControllers[address].input(y)
                        hasInput[address] = true
                    }
                }
                nic.output.clear()
            }
            for (i in networkInterfaceControllers.indices) {
                if (!hasInput[i])
                    networkInterfaceControllers[i].input(-1)
            }
            if (isNatActive && hasInput.none { it }) {
                if (natHistory == natY)
                    return@run natY
                natHistory = natY
                networkInterfaceControllers[0].input(natX)
                networkInterfaceControllers[0].input(natY)
            }
        }
    } as Long

    return Output(first, second)
}

// First = the Y value of the first packet sent to address 255
// Second = the first Y value delivered by the NAT to the computer at address 0 twice in a row
class Output(val first: Long, val second: Long)