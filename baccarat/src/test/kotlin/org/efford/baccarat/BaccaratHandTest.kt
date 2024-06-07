package org.efford.baccarat

import io.kotest.assertions.withClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import org.efford.cards.Card
import org.efford.cards.CardCollection

@Suppress("unused")
class BaccaratHandTest: StringSpec({
    isolationMode = IsolationMode.InstancePerTest

    val hand = BaccaratHand()
    val handOneCard = BaccaratHand()
    val handTwoCards = BaccaratHand()

    handOneCard.add(BaccaratCard(Card.Rank.NINE, Card.Suit.DIAMONDS))
    handTwoCards.add(BaccaratCard(Card.Rank.NINE, Card.Suit.DIAMONDS))
    handTwoCards.add(BaccaratCard(Card.Rank.TWO, Card.Suit.CLUBS))

    "BaccaratHand derives from CardCollection<BaccaratCard>" {
        hand.shouldBeInstanceOf<CardCollection<BaccaratCard>>()
    }

    "Hand values are computed correctly" {
        withClue("Empty") { hand.value shouldBe 0 }
        withClue("9D") { handOneCard.value shouldBe 9 }
        withClue("9D,2C") { handTwoCards.value shouldBe 1 }
    }

    "Non-natural hands are recognised correctly" {
        withClue("Empty hand a Natural?") { hand.isNatural shouldBe false }
        withClue("Single-card hand a Natural?") { handOneCard.isNatural shouldBe false }
        withClue("9D,2C a Natural?") { handTwoCards.isNatural shouldBe false }
    }

    "Natural hands are recognised correctly" {
        withClue("3H,5C a Natural?") {
            BaccaratHand().apply {
                add(BaccaratCard(Card.Rank.THREE, Card.Suit.HEARTS))
                add(BaccaratCard(Card.Rank.FIVE, Card.Suit.CLUBS))
            }.isNatural shouldBe true
        }
        withClue("8S,JD a Natural?") {
            BaccaratHand().apply {
                add(BaccaratCard(Card.Rank.EIGHT, Card.Suit.SPADES))
                add(BaccaratCard(Card.Rank.JACK, Card.Suit.DIAMONDS))
            }.isNatural shouldBe true
        }
        withClue("8S,AD a Natural?") {
            BaccaratHand().apply {
                add(BaccaratCard(Card.Rank.EIGHT, Card.Suit.SPADES))
                add(BaccaratCard(Card.Rank.ACE, Card.Suit.DIAMONDS))
            }.isNatural shouldBe true
        }
    }

    "Hands have the correct string representation" {
        hand.toString() shouldBe ""
        handOneCard.toString() shouldBe "9\u2666"
        handTwoCards.toString() shouldBe "9\u2666 2\u2663"
    }

    "Hands have the correct plain string representation" {
        hand.plainString() shouldBe ""
        handOneCard.plainString() shouldBe "9D"
        handTwoCards.plainString() shouldBe "9D 2C"
    }
})
