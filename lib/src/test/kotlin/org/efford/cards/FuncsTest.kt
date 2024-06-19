package org.efford.cards

import io.kotest.assertions.withClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.collections.*

import org.efford.cards.Card.Rank.*
import org.efford.cards.Card.Suit.*

@Suppress("unused")
class FuncsTest: StringSpec({
    val aceClubs = Card(ACE, CLUBS)
    val twoDiamonds = Card(TWO, DIAMONDS)
    val tenHearts = Card(TEN, HEARTS)
    val kingSpades = Card(KING, SPADES)

    val deck = deckOf<Card>().toList()
    val clubs = suitOf<Card>(CLUBS).toList()
    val diamonds = suitOf<Card>(DIAMONDS).toList()
    val hearts = suitOf<Card>(HEARTS).toList()
    val spades = suitOf<Card>(SPADES).toList()

    "deckOf() yields a full sorted 52-card sequence" {
        deck.let {
            it.shouldHaveSize(52)
            it.shouldBeUnique()
            it.shouldBeSorted()
        }
    }

    "suitOf() yields a full sorted sequence of Clubs" {
        clubs.let {
            it.shouldHaveSize(13)
            it.shouldBeUnique()
            it.shouldBeSorted()
        }.forAll {
            it.suit shouldBe Card.Suit.CLUBS
        }
    }

    "suitOf() yields a full sorted sequence of Diamonds" {
        diamonds.let {
            it.shouldHaveSize(13)
            it.shouldBeUnique()
            it.shouldBeSorted()
        }.forAll {
            it.suit shouldBe Card.Suit.DIAMONDS
        }
    }

    "suitOf() yields a full sorted sequence of Hearts" {
        hearts.let {
            it.shouldHaveSize(13)
            it.shouldBeUnique()
            it.shouldBeSorted()
        }.forAll {
            it.suit shouldBe Card.Suit.HEARTS
        }
    }

    "suitOf() yields a full sorted sequence of Spades" {
        spades.let {
            it.shouldHaveSize(13)
            it.shouldBeUnique()
            it.shouldBeSorted()
        }.forAll {
            it.suit shouldBe Card.Suit.SPADES
        }
    }

    "stringTo() produces card from a valid string" {
        stringTo<Card>("A\u2663") shouldBe aceClubs
        stringTo<Card>("K\u2660") shouldBe kingSpades
    }

    "stringTo() produces card from a valid plain string" {
        stringTo<Card>("AC") shouldBe aceClubs
        stringTo<Card>("KS") shouldBe kingSpades
    }

    "Exception from stringTo() with string of wrong length" {
        withClue("\"\"") {
            shouldThrow<IllegalArgumentException> {
                stringTo<Card>("")
            }
        }
        withClue("\"AC.\"") {
            shouldThrow<IllegalArgumentException> {
                stringTo<Card>("AC.")
            }
        }
    }

    "Exception from stringTo() with an invalid rank" {
        withClue("\"1C\"") {
            shouldThrow<IllegalArgumentException> {
                stringTo<Card>("1C")
            }
        }
    }

    "Exception from stringTo() with an invalid suit" {
        withClue("\"AB\"") {
            shouldThrow<IllegalArgumentException> {
                stringTo<Card>("AB")
            }
        }
    }

    "stringToSequence() can produce an empty sequence" {
        stringToSequence<Card>("").toList().shouldBeEmpty()
        stringToSequence<Card>(" ").toList().shouldBeEmpty()
    }

    "stringToSequence() can produce a one-card sequence" {
        stringToSequence<Card>("AC").toList().shouldContainExactly(aceClubs)
    }

    "stringToSequence() can produce a three-card sequence" {
        stringToSequence<Card>("AC 2D TH").toList()
            .shouldContainExactly(aceClubs, twoDiamonds, tenHearts)
    }

    "stringToSequence() can handle \",\" separator" {
        stringToSequence<Card>("AC,2D,TH", sep=",").toList()
            .shouldContainExactly(aceClubs, twoDiamonds, tenHearts)
    }

    "stringToSequence() can handle \"[\" prefix & \"]\" suffix" {
        stringToSequence<Card>("[AC 2D TH]", start="[", end="]").toList()
            .shouldContainExactly(aceClubs, twoDiamonds, tenHearts)
    }

    "Exception if card separator not provided when needed" {
        shouldThrow<IllegalArgumentException> {
            stringToSequence<Card>("AC,2D,TH").toList()
        }
    }

    "Exception if prefix/suffix not provided when needed" {
        shouldThrow<IllegalArgumentException> {
            stringToSequence<Card>("[AC 2D TH").toList()
        }
        shouldThrow<IllegalArgumentException> {
            stringToSequence<Card>("AC 2D TH]").toList()
        }
    }
})
