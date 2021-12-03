fun main() {
    fun part1(input: List<String>): Int {
        return part1ResolutionDay1(input) // 1564
    }

    fun part2(input: List<String>): Int {
        return part2ResolutionDay1(input) // 1611
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println("Day 1")
    println("Result Part 1: ${part1(input)}")
    println("Result Part 2: ${part2(input)}")

    println(footer())
}

fun part1ResolutionDay1(input: List<String>): Int {
    return input.filterIndexed { index, depth ->
        val previousDepth = input.getOrNull(index - 1)?.toIntOrNull()
        val currentDepth = depth.toIntOrNull()
        previousDepth != null && currentDepth != null && currentDepth > previousDepth
    }.count()
}

fun part2ResolutionDay1(input: List<String>): Int {
    val windowedDepths = input.windowed(3)
        .map { it.sumOf { value -> value.toIntOrNull() ?: 0 } }

    return windowedDepths.filterIndexed { index, sumWindowed ->
        val previous = windowedDepths.getOrNull(index - 1)
        previous != null && sumWindowed > previous
    }.count()
}