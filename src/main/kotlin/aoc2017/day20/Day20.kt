package aoc2017.day20

import util.readInputLineByLine
import kotlin.math.abs

fun readInputToParticles(path: String): List<Particle> {
    return readInputLineByLine(path)
        .mapIndexed { index, string -> parseParticle(index, string) }
}

fun findClosestParticle(particles: List<Particle>): Int? {
    return (1..1000).fold(particles) { acc, _ ->
        acc.map { it.move() }
    }.minByOrNull { it.position.distance }?.id
}

fun findClosestParticlePart2(particles: List<Particle>): Int {
    return (1..1000).fold(particles) { acc, _ ->
        acc.map { it.move() }
            .groupBy { it.position }
            .filterValues { it.size == 1 }
            .flatMap { it.value }
    }.size
}

private fun parseParticle(id: Int, inputString: String): Particle {
    return inputString.split("<", ">").let {
        Particle(id, parseVector(it[1]), parseVector(it[3]), parseVector(it[5]))
    }
}

private fun parseVector(inputString: String): Vector {
    return inputString
        .split(",").map { it.trim().toLong() }
        .let { Vector(it[0], it[1], it[2]) }
}

data class Vector(val x: Long, val y: Long, val z: Long) {

    val distance = abs(x) + abs(y) + abs(z)

    operator fun plus(other: Vector): Vector {
        return Vector(x + other.x, y + other.y, z + other.z)
    }
}

data class Particle(val id: Int, val position: Vector, val velocity: Vector, var acceleration: Vector) {

    fun move() = this.copy(
        velocity = velocity + acceleration,
        position = position + velocity + acceleration
    )
}
