package aoc2022.day12.render

import java.io.File

fun main() {
    RenderDay12().run()
}

class RenderDay12 {

    fun run() {
        val input = util.readInputToMap("src/main/kotlin/aoc2022/day12/assets/input")
        var body = ""
        input.entries.forEach { entry ->
            body += "<span class=\"c-${entry.value}\">&nbsp;&nbsp;</span>"
            if (entry.key.x == 158) body += "<br>\n"
        }
        val content = """<!doctype html>
<head title="Output render">
<meta charset="utf-8">
<link href="//fonts.googleapis.com/css?family=Source+Code+Pro:600&amp;subset=latin,latin-ext" rel="stylesheet" type="text/css">
<style>
    body {
        background: #0f0f23;
        font-size: 10px;
        font-family: "Source Code Pro", monospace;
    }
    .c-S { background: #45bf55; }
    .c-E { background: #FF0000; }
    .c-a { background: #fff9ef; }
    .c-b { background: #ffe1ae; }
    .c-c { background: #ffdb9e; }
    .c-d { background: #ffd48e; }
    .c-e { background: #ffce7d; }
    .c-f { background: #ffc86d; }
    .c-g { background: #ffc25d; }
    .c-h { background: #ffbc4d; }
    .c-i { background: #ffb63d; }
    .c-j { background: #ffb02c; }
    .c-k { background: #ffaa1c; }
    .c-l { background: #ffa40c; }
    .c-m { background: #fb9d00; }
    .c-n { background: #eb9300; }
    .c-o { background: #da8900; }
    .c-p { background: #ca7e00; }
    .c-q { background: #ba7400; }
    .c-r { background: #aa6a00; }
    .c-s { background: #9a6000; }
    .c-t { background: #895600; }
    .c-u { background: #794c00; }
    .c-v { background: #694200; }
    .c-w { background: #593800; }
    .c-x { background: #492d00; }
    .c-y { background: #382300; }
    .c-z { background: #281900; }
   
</style>
<body>
    <div>
        $body
    </div>
</body>
"""
        val inputRender = File("src/main/kotlin/aoc2022/day12/render/input.html")
        inputRender.printWriter().use { it.println(content) }
    }
}
