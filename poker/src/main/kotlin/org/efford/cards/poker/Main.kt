package org.efford.cards.poker

/**
 * Entry point for Poker stats program.
 */
fun main() {
    println()
    val numTrials = getNumTrials()
    val logPath = getLogPath()
    with(PokerStats(numTrials, logPath)) {
        runTrials()
        showResults()
    }
}

private fun getNumTrials(): Int {
    while (true) {
        try {
            print("Number of trials: ")
            return readln().toInt().also { require(it > 0) }
        }
        catch (err: Exception) {
            println("Error: try again...")
        }
    }
}

private fun getLogPath(): String {
    while (true) {
        try {
            print("Logfile path: ")
            return readln().also { require(it.isNotBlank()) }
        }
        catch (err: Exception) {
            println("Error: try again...")
        }
    }
}
