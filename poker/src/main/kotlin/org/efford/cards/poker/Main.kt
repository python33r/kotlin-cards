package org.efford.cards.poker

fun getInt(prompt: String): Int {
    while (true) {
        try {
            print(prompt)
            return readln().toInt().also { require(it > 0) }
        }
        catch (err: Exception) {
            println("Error: try again...")
        }
    }
}

fun getString(prompt: String): String {
    while (true) {
        try {
            print(prompt)
            return readln().also { require(it.isNotBlank()) }
        }
        catch (err: Exception) {
            println("Error: try again...")
        }
    }
}

fun main() {
    println()
    val numTrials = getInt("Number of trials: ")
    val logPath = getString("Logfile path: ")
    with(PokerStats(numTrials, logPath)) {
        runTrials()
        showResults()
    }
}
