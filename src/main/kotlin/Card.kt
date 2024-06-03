import kotlin.math.min

/**
 * A playing card.
 *
 * @property[rank] Rank of the card
 * @property[suit] Suit of the card
 */
open class Card(val rank: Rank, val suit: Suit): Comparable<Card> {
    /**
     * Rank of a playing card.
     */
    enum class Rank(val symbol: Char) {
        ACE('A'), TWO('2'), THREE('3'), FOUR('4'), FIVE('5'),
        SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9'), TEN('T'),
        JACK('J'), QUEEN('Q'), KING('K');

        override fun toString() = name.lowercase().replaceFirstChar(Char::titlecase)
    }

    /**
     * Suit of a playing card.
     */
    enum class Suit(val symbol: Char) {
        CLUBS('\u2663'), DIAMONDS('\u2666'),
        HEARTS('\u2665'), SPADES('\u2660');

        override fun toString() = name.lowercase().replaceFirstChar(Char::titlecase)
    }

    /**
     * Full name of this card (e.g., "Ace of Clubs").
     */
    val fullName = "$rank of $suit"

    /**
     * Value of this card.
     *
     * Value is the number of 'pips' on the card. Picture cards
     * (Jack, Queen, King) have a value of 10.
     */
    open val value = min(rank.ordinal + 1, 10)

    /**
     * Generates a two-character string representation of this card.
     *
     * @return Card as a string
     */
    override fun toString() = "${rank.symbol}${suit.symbol}"

    /**
     * Generates a plain two-character string representation of this card.
     *
     * Characters 'C', 'D', 'H', 'S' are substituted for the fancier
     * Unicode suit symbols.
     *
     * @return Card as a plain string
     */
    fun plainString() = "${rank.symbol}${suit.name[0]}"

    /**
     * Tests whether this card is equal to another object.
     *
     * @param[other] Object with which this card is being compared
     * @return true if object is a card with same rank & suit, false otherwise
     */
    override fun equals(other: Any?) = when(other) {
        is Card -> rank == other.rank && suit == other.suit
        else    -> false
    }

    /**
     * Computes the hash code of this card.
     *
     * @return Hash code
     */
    override fun hashCode() = java.util.Objects.hash(rank, suit)

    /**
     * Compares this card with another.
     *
     * @param[other] Card with which this card is being compared
     * @return 0 if the cards are the same, a negative value if this card comes
     *           before the other, a positive value if this card comes after
     */
    override fun compareTo(other: Card): Int {
        val diff = suit.compareTo(other.suit)
        return if (diff == 0) rank.compareTo(other.rank) else diff
    }
}
