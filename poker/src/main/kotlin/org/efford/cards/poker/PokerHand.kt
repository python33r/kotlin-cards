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
        require(card.size <= 5) { "too many cards" }
        card.forEach { add(it) }
    }

    /**
     * Creates a PokerHand from the given cards.
     *
     * Cards are specified using their two-character representations
     * and must be separated by whitespace.
     *
     * @param[str] String specifying the cards to be added - e.g., "AC JH 7D"
     */
    constructor(str: String): this() {
        val cardStrings = str.trim().split("\\s+".toRegex())
        require(cardStrings.size <= 5) { "too many cards" }
        cardStrings.forEach {
            add(stringTo<Card>(it))
        }
    }

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
