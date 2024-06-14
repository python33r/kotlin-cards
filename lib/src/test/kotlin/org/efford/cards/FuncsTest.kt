package org.efford.cards

import io.kotest.assertions.withClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.*
import io.kotest.matchers.shouldBe

@Suppress("unused")
class FuncsTest: StringSpec({
    val aceClubs = Card(Card.Rank.ACE, Card.Suit.CLUBS)
    val twoDiamonds = Card(Card.Rank.TWO, Card.Suit.DIAMONDS)
    val tenHearts = Card(Card.Rank.TEN, Card.Suit.HEARTS)
    val kingSpades = Card(Card.Rank.KING, Card.Suit.SPADES)

    val deck = deckOf<Card>().toList()
    val clubs = suitOf<Card>(Card.Suit.CLUBS).toList()
    val diamonds = suitOf<Card>(Card.Suit.DIAMONDS).toList()
    val hearts = suitOf<Card>(Card.Suit.HEARTS).toList()
    val spades = suitOf<Card>(Card.Suit.SPADES).toList()

    "deckOf() yields a full sorted 52-card sequence" {
        deck.shouldHaveSize(52)
        deck.shouldBeUnique()
        deck.shouldBeSorted()
    }

    "suitOf() yields a full sorted sequence of Clubs" {
        clubs.shouldHaveSize(13)
        clubs.shouldBeUnique()
        clubs.shouldBeSorted()
        clubs.forAll {
            it.suit shouldBe Card.Suit.CLUBS
        }
    }

    "suitOf() yields a full sorted sequence of Diamonds" {
        diamonds.shouldHaveSize(13)
        diamonds.shouldBeUnique()
        diamonds.shouldBeSorted()
        diamonds.forAll {
            it.suit shouldBe Card.Suit.DIAMONDS
        }
    }

    "suitOf() yields a full sorted sequence of Hearts" {
        hearts.shouldHaveSize(13)
        hearts.shouldBeUnique()
        hearts.shouldBeSorted()
        hearts.forAll {
            it.suit shouldBe Card.Suit.HEARTS
        }
    }

    "suitOf() yields a full sorted sequence of Spades" {
        spades.shouldHaveSize(13)
        spades.shouldBeUnique()
        spades.shouldBeSorted()
        spades.forAll {
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
