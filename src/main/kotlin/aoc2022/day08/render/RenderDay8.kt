package aoc2022.day08.render

import aoc2021.day09.readInputToMap
import java.io.File

fun main() {
    RenderDay8().run()
}

class RenderDay8 {

    fun run() {
        val grid = readInputToMap("src/main/kotlin/aoc2022/day08/assets/input")
        var body1 = ""
        grid.entries.forEach { entry ->
            body1 += "<span class=\"height-${entry.value}\">&nbsp;&nbsp;</span>"
            if (entry.key.x == 98) body1 += "<br>\n"
        }
        val content1 = """<!doctype html>
<head title="Input render">
<meta charset="utf-8">
<link href="//fonts.googleapis.com/css?family=Source+Code+Pro:600&amp;subset=latin,latin-ext" rel="stylesheet" type="text/css">
<style>
    body {
        background: #0f0f23;
        font-size: 10px;
        font-family: "Source Code Pro", monospace;
    }
    .height-0 {
        background: #ffffff;
    }
    .height-1 {
        background: #dcf2dc;
    }
    .height-2 {
        background: #b8e5b8;
    }
    .height-3 {
        background: #95d895;
    }
    .height-4 {
        background: #72cb72;
    }
    .height-5 {
        background: #4ebe4e;
    }
    .height-6 {
        background: #3ba03b;
    }
    .height-7 {
        background: #2e7d2e;
    }
    .height-8 {
        background: #215a21;
    }
    .height-9 {
        background: #143614;
    }
</style>
<body>
    <div>
        $body1
    </div>
</body>
"""
        val inputRender = File("src/main/kotlin/aoc2022/day08/render/input.html")
        inputRender.printWriter().use { it.println(content1) }


        val outputGrid = util.readInputToMap("src/main/kotlin/aoc2022/day08/render/output")
        var body2 = ""
        outputGrid.entries.forEach { entry ->
            body2 += "<span class=\"height-${entry.value}\">&nbsp;&nbsp;</span>"
            if (entry.key.x == 98) body2 += "<br>\n"
        }
        val content2 = """<!doctype html>
<head title="Output render">
<meta charset="utf-8">
<link href="//fonts.googleapis.com/css?family=Source+Code+Pro:600&amp;subset=latin,latin-ext" rel="stylesheet" type="text/css">
<style>
    body {
        background: #0f0f23;
        font-size: 10px;
        font-family: "Source Code Pro", monospace;
    }
    .height-0 {
        background: #ffffff;
    }
    .height-1 {
        background: #dcf2dc;
    }
    .height-2 {
        background: #b8e5b8;
    }
    .height-3 {
        background: #95d895;
    }
    .height-4 {
        background: #72cb72;
    }
    .height-5 {
        background: #4ebe4e;
    }
    .height-6 {
        background: #3ba03b;
    }
    .height-7 {
        background: #2e7d2e;
    }
    .height-8 {
        background: #215a21;
    }
    .height-9 {
        background: #143614;
    }
    .height-x {
        background: #fae03f;
    }    
    .height-t {
        background: #ff1515;
    }

</style>
<body>
    <div>
        $body2
    </div>
</body>
"""
        val inputRender2 = File("src/main/kotlin/aoc2022/day08/render/output.html")
        inputRender2.printWriter().use { it.println(content2) }
    }
}
