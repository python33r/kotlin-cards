package org.efford.baccarat

import io.kotest.assertions.withClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class BaccaratHandTest: StringSpec({
    isolationMode = IsolationMode.InstancePerTest

    val hand = BaccaratHand()
    val handOneCard = BaccaratHand()
    val handTwoCards = BaccaratHand()

    handOneCard.add(BaccaratCard(Card.Rank.NINE, Card.Suit.DIAMONDS))
    handTwoCards.add(BaccaratCard(Card.Rank.NINE, Card.Suit.DIAMONDS))
    handTwoCards.add(BaccaratCard(Card.Rank.TWO, Card.Suit.CLUBS))

    "A hand starts off empty" {
        withClue("Hand size") { hand.size shouldBe 0 }
    }

    "Cards can be added to a hand" {
        withClue("Hand size") {
            handOneCard.size shouldBe 1
            handTwoCards.size shouldBe 2
        }
    }

    "Hand values are computed correctly" {
        withClue("Empty") { hand.value shouldBe 0 }
        withClue("9D") { handOneCard.value shouldBe 9 }
        withClue("9D 2C") { handTwoCards.value shouldBe 1 }
    }

    "Natural hands are detected correctly" {
        withClue("Empty hand a Natural?") { hand.isNatural shouldBe false }
        withClue("Single-card hand a Natural?") { handOneCard.isNatural shouldBe false }
        withClue("9D,2C a Natural?") { handTwoCards.isNatural shouldBe false }

        val nat1 = BaccaratHand().apply {
            add(BaccaratCard(Card.Rank.THREE, Card.Suit.HEARTS))
            add(BaccaratCard(Card.Rank.FIVE, Card.Suit.CLUBS))
        }
        val nat2 = BaccaratHand().apply {
            add(BaccaratCard(Card.Rank.EIGHT, Card.Suit.SPADES))
            add(BaccaratCard(Card.Rank.JACK, Card.Suit.DIAMONDS))
        }
        withClue("3H,5C a Natural?") { nat1.isNatural shouldBe true }
        withClue("8S,JD a Natural?") { nat2.isNatural shouldBe true }
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