package aoc2022.day17.renders

import java.io.File

fun main() {
    RenderDay17().run(1)
    RenderDay17().run(2)
}

class RenderDay17 {

    fun run(id: Int) {
        val input = util.readInputToMap("src/main/kotlin/aoc2022/day17/renders/output${id}")
        var body = ""
        val maxX = input.keys.maxBy { it.x }.x
        input.entries.forEach { entry ->
            var tile = entry.value.toString()
            if (entry.value == '.') tile = "blank"
            if (entry.value == '|' || entry.value == '+' || entry.value == '-') tile = "border"
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
        font-size: 14px;
        font-family: "Source Code Pro", monospace;
    }
    .tile-blank { background: #1e1b1b; border-style: solid; }
    .tile-border { background: #86857e; border-style: outset; }
    .tile-p { background: #da7f04; border-style: outset; }
    .tile-s { background: #ffd60f; border-style: outset; }
    .tile-r { background: #3858bb; border-style: outset; }
    .tile-b { background: #e30000; border-style: outset; }
    .tile-l { background: #1b9f1d; border-style: outset; }
</style>
<body>
    <div>
        $body
    </div>
</body>
"""
        val inputRender = File("src/main/kotlin/aoc2022/day17/renders/output${id}.html")
        inputRender.printWriter().use { it.println(content) }
    }
}

