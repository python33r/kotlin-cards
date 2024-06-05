package org.efford.baccarat

/**
 * A shoe of cards for the game of Baccarat.
 *
 * Shoes can be created using either six or eight full decks of cards.
 * If you don't specify the number of decks, six is assumed.
 *
 * @param[numDecks] Number of decks to use
 */
class Shoe(numDecks: Int = 6): CardCollection<BaccaratCard>() {
    init {
        require(numDecks == 6 || numDecks == 8) { "Invalid number of decks" }
        repeat(numDecks) {
            for (suit in Card.Suit.entries) {
                for (rank in Card.Rank.entries) {
                    cards.add(BaccaratCard(rank, suit))
                }
            }
        }
    }

    /**
     * Removes and returns top card of this shoe.
     *
     * @return Card at the top of the shoe, or `null` if shoe is empty
     */
    fun deal(): BaccaratCard? = cards.removeFirstOrNull()

    /**
     * Rearranges cards in this shoe randomly.
     *
     * Shoe contents are ordered on creation, so this method needs to
     * be called before using the shoe in a game.
     */
    fun shuffle() = cards.shuffle()
}
