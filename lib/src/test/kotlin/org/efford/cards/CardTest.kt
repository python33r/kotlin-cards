package org.efford.cards

import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

import org.efford.cards.Card.Rank.*
import org.efford.cards.Card.Suit.*

@Suppress("unused")
class CardTest: StringSpec({
    val aceClubs = Card(ACE, CLUBS)
    val twoClubs = Card(TWO, CLUBS)
    val aceDiamonds = Card(ACE, DIAMONDS)
    val twoDiamonds = Card(TWO, DIAMONDS)
    val nineHearts = Card(NINE, HEARTS)
    val tenHearts = Card(TEN, HEARTS)
    val kingSpades = Card(KING, SPADES)

    "Cards can be created with a rank and a suit" {
        withClue("AC") {
            aceClubs.rank shouldBe ACE
            aceClubs.suit shouldBe CLUBS
        }
        withClue("TH") {
            tenHearts.rank shouldBe TEN
            tenHearts.suit shouldBe HEARTS
        }
    }

    "Cards have the correct full name" {
        aceClubs.fullName shouldBe "Ace of Clubs"
        twoDiamonds.fullName shouldBe "Two of Diamonds"
        tenHearts.fullName shouldBe "Ten of Hearts"
        kingSpades.fullName shouldBe "King of Spades"
    }

    "Cards have the correct string representation" {
        aceClubs.toString() shouldBe "A\u2663"
        twoDiamonds.toString() shouldBe "2\u2666"
        tenHearts.toString() shouldBe "T\u2665"
        kingSpades.toString() shouldBe "K\u2660"
    }

    "Cards can be represented with plainer strings" {
        aceClubs.plainString() shouldBe "AC"
        twoDiamonds.plainString() shouldBe "2D"
        tenHearts.plainString() shouldBe "TH"
        kingSpades.plainString() shouldBe "KS"
    }

    "Cards have the correct values" {
        withClue("AC") { aceClubs.value shouldBe 1 }
        withClue("AD") { aceDiamonds.value shouldBe 1 }
        withClue("2D") { twoDiamonds.value shouldBe 2 }
        withClue("9H") { nineHearts.value shouldBe 9 }
        withClue("TH") { tenHearts.value shouldBe 10 }
        withClue("KS") { kingSpades.value shouldBe 10 }
    }

    "A card can be tested for equality" {
        withClue("With itself") {
            (aceClubs == aceClubs) shouldBe true
        }
        withClue("With other card of same rank & suit") {
            (aceClubs == Card(ACE, CLUBS)) shouldBe true
            (aceClubs == Card(ACE, CLUBS)) shouldBe true
        }
        withClue("With other card of same suit & different rank") {
            (aceClubs == twoClubs) shouldBe false
        }
        withClue("With other card of same rank & different suit") {
            (aceClubs == aceDiamonds) shouldBe false
        }
        withClue("With its string representation") {
            aceClubs.equals("AC") shouldBe false
        }
    }

    "Cards can be compared" {
        aceClubs shouldBeEqualComparingTo aceClubs
        aceClubs shouldBeEqualComparingTo Card(ACE, CLUBS)
        tenHearts shouldBeEqualComparingTo tenHearts

        aceClubs shouldBeLessThan twoClubs
        twoClubs shouldBeLessThan aceDiamonds

        twoClubs shouldBeGreaterThan aceClubs
        aceDiamonds shouldBeGreaterThan twoClubs
    }

    "Cards have sensible hash codes" {
        withClue("Hashes for same rank & suit") {
            aceClubs.hashCode() shouldBe Card(ACE, CLUBS).hashCode()
        }
        withClue("Hashes for different ranks") {
            aceClubs.hashCode() shouldNotBe twoClubs.hashCode()
        }
        withClue("Hashes for different suits") {
            aceClubs.hashCode() shouldNotBe aceDiamonds.hashCode()
        }
    }
})
