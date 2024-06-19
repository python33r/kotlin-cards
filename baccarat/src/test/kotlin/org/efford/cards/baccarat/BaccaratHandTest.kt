package org.efford.cards.baccarat

import io.kotest.assertions.withClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import org.efford.cards.Card.Rank.*
import org.efford.cards.Card.Suit.*
import org.efford.cards.CardCollection

@Suppress("unused")
class BaccaratHandTest: StringSpec({
    isolationMode = IsolationMode.InstancePerTest

    val hand = BaccaratHand()
    val handOneCard = BaccaratHand()
    val handTwoCards = BaccaratHand()

    handOneCard.add(BaccaratCard(NINE, DIAMONDS))
    handTwoCards.add(BaccaratCard(NINE, DIAMONDS))
    handTwoCards.add(BaccaratCard(TWO, CLUBS))

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
                add(BaccaratCard(THREE, HEARTS))
                add(BaccaratCard(FIVE, CLUBS))
            }.isNatural shouldBe true
        }
        withClue("8S,JD a Natural?") {
            BaccaratHand().apply {
                add(BaccaratCard(EIGHT, SPADES))
                add(BaccaratCard(JACK, DIAMONDS))
            }.isNatural shouldBe true
        }
        withClue("8S,AD a Natural?") {
            BaccaratHand().apply {
                add(BaccaratCard(EIGHT, SPADES))
                add(BaccaratCard(ACE, DIAMONDS))
            }.isNatural shouldBe true
        }
    }
})
