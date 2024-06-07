package org.efford.cards.baccarat

/**
 * Simulator for a simplified version of Baccarat.
 */
class BaccaratGame(numDecks: Int = 6) {
    companion object {
        const val MIN_SHOE_SIZE = 6
    }

    private val shoe = Shoe(numDecks).apply { shuffle() }
    private val player = BaccaratHand()
    private val banker = BaccaratHand()

    private var round = 0
    private var playerWins = 0
    private var bankerWins = 0
    private var ties = 0

    /**
     * Plays the game, in either interactive or non-interactive mode.
     *
     * In interactive mode, the user is asked after each round whether
     * they wish the game to continue or not.
     *
     * In non-interactive mode (the default), the game will run to completion
     * without any user interaction.
     *
     * In either mode, the game will stop once there are no longer enough
     * cards in the shoe to guarantee a complete round of play.
     *
     * @param[interact] `true` if interactive mode is required, `false` otherwise
     */
    fun play(interact: Boolean = false) {
        while (shoe.size >= MIN_SHOE_SIZE) {
            playRound()
            if (interact && playerWantsToQuit()) {
                break
            }
        }
        displayStats()
    }

    private fun playRound() {
        startRound()
        dealInitialCards()
        reportNaturals()
        if (nobodyHasNatural()) {
            dealExtraCards()
        }
        updateStats()
        endRound()
    }

    private fun startRound() {
        ++round
        println("\nRound $round")
    }

    private fun dealInitialCards() {
        repeat(2) {
            shoe.deal()?.let { player.add(it) }
            shoe.deal()?.let { banker.add(it) }
        }
        showHands()
    }

    private fun showHands() {
        println("Player: $player = ${player.value}")
        println("Banker: $banker = ${banker.value}")
    }

    private fun reportNaturals() {
        if (player.isNatural) println("Player has a Natural")
        if (banker.isNatural) println("Banker has a Natural")
    }

    private fun nobodyHasNatural() = !(player.isNatural || banker.isNatural)

    private fun dealExtraCards() {
        val dealToPlayer = player.value < 6
        var dealToBanker = false

        if (dealToPlayer) {
            shoe.deal()?.let {
                println("Dealing third card to player...")
                player.add(it)
                dealToBanker = when {
                    banker.value <= 2 -> true
                    banker.value == 3 && it.value != 8 -> true
                    banker.value == 4 && it.value in 2..7 -> true
                    banker.value == 5 && it.value in 4..7 -> true
                    banker.value == 6 && it.value in 6..7 -> true
                    else -> false
                }
            }
        }
        else if (banker.value < 6) {
            dealToBanker = true
        }

        if (dealToBanker) {
            shoe.deal()?.let {
                println("Dealing third card to banker...")
                banker.add(it)
            }
        }

        if (dealToPlayer || dealToBanker) {
            showHands()
        }
    }

    private fun updateStats() {
        if (player.value > banker.value) {
            println("Player win!")
            ++playerWins
        }
        else if (banker.value > player.value) {
            println("Banker win!")
            ++bankerWins
        }
        else {
            println("Tie")
            ++ties
        }
    }

    private fun endRound() {
        player.discard()
        banker.discard()
    }

    private fun displayStats() {
        // Extension function to pluralize a string
        fun String.s(n: Int) = if (n != 1) this + "s" else this

        round.let { println("\n$it round".s(it) + " played") }
        playerWins.let { println("$it player win".s(it)) }
        bankerWins.let { println("$it banker win".s(it)) }
        ties.let { println("$it tie".s(it)) }
    }

    private fun playerWantsToQuit(): Boolean {
        print("\n[Q]uit or [P]lay another round?: ")
        val response = readlnOrNull()?.lowercase() ?: ""
        return response.startsWith("q")
    }
}
