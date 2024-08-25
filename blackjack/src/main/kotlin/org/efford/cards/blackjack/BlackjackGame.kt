package org.efford.cards.blackjack

/**
 * Simulator for a simplified version of Blackjack.
 */
class BlackjackGame {
    private val deck = Deck().apply { shuffle() }
    private val player = BlackjackHand()
    private val dealer = BlackjackHand()

    private var round = 0
    private var playerWins = 0
    private var dealerWins = 0
    private var ties = 0

    /**
     * Plays the game.
     */
    fun play() {
        while (roundRequested()) {
            playRound()
        }
        finish()
    }

    private fun roundRequested(): Boolean {
        return responseTo("\nPlay a round? (y/n): ") in "yY"
    }

    private fun responseTo(prompt: String): Char {
        print(prompt)
        return readlnOrNull()?.firstOrNull() ?: '?'
    }

    private fun playRound() {
        ++round
        println("\nROUND $round\n")

        dealToPlayer()
        if (player.isBust) {
            println("Dealer wins!")
            ++dealerWins
            player.discardTo(deck)
        }
        else {
            dealToDealer()
            determineWinner()
            player.discardTo(deck)
            dealer.discardTo(deck)
        }
    }

    private fun dealToPlayer() {
        repeat(2) {
            deck.deal()?.let { player.add(it) }
        }

        println("Player's hand:")
        while (true) {
            printHand(player)
            if (player.isBust) {
                println("Player is bust!")
                break
            }
            when (responseTo("[H]it or [S]tand? ")) {
                in "hH" -> deck.deal()?.let { player.add(it) }
                in "sS" -> return
                else -> println("Sorry, I didn't understand that!")
            }
        }
    }

    private fun printHand(hand: BlackjackHand) {
        print(hand.toString(",", start="(", end=")"))
        println(" = ${hand.value}")
    }

    private fun dealToDealer() {
        println("\nDealer's hand:")
        while (dealer.value < 17) {
            deck.deal()?.let { dealer.add(it) }
            printHand(dealer)
        }
    }

    private fun determineWinner() {
        println()
        if (dealer.isBust) {
            println("Dealer is bust - player wins!")
            ++playerWins
            return
        }

        when {
            player.isNatural && !dealer.isNatural -> {
                println("Player has natural, dealer does not - player wins!")
                ++playerWins
            }
            dealer.isNatural && !player.isNatural -> {
                println("Dealer has natural, player does not - dealer wins!")
                ++dealerWins
            }
            player.value > dealer.value -> {
                println("Player wins!")
                ++playerWins
            }
            dealer.value > player.value -> {
                println("Dealer wins!")
                ++dealerWins
            }
            else -> {
                println("Tie!")
                ++ties
            }
        }
    }

    private fun finish() {
        // Extension function to pluralize a string
        fun String.s(n: Int) = if (n != 1) this + "s" else this

        println("\nGAME OVER\n")

        round.let { println("$it round".s(it) + " played") }
        playerWins.let { println("$it player win".s(it)) }
        dealerWins.let { println("$it dealer win".s(it)) }
        ties.let { println("$it tie".s(it)) }
    }
}
