package aoc2023.day18.render

import util.Coord
import util.readInputToMap
import java.io.File

fun main() {
    val inputFileName = "filledmap1"
    RenderDay18().run(inputFileName)
}
class RenderDay18 {

    fun run(inputFileName: String) {
        val grid = readInputToMap("src/main/kotlin/aoc2023/day18/render/${inputFileName}")
        var body = ""
        grid.entries.forEach { entry: Map.Entry<Coord, Char> ->
            val char = if (entry.value == '#') "fill" else "void"
            body += "<span class=\"${char}\">&nbsp;&nbsp;&nbsp;</span>"
            if (entry.key.x == 338) body += "<br>\n"
        }
        val html = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>2023 Day 10</title>
                <style>
                    .fill {
                        background-color: #447FAA;
                    }
                    .void {
                        background-color: #65BFFF;
                    }
                </style>
            </head>
            <body>
                $body
            </body>
            </html>
        """.trimIndent()

        val inputRender = File("src/main/kotlin/aoc2023/day18/render/${inputFileName}.html")
        inputRender.printWriter().use { it.println(html) }
    }
}