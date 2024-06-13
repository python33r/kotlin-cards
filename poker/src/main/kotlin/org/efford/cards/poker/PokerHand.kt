package org.efford.cards.poker

import org.efford.cards.Card
import org.efford.cards.CardCollection
import org.efford.cards.stringTo

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
     * to specify suit. These two-character representations must be
     * separated from each other by either a single space or a comma.
     *
     * Example: `"AC JH 7D"` or `"QS,TH,AD,7C"`
     *
     * @param[str] String specifying the cards to be added
     */
    constructor(str: String): this() {
        if (str.isNotBlank()) {
            val cardStrings = str.trim().split(" ", ",")
            require(cardStrings.size <= 5) { "Too many cards" }
            cardStrings.forEach {
                add(stringTo<Card>(it))
            }
        }
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
