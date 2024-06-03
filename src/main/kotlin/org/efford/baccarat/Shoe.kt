package org.efford.baccarat

/**
 * A shoe of cards for the game of Baccarat.
 */
class Shoe(val numDecks: Int = 6): CardCollection() {
    init {
        require(numDecks == 6 || numDecks == 8) { "invalid number of decks" }
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
     */
    fun deal(): Card {
        // TODO: check for empty list and throw custom exception here?
        return cards.removeFirst()
    }

    /**
     * Rearranges cards in this shoe randomly.
     */
    fun shuffle() = cards.shuffle()
}
