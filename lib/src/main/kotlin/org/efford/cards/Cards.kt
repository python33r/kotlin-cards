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
    operator fun contains(card: T) = cards.contains(card)

    /**
     * Adds the specified card to this collection.
     *
     * @param[card] Card to be added
     */
    open fun add(card: T) {
        cards.add(card)
    }

    /**
     * Empties this collection of cards.
     */
    open fun discard() = cards.clear()

    /**
     * Sorts this collection into its natural order.
     */
    fun sort() = cards.sort()

    /**
     * Generates the default string representation of this collection.
     *
     * @return Collection as a string
     */
    override fun toString() = cards.joinToString(" ")

    /**
     * Generates a configurable string representation of this collection.
     *
     * The string separating each card must be specified. Prefix and
     * suffix strings can optionally be provided.
     *
     * @param[sep] String that separates the cards
     * @param[start] Optional prefix string
     * @param[end] Optional suffix string
     * @return Collection as a string
     */
    fun toString(sep: String, start: String = "", end: String = ""): String {
        require(sep.isNotEmpty()) { "Separator cannot be empty" }
        return start + cards.joinToString(sep) + end
    }

    /**
     * Generates a plainer string representation of this collection.
     *
     * Characters `'C'`, `'D'`, `'H'`, `'S'` are substituted for the fancier
     * Unicode suit symbols. If no card separator string is provided,
     * cards will be separated by a single space. Prefix and suffix strings
     * can optionally be provided.
     *
     * @param[sep] Optional card separator string
     * @param[start] Optional prefix string
     * @param[end] Optional suffix string
     * @return Collection as a plainer string
     */
    fun plainString(sep: String = " ", start: String = "", end: String = ""): String {
        require(sep.isNotEmpty()) { "Separator cannot be empty" }
        return start + cards.joinToString(sep) { it.plainString() } + end
    }
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
