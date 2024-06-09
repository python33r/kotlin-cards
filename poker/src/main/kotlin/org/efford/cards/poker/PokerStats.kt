package org.efford.cards.poker

import java.io.File
import java.io.PrintWriter

class PokerStats(val numTrials: Int, logPath: String) {
    private val logFile = PrintWriter(File(logPath))
    private var pair = 0
    private var twoPairs = 0
    private var threeOfAKind = 0
    private var fourOfAKind = 0
    private var fullHouse = 0
    private var flush = 0
    private var straight = 0

    fun runTrials() {
        logFile.use {
            repeat(numTrials) {
                val deck = Deck()
                deck.shuffle()
                runTrialWith(deck)
            }
        }
    }

    private fun runTrialWith(deck: Deck) {
        repeat(10) {
            val h = PokerHand()
            repeat(5) {
                deck.deal()?.let { h.add(it) }
            }
            when {
                h.isPair         -> { log("$h   Pair"); ++pair }
                h.isTwoPairs     -> { log("$h   Two Pairs"); ++twoPairs }
                h.isThreeOfAKind -> { log("$h   Three of a Kind"); ++threeOfAKind }
                h.isFourOfAKind  -> { log("$h   Four of a Kind"); ++fourOfAKind }
                h.isFullHouse    -> { log("$h   Full House"); ++fullHouse }
                h.isFlush        -> { log("$h   Flush"); ++flush }
                h.isStraight     -> { log("$h   Straight"); ++straight }
                else             -> log("$h")
            }
        }
    }

    private fun log(msg: String) = logFile.println(msg)

    fun showResults() {
        val hands = numTrials * 10
        with(System.out) {
            printf("%,d hands dealt\n\n", hands)
            printf("P(Pair)        = %5.2f%%\n", 100.0 * pair / hands)
            printf("P(Two Pairs)   = %5.2f%%\n", 100.0 * twoPairs / hands)
            printf("P(3 of a Kind) = %5.2f%%\n", 100.0 * threeOfAKind / hands)
            printf("P(Straight)    = %5.2f%%\n", 100.0 * straight / hands)
            printf("P(Flush)       = %5.2f%%\n", 100.0 * flush / hands)
            printf("P(Full House)  = %5.2f%%\n", 100.0 * fullHouse / hands)
            printf("P(4 of a Kind) = %5.2f%%\n", 100.0 * fourOfAKind / hands)
        }
    }
}
