package util

import java.io.File


fun readInputLineByLine(path: String): List<String> {
    val lineList = mutableListOf<String>()
    File(path).inputStream().bufferedReader().forEachLine { lineList.add(it) }
    return lineList
}
