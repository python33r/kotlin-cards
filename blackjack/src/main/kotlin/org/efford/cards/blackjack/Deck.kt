package org.efford.cards.blackjack

import org.efford.cards.Card
import org.efford.cards.CardCollection
import org.efford.cards.deckOf

/**
 * A deck of 52 playing cards.
 */
class Deck: CardCollection<Card>() {
    init {
        cards.addAll(deckOf<Card>())
    }

    /**
     * Deals the card from this deck.
     *
     * @return Card at the top of the deck, or null if the deck is empty
     */
    fun deal(): Card? = cards.removeFirstOrNull()

    /**
     * Shuffles this deck's contents into a random order.
     */
    fun shuffle() = cards.shuffle()
}
