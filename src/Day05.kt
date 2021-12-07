import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return part1ResolutionDay5(input) //
    }

    fun part2(input: List<String>): Int {
        return part2ResolutionDay5(input) //
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println("Day 5")
    println("Result Part 1: ${part1(input)}")
    println("Result Part 2: ${part2(input)}")

    println(footer())
}

fun part2ResolutionDay5(input: List<String>): Int {
    return findInterceptions(input)
}

fun part1ResolutionDay5(input: List<String>): Int {
    return findInterceptions(input, true)
}

fun findInterceptions(input: List<String>, filterLines: Boolean = false): Int {
    val lines = input.map {
        val lineElements = it.split(" ")
        val startPoint = Point.fromString(lineElements[0])
        val endPoint = Point.fromString(lineElements[2])
        Line(startPoint, endPoint)
    }.run {
        if (filterLines) this.filter { it.isALine } else this
    }

    return lines
        .flatMap { it.points }
        .groupBy {
            it
        }
        .count {
            it.value.size > 1
        }
}

class Line(val start: Point, val end: Point) {
    val points: List<Point> get() {
        val points = mutableListOf<Point>()
        var x = start.x
        var y = start.y
        val dx = end.x - start.x
        val dy = end.y - start.y
        val steps = abs(dx).coerceAtLeast(abs(dy))
        val xInc = dx / steps
        val yInc = dy / steps
        for (i in 0 .. steps) {
            points.add(Point(x, y))
            x += xInc
            y += yInc
        }
        return points
    }

    val isALine: Boolean get() {
        return start.x == end.x || start.y == end.y
    }
}

data class Point(val x: Int, val y: Int) {
    companion object {
        fun fromString(input: String): Point {
            val lineElements = input.split(",")
            return Point(x = lineElements[0].toInt(), y = lineElements[1].toInt())
        }
    }
}