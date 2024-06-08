package org.efford.cards.blackjack

import org.efford.cards.Card
import org.efford.cards.CardCollection

class BlackjackHand(): CardCollection<Card>() {
    /**
     * Creates a BlackjackHand from the given Card instances.
     *
     * @param[card] One or more instances of Card
     */
    constructor(vararg card: Card): this() {
        card.forEach { this.add(it) }
    }

    /**
     * Value of this hand
     *
     * Aces score 11 or 1, with the lower value being used if necessary
     * to bring the value of the hand below 21.
     */
    override val value: Int
        get() {
            var sum = cards.sumOf { if (it.rank == Card.Rank.ACE) 11 else it.value }
            var softAces = cards.count { it.rank == Card.Rank.ACE }
            while (softAces > 0 && sum > 21) {
                --softAces
                sum -= 10
            }
            return sum
        }

    /**
     * Indicator of whether this hand is a natural or not
     */
    val isNatural get() = size == 2 && value == 21

    /**
     * Indicator of whether this hand is bust or not
     */
    val isBust get() = value > 21

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
