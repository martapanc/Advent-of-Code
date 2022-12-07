package aoc2022.day07

import util.readInputLineByLine
import java.io.File

fun createScript(list: List<String>, test: String = "") {
    var scriptContent = "#!/bin/bash\n\nmkdir root${test}\ncd root${test}\n"
    list.forEach { line ->
        if (line.startsWith("$")) {
            if (line.startsWith("$ cd") && line != "$ cd /") {
                scriptContent += line.replace("$ ", "") + "\n"
            }
        } else if (line.startsWith("dir")) {
            scriptContent += line.replace("dir ", "mkdir ") + "\n"
        } else {
            val split = line.split(" ")
            val size = Integer.parseInt(split[0])
            val filename = split[1]
            scriptContent += "truncate -s $size $filename\n"
        }
    }

    val script = File("src/main/kotlin/aoc2022/day07/assets/day7script${test}.sh")
    script.printWriter().use { it.println(scriptContent) }
}

fun readTreeInputToList(path: String): Map<String, Long> {
    val subDirs: MutableMap<String, Int> = mutableMapOf()
    readInputLineByLine(path).forEach { line ->
        val dirname = line.split("\\s+".toRegex()).last()
        val subdirCount = readInputLineByLine(path).count { it.contains(dirname) }
        subDirs[dirname] = subdirCount
    }

    val dirs: MutableMap<String, Long> = mutableMapOf()
    readInputLineByLine(path).map { entry ->
        val split = entry.split("\\s+".toRegex())
        val dirname: String = split[1]
        val subdirCount: Int = subDirs[dirname]!!
        val size = split[0].toLong() - 4096 * subdirCount
        dirs[dirname] = size
    }
    return dirs
}

fun part1(dirs: Map<String, Long>): Long {
    return dirs.values.filter { it <= 100000 }.sum()
}

fun part2(dirs: Map<String, Long>): Long {
    val spaceToFree = 30000000 - (70000000 - dirs.values.max())
    return dirs.values.filter { it >= spaceToFree }.min()
}
