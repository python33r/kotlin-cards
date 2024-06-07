package org.efford.cards

/**
 * A collection of cards.
 */
abstract class CardCollection<T: Card> {
    protected val cards = mutableListOf<T>()

    /**
     * Number of cards in this collection
     */
    val size get() = cards.size

    /**
     * Indicator of whether this collection is empty or not
     */
    val isEmpty get() = cards.isEmpty()

    /**
     * Value of this collection
     *
     * By default, this is simply the sum of the card values.
     */
    open val value get() = cards.sumOf { it.value }

    /**
     * Indicates whether this collection contains a card.
     *
     * @param[card] Card being tested
     * @return `true` if specified card is in the collection, `false` otherwise
     */
    infix fun contains(card: T) = cards.contains(card)

    /**
     * Adds the specified card to this collection.
     *
     * @param[card] Card to be added
     */
    open fun add(card: T) = cards.add(card)

    /**
     * Empties this collection of cards.
     */
    open fun discard() = cards.clear()

    /**
     * Sorts this collection into its natural order.
     */
    fun sort() = cards.sort()
}

/**
 * Generates a full deck of 52 cards, in sequence.
 *
 * @return Sequence of instances of Card (or a subtype)
 */
inline fun <reified T: Card> deckOf(): Sequence<T> = sequence {
    for (suit in Card.Suit.entries) {
        for (rank in Card.Rank.entries) {
            yield(T::class.constructors.first().call(rank, suit))
        }
    }
}
