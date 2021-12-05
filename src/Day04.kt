fun main() {
    fun part1(input: List<String>): Int {
        return part1ResolutionDay4(input) // 38594
    }

    fun part2(input: List<String>): Int {
        return part2ResolutionDay4(input) //
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println("Day 4")
    println("Result Part 1: ${part1(input)}")
    println("Result Part 2: ${part2(input)}")

    println(footer())
}

fun part1ResolutionDay4(input: List<String>): Int {
    return resolveBingo(input)
}

fun part2ResolutionDay4(input: List<String>): Int {
    return resolveBingo(input, false)
}

fun resolveBingo(input: List<String>, stopOnFirst: Boolean = true): Int {
    val numbers = input.first().split(",").map { it.toInt() }
    val boards = input.drop(1)
        .filterNot { it.isEmpty() }
        .chunked(5)
        .map { row ->
            row.map { numbers -> numbers
                .split(" ")
                .filterNot { it == "" }
                .map { it.toInt() }
                .map { BingoCell(it) }
            }
        }.map {  row ->
            BingoBoard(row)
        }

    var bingoNumber = -1
    var bingoBoardWinner: BingoBoard? = null
    run boards@ {
        numbers.forEach { number ->
            boards.forEach  { bingoBoard ->
                if (!bingoBoard.checkBingo()) {
                    bingoBoard.updateCheckedNumber(number)
                    val isWinner = bingoBoard.checkBingo()
                    if (isWinner) {
                        bingoNumber = number
                        bingoBoardWinner = bingoBoard
                        if (stopOnFirst) return@boards
                    }
                }
                println(number)
                println(bingoBoard.toStringBoard())
                println()
            }
        }
    }

    val unmarkedNumbers = bingoBoardWinner?.rows?.flatten()?.filterNot { it.checked }?.sumOf { it.number } ?: 0
    return unmarkedNumbers * bingoNumber
}

class BingoBoard(val rows: List<List<BingoCell>>) {
    fun toStringBoard() =
        rows.map { row ->
            row.map { cell ->
                if (cell.checked) {
                    "${"%02d".format(cell.number)} true  "
                } else {
                    "${"%02d".format(cell.number)} false "
                }
            }.joinToString(" ")
        }.joinToString ("\n")

    fun checkBingo(): Boolean {
        rows.forEach { cell ->
            val bingo = cell.all { it.checked }
            if (bingo) return true
        }
        (rows.indices).forEach { indexCell ->
            val bingo = rows.all { it[indexCell].checked }
            if (bingo) return true
        }
        return false
    }

    fun updateCheckedNumber(value: Int) {
        rows.forEach { row ->
            row.forEach { cell ->
                if (cell.number == value) {
                    cell.checked = true
                }
            }
        }
    }
}

class BingoCell(val number: Int, var checked: Boolean = false)