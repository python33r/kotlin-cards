package org.efford.cards.baccarat

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import org.efford.cards.Card
import org.efford.cards.Card.Rank.*
import org.efford.cards.Card.Suit.*

@Suppress("unused")
class BaccaratCardTest: StringSpec({
    val aceClubs = BaccaratCard(ACE, CLUBS)
    val aceDiamonds = BaccaratCard(ACE, DIAMONDS)
    val twoDiamonds = BaccaratCard(TWO, DIAMONDS)
    val nineHearts = BaccaratCard(NINE, HEARTS)
    val tenHearts = BaccaratCard(TEN, HEARTS)
    val kingSpades = BaccaratCard(KING, SPADES)

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
