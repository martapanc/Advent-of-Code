package aoc2021.day16

import aoc2020.day05.binaryToDecimal

fun expandAndSumVersion(hexInput: String): Int {
  val hexToBin = getHexToBinMap()
  val packetsToProcess = mutableListOf<Packet>()
  var binaryInput = ""
  hexInput.forEach { binaryInput += hexToBin[it] }

  var versionChecksum = -1

  packetsToProcess.add(
    Packet(
      binaryInput.substring(6),
      binaryToDecimal(binaryInput.substring(0, 3).toInt()),
      binaryToDecimal(binaryInput.substring(3, 6).toInt())
    )
  )

  while (packetsToProcess.isNotEmpty()) {
    val packet = packetsToProcess[0]

    when (packet.typeId) {
      4 -> {
        val bits = packet.content.windowed(5, 5)
        var number = ""
        bits.forEach { number += it.substring(1) }
        val result = binaryToDecimal(number.toInt())
      }
      else -> {
        val lengthTypeId = packet.content.substring(0, 1).toInt()
        val length = if (lengthTypeId == 0) 15 else 11
      }
    }

    versionChecksum += (packet.version)
    packetsToProcess.removeAt(0)
  }

  return versionChecksum
}

fun getHexToBinMap(): Map<Char, String> {
  val map = mutableMapOf<Char, String>()
  map['0'] = "0000"
  map['1'] = "0001"
  map['2'] = "0010"
  map['3'] = "0011"
  map['4'] = "0100"
  map['5'] = "0101"
  map['6'] = "0110"
  map['7'] = "0111"
  map['8'] = "1000"
  map['9'] = "1001"
  map['A'] = "1010"
  map['B'] = "1011"
  map['C'] = "1100"
  map['D'] = "1101"
  map['E'] = "1110"
  map['F'] = "1111"
  return map
}

class Packet(val content: String, val version: Int, val typeId: Int) {
  override fun toString(): String {
    return "{version: $version, typeId: $typeId, packet: $content}"
  }
}
