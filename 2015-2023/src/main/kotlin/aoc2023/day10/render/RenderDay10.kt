package aoc2023.day10.render

import util.Coord
import util.readInputToMap
import java.io.File

fun main() {
    val inputFileName = "input3"
    RenderDay10().run(inputFileName)
}
class RenderDay10 {

    fun run(inputFileName: String) {
        val grid = readInputToMap("src/main/kotlin/aoc2023/day10/assets/${inputFileName}")
        var body = ""
        grid.entries.forEach { entry: Map.Entry<Coord, Char> ->
            val img = if (entry.value == '.') "empty" else entry.value
            body += "<img src=\"pics/${img}.svg\" alt=\"${entry.value}\">\n"
        }
        val cols = grid.keys.maxBy { it.x }.x + 1
        val width = 20 * cols
        var html = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>2023 Day 10</title>
                <style>
                    body {
                        display: grid;
                        width: ${width}px;
                        grid-template-columns: repeat(${cols}, 1fr);
                        gap: 0; 
                        justify-content: center;
                        margin: 0; 
                        padding: 0; 
                    }

                    img {
                        width: 20px;
                        height: 20px;
                        margin: 0;
                        padding: 0;
                        display: block;
                    }
                </style>
            </head>
            <body>
                $body
            </body>
            </html>
        """.trimIndent()

        val inputRender = File("src/main/kotlin/aoc2023/day10/render/${inputFileName}.html")
        inputRender.printWriter().use { it.println(html) }
    }
}