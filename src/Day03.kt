fun main() {
    fun part1(input: List<String>): Int {
        return part1ResolutionDay3(input) // 852500
    }

    fun part2(input: List<String>): Int {
        return part2ResolutionDay3(input) // 1007985
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println("Day 3")
    println("Result Part 1: ${part1(input)}")
    println("Result Part 2: ${part2(input)}")

    println(footer())
}

fun part2ResolutionDay3(input: List<String>): Int {
    val resultsOxygenGenerator = mutableListOf<String>()
    resultsOxygenGenerator.addAll(input)

    val resultsCO2Scrubber = mutableListOf<String>()
    resultsCO2Scrubber.addAll(input)

    val positions = input.maxOf { it.length }
    (0 until positions).forEach { position ->
        val gammaRate = binaryGroup(resultsOxygenGenerator)
        val ellipsonRate = binaryGroup(resultsCO2Scrubber)

        val backupOxygenResults = resultsOxygenGenerator.toList()
        resultsOxygenGenerator.clear()

        val backupCO2Results = resultsCO2Scrubber.toList()
        resultsCO2Scrubber.clear()

        filterData(
            input = backupOxygenResults,
            finalCheck = '1',
            dataCheck = gammaRate[position].mostCommon.toString(),
            areEqual = gammaRate[position].areEqual,
            position = position,
            onEach = { resultsOxygenGenerator.add(it) }
        )

        filterData(
            input = backupCO2Results,
            finalCheck = '0',
            dataCheck = ellipsonRate[position].leastCommon.toString(),
            areEqual = ellipsonRate[position].areEqual,
            position = position,
            onEach = { resultsCO2Scrubber.add(it) }
        )
    }
    val oxygenGeneratorRating = resultsOxygenGenerator.first().toInt(2)
    val co2GeneratorRating = resultsCO2Scrubber.first().toInt(2)

    return oxygenGeneratorRating * co2GeneratorRating
}

fun filterData(
    input: List<String>,
    finalCheck: Char,
    dataCheck: String,
    areEqual: Boolean,
    position: Int,
    onEach: (String) -> Unit,
) {
    input.filter { row ->
        when {
            input.size == 1 -> true
            areEqual -> { row[position] == finalCheck }
            else -> { row[position].toString() == dataCheck }
        }
    }.onEach {
        onEach(it)
    }
}

fun part1ResolutionDay3(input: List<String>): Int {
    val resultBinaryGroup = binaryGroup(input)

    val gammaRate = resultBinaryGroup.gammaRate()
    val epsilonRate = resultBinaryGroup.epsilonRate()

    val gammaRateBase2 = gammaRate.toInt(2)
    val epsilonRateBase2 = epsilonRate.toInt(2)

    return gammaRateBase2 * epsilonRateBase2
}

private fun binaryGroup(input: List<String>) = input
    .map { line ->
        line.asIterable().map { char ->
            when (char) {
                '0' -> Binary(zero = 1, one = 0)
                '1' -> Binary(zero = 0, one = 1)
                else -> Binary(zero = 0, one = 0)
            }
        }
    }.reduce { acc, listBinary ->
        acc.zip(listBinary).map { (a, b) -> a + b }
    }

class Binary(val zero: Int, val one: Int) {
    operator fun plus(other: Binary): Binary {
        return Binary(zero + other.zero, one + other.one)
    }
    val mostCommon: Int
        get() = if (zero > one) 0 else 1

    val leastCommon: Int
        get() = if (zero > one) 1 else 0

    val areEqual: Boolean
        get() = zero == one
}

fun List<Binary>.gammaRate(): String {
    return this.map {
        it.mostCommon.toString()
    }.reduce { acc, s -> acc + s }
}

fun List<Binary>.epsilonRate(): String {
    return this.map {
        it.leastCommon.toString()
    }.reduce { acc, s -> acc + s }
}