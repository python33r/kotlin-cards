package org.efford.cards.poker

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import org.efford.cards.Card
import org.efford.cards.Card.Rank.*
import org.efford.cards.Card.Suit.*
import org.efford.cards.CardCollection

@Suppress("unused")
class PokerHandTest: StringSpec({
    isolationMode = IsolationMode.InstancePerTest

    val aceClubs = Card(ACE, CLUBS)
    val aceDiamonds = Card(ACE, DIAMONDS)
    val aceHearts = Card(ACE, HEARTS)
    val aceSpades = Card(ACE, SPADES)
    val twoClubs = Card(TWO, CLUBS)
    val twoDiamonds = Card(TWO, DIAMONDS)
    val threeDiamonds = Card(THREE, DIAMONDS)
    val fourSpades = Card(FOUR, SPADES)
    val fiveClubs = Card(FIVE, CLUBS)
    val fiveHearts = Card(FIVE, HEARTS)
    val fiveSpades = Card(FIVE, SPADES)
    val sixClubs = Card(SIX, CLUBS)
    val sevenClubs = Card(SEVEN, CLUBS)
    val sevenSpades = Card(SEVEN, SPADES)
    val nineClubs = Card(NINE, CLUBS)
    val tenDiamonds = Card(TEN, DIAMONDS)
    val jackSpades = Card(JACK, SPADES)
    val queenHearts = Card(QUEEN, HEARTS)
    val kingSpades = Card(KING, SPADES)

    val empty = PokerHand()
    val oneCard = PokerHand(aceClubs)
    val twoCards = PokerHand(aceClubs, twoDiamonds)
    val threeAces = PokerHand(aceClubs, aceDiamonds, aceHearts)
    val fourAces = PokerHand(aceClubs, aceDiamonds, aceHearts, aceSpades)
    val fourCards = PokerHand(aceClubs, twoDiamonds, fiveHearts, aceSpades)
    val fiveCards = PokerHand(aceClubs, twoDiamonds, fiveHearts, sevenSpades, nineClubs)
    val pair = PokerHand(aceClubs, aceDiamonds, fiveHearts, sevenSpades, nineClubs)
    val twoPairs = PokerHand(aceClubs, aceDiamonds, fiveHearts, fiveSpades, nineClubs)
    val threeOfAKind = PokerHand(aceClubs, aceDiamonds, aceHearts, sevenSpades, nineClubs)
    val fourOfAKind = PokerHand(aceClubs, aceDiamonds, aceHearts, aceSpades, nineClubs)
    val fullHouse = PokerHand(aceClubs, aceHearts, aceSpades, fiveHearts, fiveSpades)

    "PokerHand derives from CardCollection<Card>" {
        empty.shouldBeInstanceOf<CardCollection<Card>>()
    }

    "Hands can be created with & without cards" {
        withClue("Empty size") {
            empty.size shouldBe 0
        }
        with(oneCard) {
            withClue("1 card size") { size shouldBe 1 }
            withClue("1 card: AC present?") { contains(aceClubs) shouldBe true }
        }
        with(twoCards) {
            withClue("2 cards size") { size shouldBe 2 }
            withClue("2 cards: AC & 2D present?") {
                contains(aceClubs) shouldBe true
                contains(twoDiamonds) shouldBe true
            }
        }
    }

    "Hands can be created from strings" {
        with(PokerHand("")) {
            withClue("Empty size") { size shouldBe 0 }
        }
        with(PokerHand("AC")) {
            withClue("\"AC\" size") { size shouldBe 1 }
            withClue("\"AC\": AC present?") { contains(aceClubs) shouldBe true }
        }
        with(PokerHand("AC 2D")) {
            withClue("\"AC 2D\" size") { size shouldBe 2 }
            withClue("\"AC 2D\": AC & 2D present?") {
                contains(aceClubs) shouldBe true
                contains(twoDiamonds) shouldBe true
            }
        }
    }

    "Hands can be created from strings with different separator" {
        with(PokerHand("AC,2D", sep=",")) {
            withClue("\"AC,2D\" size") { size shouldBe 2 }
            withClue("\"AC,2D\": AC & 2D present?") {
                contains(aceClubs) shouldBe true
                contains(twoDiamonds) shouldBe true
            }
        }
    }

    "Hands can be created from strings with prefix/suffix" {
        with(PokerHand("[AC 2D]", start="[", end="]")) {
            withClue("\"[AC 2D]\" size") { size shouldBe 2 }
            withClue("\"[AC 2D]\": AC & 2D present?") {
                contains(aceClubs) shouldBe true
                contains(twoDiamonds) shouldBe true
            }
        }
        with(PokerHand("<AC:2D>", sep=":", start="<", end=">")) {
            withClue("\"<AC:2D>\" size") { size shouldBe 2 }
            withClue("\"<AC:2D>\": AC & 2D present?") {
                contains(aceClubs) shouldBe true
                contains(twoDiamonds) shouldBe true
            }
        }
    }

    "Invalid string specifications cause an exception" {
        withClue("Bad card specification") {
            shouldThrow<IllegalArgumentException> {
                PokerHand("1C")
            }
        }
        withClue("Separator not specified") {
            shouldThrow<IllegalArgumentException> {
                PokerHand("AC,2D")
            }
        }
        withClue("Prefix/suffix not specified") {
            shouldThrow<IllegalArgumentException> {
                PokerHand("[AC 2D]")
            }
        }
        withClue("Too many cards") {
            shouldThrow<IllegalArgumentException> {
                PokerHand("AC 2D 5H 7S 9C KC")
            }
        }
    }

    "Adding a card to a full hand causes an exception" {
        shouldThrow<IllegalArgumentException> {
            fiveCards.add(Card(KING, CLUBS))
        }
    }

    "Hands can be emptied" {
        fiveCards.discard()
        withClue("Size") { fiveCards.size shouldBe 0 }
    }

    "Pair is detected correctly" {
        withClue("Empty") { empty.isPair shouldBe false }
        withClue("AC,2D,5H,AS") { fourCards.isPair shouldBe false }
        withClue("AC,2D,5H,7S,9C") { fiveCards.isPair shouldBe false }
        withClue("AC,AD,5H,7S,9C") { pair.isPair shouldBe true }
        withClue("AC,AD,5H,5S,9C") { twoPairs.isPair shouldBe false }
        withClue("AC,AD,AH,7S,9C") { threeOfAKind.isPair shouldBe false }
        withClue("AC,AD,AH,AS,9C") { fourOfAKind.isPair shouldBe false }
        withClue("AC,AH,AS,5H,5S") { fullHouse.isPair shouldBe false }
    }

    "Two Pairs is detected correctly" {
        withClue("Empty") { empty.isTwoPairs shouldBe false }
        withClue("AC,2D,5H,AS") { fourCards.isTwoPairs shouldBe false }
        with(PokerHand(aceClubs, aceDiamonds, fiveHearts, fiveSpades)) {
            withClue("AC,AD,5H,5S") { isTwoPairs shouldBe false }
        }
        withClue("AC,2D,5H,7S,9C") { fiveCards.isTwoPairs shouldBe false }
        withClue("AC,AD,5H,7S,9C") { pair.isTwoPairs shouldBe false }
        withClue("AC,AD,5H,5S,9C") { twoPairs.isTwoPairs shouldBe true }
        withClue("AC,AD,AH,7S,9C") { threeOfAKind.isTwoPairs shouldBe false }
        withClue("AC,AD,AH,AS,9C") { fourOfAKind.isTwoPairs shouldBe false }
        withClue("AC,AH,AS,5H,5S") { fullHouse.isTwoPairs shouldBe false }
    }

    "Three of a Kind is detected correctly" {
        withClue("Empty") { empty.isThreeOfAKind shouldBe false }
        withClue("AC,AD,AH") { threeAces.isThreeOfAKind shouldBe false }
        withClue("AC,AD,AH,AS") { fourAces.isThreeOfAKind shouldBe false }
        withClue("AC,2D,5H,7S,9C") { fiveCards.isThreeOfAKind shouldBe false }
        withClue("AC,AD,5H,7S,9C") { pair.isThreeOfAKind shouldBe false }
        withClue("AC,AD,5H,5S,9C") { twoPairs.isThreeOfAKind shouldBe false }
        withClue("AC,AD,AH,7S,9C") { threeOfAKind.isThreeOfAKind shouldBe true }
        withClue("AC,AD,AH,AS,9C") { fourOfAKind.isThreeOfAKind shouldBe false }
        withClue("AC,AH,AS,5H,5S") { fullHouse.isThreeOfAKind shouldBe false }
        with(PokerHand(aceClubs, fiveClubs, fiveHearts, fiveSpades, sevenSpades)) {
            withClue("AC 5C 5H 5S 7S") { isThreeOfAKind shouldBe true }
        }
    }

    "Four of a Kind is detected correctly" {
        withClue("Empty") { empty.isFourOfAKind shouldBe false }
        withClue("AC,AD,AH") { threeAces.isFourOfAKind shouldBe false }
        withClue("AC,AD,AH,AS") { fourAces.isFourOfAKind shouldBe false }
        withClue("AC,2D,5H,7S,9C") { fiveCards.isFourOfAKind shouldBe false }
        withClue("AC,AD,5H,7S,9C") { pair.isFourOfAKind shouldBe false }
        withClue("AC,AD,5H,5S,9C") { twoPairs.isFourOfAKind shouldBe false }
        withClue("AC,AD,AH,7S,9C") { threeOfAKind.isFourOfAKind shouldBe false }
        withClue("AC,AD,AH,AS,9C") { fourOfAKind.isFourOfAKind shouldBe true }
        withClue("AC,AH,AS,5H,5S") { fullHouse.isFourOfAKind shouldBe false }
    }

    "Full House is detected correctly" {
        withClue("Empty") { empty.isFullHouse shouldBe false }
        withClue("AC,AD,AH") { threeAces.isFullHouse shouldBe false }
        withClue("AC,AD,AH,AS") { fourAces.isFullHouse shouldBe false }
        withClue("AC,2D,5H,7S,9C") { fiveCards.isFullHouse shouldBe false }
        withClue("AC,AD,5H,7S,9C") { pair.isFullHouse shouldBe false }
        withClue("AC,AD,5H,5S,9C") { twoPairs.isFullHouse shouldBe false }
        withClue("AC,AD,AH,7S,9C") { threeOfAKind.isFullHouse shouldBe false }
        withClue("AC,AD,AH,AS,9C") { fourOfAKind.isFullHouse shouldBe false }
        withClue("AC,AH,AS,5H,5S") { fullHouse.isFullHouse shouldBe true }
        with(PokerHand(aceClubs, aceDiamonds, fiveClubs, fiveHearts, fiveSpades)) {
            withClue("AC,AD,5C,5H,5S") { isFullHouse shouldBe true }
        }
    }

    "Flush is detected correctly" {
        with(PokerHand(aceClubs, twoClubs, fiveClubs, sevenClubs)) {
            withClue("AC,2C,5C,7C") { isFlush shouldBe false }
        }
        with(PokerHand(aceClubs, twoClubs, fiveClubs, sevenClubs, twoDiamonds)) {
            withClue("AC,2C,5C,7C,2D") { isFlush shouldBe false }
        }
        with(PokerHand(aceClubs, twoClubs, fiveClubs, sevenClubs, nineClubs)) {
            withClue("AC,2C,5C,7C,9C") { isFlush shouldBe true }
        }
        with(PokerHand(aceSpades, fiveSpades, sevenSpades, jackSpades, kingSpades)) {
            withClue("AS,5S,7S,JS,KS") { isFlush shouldBe true }
        }
    }

    "Straight is detected correctly" {
        with(PokerHand(twoDiamonds, threeDiamonds, fourSpades, fiveHearts)) {
            withClue("2D,3D,4S,5H") { isStraight shouldBe false }
        }
        with(PokerHand(twoDiamonds, threeDiamonds, fourSpades, fiveHearts, sevenClubs)) {
            withClue("2D,3D,4S,5H,7C") { isStraight shouldBe false }
        }
        with(PokerHand(twoDiamonds, threeDiamonds, fourSpades, fiveHearts, sixClubs)) {
            withClue("2D,3D,4S,5H,6C") { isStraight shouldBe true }
        }
        with(PokerHand(aceClubs, twoDiamonds, threeDiamonds, fourSpades, fiveHearts)) {
            withClue("AC,2D,3D,4S,5H") { isStraight shouldBe true }
        }
    }

    "High-ace Straight is detected correctly" {
        with(PokerHand(tenDiamonds, jackSpades, queenHearts, kingSpades, aceClubs)) {
            withClue("TD,JS,QH,KS,AC") { isStraight shouldBe true }
        }
    }
})
