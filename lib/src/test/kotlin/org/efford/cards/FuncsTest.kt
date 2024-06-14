package org.efford.cards

import io.kotest.assertions.withClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldBeSorted
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

@Suppress("unused")
class FuncsTest: StringSpec({
    val aceClubs = Card(Card.Rank.ACE, Card.Suit.CLUBS)
    val twoDiamonds = Card(Card.Rank.TWO, Card.Suit.DIAMONDS)
    val tenHearts = Card(Card.Rank.TEN, Card.Suit.HEARTS)
    val kingSpades = Card(Card.Rank.KING, Card.Suit.SPADES)

    val deck = deckOf<Card>().toList()

    "deckOf() yields a 52-card sequence" {
        deck.size shouldBe 52
    }

    "deckOf() yields cards from all the suits" {
        deck.shouldContain(aceClubs)
        deck.shouldContain(twoDiamonds)
        deck.shouldContain(tenHearts)
        deck.shouldContain(kingSpades)
    }

    "deckOf() yields cards in their natural order" {
        deck.shouldBeSorted()
    }

    "stringTo() with a valid string" {
        stringTo<Card>("A\u2663") shouldBe aceClubs
        stringTo<Card>("K\u2660") shouldBe kingSpades
    }

    "stringTo() with a valid plain string" {
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

    "stringToSequence() with no cards" {
        stringToSequence<Card>("").toList().shouldBeEmpty()
        stringToSequence<Card>(" ").toList().shouldBeEmpty()
    }

    "stringToSequence() with one card" {
        stringToSequence<Card>("AC").toList().shouldContainExactly(aceClubs)
    }

    "stringToSequence() with three cards" {
        stringToSequence<Card>("AC 2D TH").toList()
            .shouldContainExactly(aceClubs, twoDiamonds, tenHearts)
    }

    "stringToSequence() with \",\" separator" {
        stringToSequence<Card>("AC,2D,TH", sep=",").toList()
            .shouldContainExactly(aceClubs, twoDiamonds, tenHearts)
    }

    "stringToSequence() with \"[\" prefix & \"]\" suffix" {
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
