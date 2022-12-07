package aoc2022.day07

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

fun part2(list: List<String>): Int {
    return 0
}
