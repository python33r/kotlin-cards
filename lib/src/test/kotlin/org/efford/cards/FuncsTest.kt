package org.efford.cards

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeSorted
import io.kotest.matchers.shouldBe

@Suppress("unused")
class FuncsTest: StringSpec({
    val cards = deckOf<Card>().toList()

    "deckOf() yields a 52-card sequence" {
        withClue("Size") { cards.size shouldBe 52 }
    }

    "deckOf() yields cards from all the suits" {
        withClue("AC") {
            (Card(Card.Rank.ACE, Card.Suit.CLUBS) in cards) shouldBe true
        }
        withClue("2D") {
            (Card(Card.Rank.TWO, Card.Suit.DIAMONDS) in cards) shouldBe true
        }
        withClue("TH") {
            (Card(Card.Rank.TEN, Card.Suit.HEARTS) in cards) shouldBe true
        }
        withClue("KS") {
            (Card(Card.Rank.KING, Card.Suit.SPADES) in cards) shouldBe true
        }
    }

    "deckOf() yields cards in their natural order" {
        cards.shouldBeSorted()
    }
})
