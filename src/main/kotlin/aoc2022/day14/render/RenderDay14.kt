package aoc2022.day14.render

import java.io.File
import kotlin.random.Random

fun main() {
    RenderDay14().run(1)
    RenderDay14().run(2)
}

class RenderDay14 {

    fun run(part: Int) {
        val input = util.readInputToMap("src/main/kotlin/aoc2022/day14/render/rendered_part${part}")
        var body = ""
        val maxX = input.keys.maxBy { it.x }.x
        val fontSize = if (part == 1) 10 else 8
        input.entries.forEach { entry ->
            var tile = "o" + Random.nextInt(1, 5)
            if (entry.value == '.') tile = "void"
            if (entry.value == '#') tile = "bar"
            if (entry.value == '+') tile = "source"
            body += "<span class=\"tile-${tile}\">&nbsp;&nbsp;</span>"
            if (entry.key.x == maxX) body += "<br>\n"
        }
        val content = """<!doctype html>
<head title="Output render">
<meta charset="utf-8">
<link href="//fonts.googleapis.com/css?family=Source+Code+Pro:600&amp;subset=latin,latin-ext" rel="stylesheet" type="text/css">
<style>
    body {
        background: #0f0f23;
        font-size: ${fontSize}px;
        font-family: "Source Code Pro", monospace;
    }
    .tile-o1 { background: #be933e; }
    .tile-o2 { background: #9d772f; }
    .tile-o3 { background: #8a6623; }
    .tile-o4 { background: #9d8351; }
    .tile-void { background: #000b2d; }
    .tile-bar { background: #7db8fc; }
    .tile-source { background: #0276fc; }
</style>
<body>
    <div>
        $body
    </div>
</body>
"""
        val inputRender = File("src/main/kotlin/aoc2022/day14/render/part${part}.html")
        inputRender.printWriter().use { it.println(content) }
    }
}
