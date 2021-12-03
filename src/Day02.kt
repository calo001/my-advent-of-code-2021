fun main() {
    fun part1(input: List<String>): Int {
        return part1ResolutionDay2(input) // 2322630
    }

    fun part2(input: List<String>): Int {
        return part2ResolutionDay2(input) // 2105273490
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println("Day 2")
    println("Result Part 1: ${part1(input)}")
    println("Result Part 2: ${part2(input)}")

    println(footer())
}

fun part1ResolutionDay2(input: List<String>): Int {
    val course = convertToCommand(input).map { command ->
        when (command) {
            is Command.Forward -> Course(position = command.amount, depth = 0)
            is Command.Down -> Course(position = 0, aim = 0, depth = command.amount)
            is Command.Up -> Course(position = 0, aim = 0, depth = -command.amount)
        }
    }.reduce { acc, course -> acc + course }

    return course.position * course.depth
}

fun part2ResolutionDay2(input: List<String>): Int {
    val course = convertToCommand(input).map { command ->
        when (command) {
            is Command.Down -> Course(position = 0, depth = 0, aim = command.amount)
            is Command.Up -> Course(position = 0, depth = 0, aim = -command.amount)
            is Command.Forward -> Course(position = command.amount, depth = 0, aim = 0)
        }
    }.reduce { acc, course ->
        Course(
            position = acc.position + course.position,
            depth = acc.depth + (acc.aim + course.aim) * course.position,
            aim = acc.aim + course.aim
        )
    }

    return course.position * course.depth
}

fun convertToCommand(input: List<String>) =
    input.mapNotNull { line ->
        val split = line.split(" ")
        when (split.firstOrNull()) {
            "forward" -> Command.Forward(split.getOrNull(1)?.toIntOrNull() ?: 0)
            "down" -> Command.Down(split.getOrNull(1)?.toIntOrNull() ?: 0)
            "up" -> Command.Up(split.getOrNull(1)?.toIntOrNull() ?: 0)
            else -> null
        }
    }

class Course(val position: Int, val depth: Int, val aim: Int = 0) {
    operator fun plus(other: Course): Course {
        return Course(position + other.position, depth + other.depth, aim + other.aim)
    }
}

sealed class Command(open val name: String, val value: Int) {
    class Forward(val amount: Int): Command("forward", amount)
    class Down(val amount: Int): Command("down", amount)
    class Up(val amount: Int): Command("up", amount)
}