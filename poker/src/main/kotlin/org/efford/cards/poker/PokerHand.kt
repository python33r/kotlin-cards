package org.efford.cards.poker

import org.efford.cards.Card
import org.efford.cards.CardCollection
import org.efford.cards.stringToSequence

class PokerHand(): CardCollection<Card>() {

    private val rankCount = mutableMapOf<Card.Rank, Int>()
    private val suitCount = mutableMapOf<Card.Suit, Int>()

    /**
     * Creates a PokerHand from the given Card instances.
     *
     * @param[card] One or more instances of Card
     */
    constructor(vararg card: Card): this() {
        require(card.size <= 5) { "Too many cards" }
        card.forEach { add(it) }
    }

    /**
     * Creates a PokerHand from a string representation of cards.
     *
     * Cards are specified using their two-character representations,
     * using either Unicode symbols or `'C'`, `'D'`, `'H'`, `'S'`
     * to specify suit. By default, it is assumed that these two-character
     * representations are separated from each other by a single space,
     * and that there are no characters with before the first card or
     * after the last card.
     *
     * Example of default format: `"AC JH 7D"`
     *
     * Examples of other supported formats: `"AC, JH, 7D"`, `"[AC:JH:7D]"`
     *
     * @param[str] String specifying the cards to be added
     * @param[sep] String separating the cards (defaults to a space)
     * @param[start] Prefix (defaults to empty string)
     * @param[end] Suffix (defaults to empty string)
     */
    constructor(str: String, sep: String = " ", start: String = "", end: String = ""): this() {
        stringToSequence<Card>(str, sep, start, end).forEach { add(it) }
    }

    /**
     * Adds the specified card to this hand.
     *
     * @param[card] Card to be added
     */
    override fun add(card: Card) {
        require(size < 5) { "Hand already full" }
        super.add(card)
        rankCount.compute(card.rank) { _, count -> count?.plus(1) ?: 1 }
        suitCount.compute(card.suit) { _, count -> count?.plus(1) ?: 1 }
    }

    /**
     * Empties this hand of its cards.
     */
    override fun discard() {
        super.discard()
        rankCount.clear()
        suitCount.clear()
    }

    /**
     * Indicator of whether this hand is a Pair
     */
    val isPair: Boolean
        get() = size == 5 && numPairs == 1 && !rankCount.containsValue(3)

    /**
     * Indicator of whether this hand is Two Pairs
     */
    val isTwoPairs: Boolean
        get() = size == 5 && numPairs == 2

    private val numPairs get() = rankCount.values.count { it == 2 }

    /**
     * Indicator of whether this hand is Three of a Kind
     */
    val isThreeOfAKind: Boolean
        get() = size == 5 && rankCount.containsValue(3) && !rankCount.containsValue(2)

    /**
     * Indicator of whether this hand is Four of a Kind
     */
    val isFourOfAKind: Boolean
        get() = size == 5 && rankCount.containsValue(4)

    /**
     * Indicator of whether this hand is a Full House
     */
    val isFullHouse: Boolean
        get() = size == 5 && rankCount.containsValue(3) && rankCount.containsValue(2)

    /**
     * Indicator of whether this hand is a Flush
     */
    val isFlush: Boolean
        get() = size == 5 && suitCount.containsValue(5)

    /**
     * Indicator of whether this hand is a Straight
     */
    val isStraight: Boolean
        get() = if (size != 5) {
            false
        }
        else {
            val ranks = IntArray(5) { i -> cards[i].rank.ordinal }
            val highRanks = ranks.map { r -> if (r == 0) 13 else r }.toIntArray()
            consecutive(ranks) || consecutive(highRanks)
        }

    private fun consecutive(values: IntArray): Boolean {
        values.sort()
        return values[1] - values[0] == 1
                && values[2] - values[1] == 1
                && values[3] - values[2] == 1
                && values[4] - values[3] == 1
    }
}
