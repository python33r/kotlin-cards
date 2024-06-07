package org.efford.baccarat

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import org.efford.cards.Card

@Suppress("unused")
class BaccaratCardTest: StringSpec({
    val aceClubs = BaccaratCard(Card.Rank.ACE, Card.Suit.CLUBS)
    val aceDiamonds = BaccaratCard(Card.Rank.ACE, Card.Suit.DIAMONDS)
    val twoDiamonds = BaccaratCard(Card.Rank.TWO, Card.Suit.DIAMONDS)
    val nineHearts = BaccaratCard(Card.Rank.NINE, Card.Suit.HEARTS)
    val tenHearts = BaccaratCard(Card.Rank.TEN, Card.Suit.HEARTS)
    val kingSpades = BaccaratCard(Card.Rank.KING, Card.Suit.SPADES)

    "BaccaratCard derives from Card" {
        aceClubs.shouldBeInstanceOf<Card>()
    }

    "Cards have the correct values" {
        withClue("AC") { aceClubs.value shouldBe 1 }
        withClue("AD") { aceDiamonds.value shouldBe 1 }
        withClue("2D") { twoDiamonds.value shouldBe 2 }
        withClue("9H") { nineHearts.value shouldBe 9 }
        withClue("TH") { tenHearts.value shouldBe 0 }
        withClue("KS") { kingSpades.value shouldBe 0 }
    }
})
