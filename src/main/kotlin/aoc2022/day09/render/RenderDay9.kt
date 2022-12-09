package aoc2022.day09.render

import java.io.File

fun main() {
    RenderDay9().run(1)
    RenderDay9().run(2)
}

class RenderDay9 {

    fun run(part: Int) {
        val output = util.readInputToMap("src/main/kotlin/aoc2022/day09/render/output_part${part}")
        var body = ""
        output.entries.forEach { entry ->
            val tile = when (entry.value) {
                'S' -> "s"
                '.' -> "b"
                '#' -> "t"
                else -> ""
            }
            body += "<span class=\"tile-${tile}\">&nbsp;&nbsp;</span>"
            if (entry.key.x == if (part == 1) 181 else 175) body += "<br>\n"
        }
        val content = """<!doctype html>
<head title="Output render">
<meta charset="utf-8">
<link href="//fonts.googleapis.com/css?family=Source+Code+Pro:600&amp;subset=latin,latin-ext" rel="stylesheet" type="text/css">
<style>
    body {
        background: #0f0f23;
        font-size: 8.5px;
        font-family: "Source Code Pro", monospace;
    }
    .tile-s {
        background: #${if (part == 1) "de9104" else "ce4f02"};
    }
    .tile-t {
        background: #${if (part == 1) "acb0ff" else "a9ffa1"};
    }
    .tile-b {
        background: #${if (part == 1) "03096e" else "073F02"};
    }
</style>
<body>
    <div>
        $body
    </div>
</body>
"""
        val inputRender = File("src/main/kotlin/aoc2022/day09/render/part${part}.html")
        inputRender.printWriter().use { it.println(content) }
    }
}
