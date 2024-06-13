package org.efford.cards.blackjack

import io.kotest.assertions.withClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldNotBeSorted
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import org.efford.cards.Card
import org.efford.cards.CardCollection

@Suppress("unused")
class DeckTest: StringSpec({
    isolationMode = IsolationMode.InstancePerTest

    val aceClubs = Card(Card.Rank.ACE, Card.Suit.CLUBS)
    val twoClubs = Card(Card.Rank.TWO, Card.Suit.CLUBS)
    val aceDiamonds = Card(Card.Rank.ACE, Card.Suit.DIAMONDS)
    val nineHearts = Card(Card.Rank.NINE, Card.Suit.HEARTS)
    val kingSpades = Card(Card.Rank.KING, Card.Suit.SPADES)

    val fullDeckSize = 52

    val deck = Deck()

    "Deck derives from CardCollection<Card>" {
        deck.shouldBeInstanceOf<CardCollection<Card>>()
    }

    "Full deck of cards can be created" {
        withClue("Size") { deck.size shouldBe fullDeckSize }
    }

    "Deck contains cards from all the suits" {
        withClue("AC") { (aceClubs in deck) shouldBe true }
        withClue("AD") { (aceDiamonds in deck) shouldBe true }
        withClue("9H") { (nineHearts in deck) shouldBe true }
        withClue("KS") { (kingSpades in deck) shouldBe true }
    }

    "Cards can be dealt from deck" {
        withClue("First card dealt") { deck.deal() shouldBe aceClubs }
        withClue("Second card dealt") { deck.deal() shouldBe twoClubs }
        withClue("Deck size") { deck.size shouldBe fullDeckSize - 2 }
    }

    "Dealing from an empty deck returns null" {
        deck.discard()
        deck.deal() shouldBe null
    }

    "Deck can be shuffled" {
        deck.shuffle()
        val cards = mutableListOf<Card>()
        repeat(10) {
            deck.deal()?.let {
                cards.add(it)
            }
        }
        cards.shouldNotBeSorted()
    }
})
