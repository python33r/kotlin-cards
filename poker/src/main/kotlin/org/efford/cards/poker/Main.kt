package org.efford.cards.poker

import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Error: number of trials & path to logfile must be specified")
        exitProcess(1)
    }

    val numTrials = args[0].toInt()

    val stats = PokerStats(numTrials, args[1])
    stats.runTrials()
    stats.showResults()
}
