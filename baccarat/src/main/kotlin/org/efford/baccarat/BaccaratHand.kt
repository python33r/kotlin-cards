package org.efford.baccarat

import org.efford.cards.CardCollection

/**
 * A hand of cards in the game of Baccarat.
 */
class BaccaratHand: CardCollection<BaccaratCard>() {
    /**
     * Value of this hand
     *
     * Hands in Baccarat have a value between 0 and 9.
     */
    override val value: Int get() = cards.sumOf { it.value } % 10

    /**
     * Whether this hand is a Natural or not
     *
     * Naturals in Baccarat occur when a two-card hand has a value of 8 or 9.
     */
    val isNatural: Boolean get() = size == 2 && (value == 8 || value == 9)

    /**
     * Generates a string representation of this hand.
     *
     * @return Hand as a string
     */
    override fun toString() = cards.joinToString(" ")

    /**
     * Generates a plainer string representation of this hand.
     *
     * Characters `'C'`, `'D'`, `'H'`, `'S'` are substituted for the fancier
     * Unicode suit symbols.
     *
     * @return Hand as a plainer string
     */
    fun plainString(): String = cards.joinToString(" ") { it.plainString() }
}
