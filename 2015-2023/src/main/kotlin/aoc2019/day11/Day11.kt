package aoc2019.day11

import aoc2019.day09.processParameterMode
import java.awt.Point
import java.util.*

object Day11 {
    // INPUT
    // 0 = black cell   |   1 = white cell
    // OUTPUT
    // 0 = paint black  |   1 = paint white
    // 0 = left         |   1 = right
    @JvmStatic
    fun processInput(numbers: ArrayList<Long>, initial: Int): Long {
        val panelMap: MutableMap<Point, Panel?> = HashMap()
        panelMap[Point(0, 0)] = Panel(initial, Panel.Direction.UP)
        var i = 0
        var relativeBase = 0
        var outputBuilder = StringBuilder()
        var currentPos = Point(0, 0)
        var paintedPanels = 1
        while (i < numbers.size) {
            val opCode = Math.toIntExact(numbers[i])
            if (opCode == 99) {
                break
            }
            val input = panelMap[currentPos]!!.currentPanel
            val output = processParameterMode(numbers, i, opCode, input, relativeBase)
            outputBuilder.append(output.code)
            if (outputBuilder.length == 2) {
                val outputVals = outputBuilder.toString()
                if (outputVals[0] == '0') {
                    panelMap[currentPos]!!.paintPanelBlack()
                } else {
                    panelMap[currentPos]!!.paintPanelWhite()
                }
                if (outputVals[1] == '0') {
                    when (panelMap[currentPos]!!.direction) {
                        Panel.Direction.UP -> {
                            currentPos = Point(currentPos.x - 1, currentPos.y)
                            if (panelMap[currentPos] == null) {
                                panelMap[currentPos] = Panel(0, Panel.Direction.LEFT)
                                paintedPanels++
                            } else {
                                panelMap[currentPos]!!.direction = Panel.Direction.LEFT
                            }
                        }
                        Panel.Direction.DOWN -> {
                            currentPos = Point(currentPos.x + 1, currentPos.y)
                            if (panelMap[currentPos] == null) {
                                panelMap[currentPos] = Panel(0, Panel.Direction.RIGHT)
                                paintedPanels++
                            } else {
                                panelMap[currentPos]!!.direction = Panel.Direction.RIGHT
                            }
                        }
                        Panel.Direction.LEFT -> {
                            currentPos = Point(currentPos.x, currentPos.y - 1)
                            if (panelMap[currentPos] == null) {
                                panelMap[currentPos] = Panel(0, Panel.Direction.DOWN)
                                paintedPanels++
                            } else {
                                panelMap[currentPos]!!.direction = Panel.Direction.DOWN
                            }
                        }
                        Panel.Direction.RIGHT -> {
                            currentPos = Point(currentPos.x, currentPos.y + 1)
                            if (panelMap[currentPos] == null) {
                                panelMap[currentPos] = Panel(0, Panel.Direction.UP)
                                paintedPanels++
                            } else {
                                panelMap[currentPos]!!.direction = Panel.Direction.UP
                            }
                        }
                    }
                } else {
                    when (panelMap[currentPos]!!.direction) {
                        Panel.Direction.UP -> {
                            currentPos = Point(currentPos.x + 1, currentPos.y)
                            if (panelMap[currentPos] == null) {
                                panelMap[currentPos] = Panel(0, Panel.Direction.RIGHT)
                                paintedPanels++
                            } else {
                                panelMap[currentPos]!!.direction = Panel.Direction.RIGHT
                            }
                        }
                        Panel.Direction.DOWN -> {
                            currentPos = Point(currentPos.x - 1, currentPos.y)
                            if (panelMap[currentPos] == null) {
                                panelMap[currentPos] = Panel(0, Panel.Direction.LEFT)
                                paintedPanels++
                            } else {
                                panelMap[currentPos]!!.direction = Panel.Direction.LEFT
                            }
                        }
                        Panel.Direction.LEFT -> {
                            currentPos = Point(currentPos.x, currentPos.y + 1)
                            if (panelMap[currentPos] == null) {
                                panelMap[currentPos] = Panel(0, Panel.Direction.UP)
                                paintedPanels++
                            } else {
                                panelMap[currentPos]!!.direction = Panel.Direction.UP
                            }
                        }
                        Panel.Direction.RIGHT -> {
                            currentPos = Point(currentPos.x, currentPos.y - 1)
                            if (panelMap[currentPos] == null) {
                                panelMap[currentPos] = Panel(0, Panel.Direction.DOWN)
                                paintedPanels++
                            } else {
                                panelMap[currentPos]!!.direction = Panel.Direction.DOWN
                            }
                        }
                    }
                }
                outputBuilder = StringBuilder()
            }
            if (output.relativeBase != 0) {
                relativeBase = output.relativeBase
            }
            i += output.index
        }
        printPanels(panelMap)
        return paintedPanels.toLong()
    }

    private fun printPanels(panelMap: Map<Point, Panel?>) {
        for (y in 1 downTo -7 + 1) {
            for (x in -2..42) {
                if (panelMap.containsKey(Point(x, y))) {
                    print(if (panelMap[Point(x, y)]!!.currentPanel == 0) " " else "█")
                } else {
                    print(" ")
                }
            }
            println()
        }
    }
}