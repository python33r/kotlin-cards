package org.efford.cards.blackjack

fun main() {
    val deck = Deck()
    deck.shuffle()

    val hand = BlackjackHand()

    repeat(10) {
        deck.deal()?.let { hand.add(it) }
        deck.deal()?.let { hand.add(it) }
        deck.deal()?.let { hand.add(it) }

        print("$hand = ${hand.value}")

        when(hand.isBust) {
            true -> println("  Bust")
            else -> println()
        }

        hand.discard()
    }
}
