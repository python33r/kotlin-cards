package org.efford.cards.baccarat

import org.efford.cards.Card

/**
 * A playing card used in Baccarat.
 */
class BaccaratCard(rank: Rank, suit: Suit): Card(rank, suit) {
    /**
     * Value of this card
     *
     * Tens and picture cards have a value of 0 in Baccarat.
     * All other cards have their natural value (number of pips).
     */
    override val value = when(rank) {
        Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING -> 0
        else -> rank.ordinal + 1
    }
}
