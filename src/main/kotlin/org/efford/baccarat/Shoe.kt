package org.efford.baccarat

/**
 * A shoe of cards for the game of Baccarat.
 *
 * Shoes can be created using either six or eight full decks of cards.
 * If you don't specify the number of decks, six is assumed.
 *
 * @property[numDecks] Number of decks to use
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
     *
     * @return Card at the top of the shoe
     */
    fun deal(): Card {
        return cards.removeFirst()
    }

    /**
     * Rearranges cards in this shoe randomly.
     *
     * Shoe contents are ordered on creation, so this method needs to
     * be called before using the shoe in a game.
     */
    fun shuffle() = cards.shuffle()
}
