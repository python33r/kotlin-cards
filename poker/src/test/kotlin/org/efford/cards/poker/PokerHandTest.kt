package org.efford.cards.poker

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import org.efford.cards.Card
import org.efford.cards.CardCollection

@Suppress("unused")
class PokerHandTest: StringSpec({
    isolationMode = IsolationMode.InstancePerTest

    val aceClubs = Card(Card.Rank.ACE, Card.Suit.CLUBS)
    val aceDiamonds = Card(Card.Rank.ACE, Card.Suit.DIAMONDS)
    val aceHearts = Card(Card.Rank.ACE, Card.Suit.HEARTS)
    val aceSpades = Card(Card.Rank.ACE, Card.Suit.SPADES)
    val twoClubs = Card(Card.Rank.TWO, Card.Suit.CLUBS)
    val twoDiamonds = Card(Card.Rank.TWO, Card.Suit.DIAMONDS)
    val threeDiamonds = Card(Card.Rank.THREE, Card.Suit.DIAMONDS)
    val fourSpades = Card(Card.Rank.FOUR, Card.Suit.SPADES)
    val fiveClubs = Card(Card.Rank.FIVE, Card.Suit.CLUBS)
    val fiveHearts = Card(Card.Rank.FIVE, Card.Suit.HEARTS)
    val fiveSpades = Card(Card.Rank.FIVE, Card.Suit.SPADES)
    val sixClubs = Card(Card.Rank.SIX, Card.Suit.CLUBS)
    val sevenClubs = Card(Card.Rank.SEVEN, Card.Suit.CLUBS)
    val sevenSpades = Card(Card.Rank.SEVEN, Card.Suit.SPADES)
    val nineClubs = Card(Card.Rank.NINE, Card.Suit.CLUBS)
    val tenDiamonds = Card(Card.Rank.TEN, Card.Suit.DIAMONDS)
    val jackSpades = Card(Card.Rank.JACK, Card.Suit.SPADES)
    val queenHearts = Card(Card.Rank.QUEEN, Card.Suit.HEARTS)
    val kingSpades = Card(Card.Rank.KING, Card.Suit.SPADES)

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
            withClue("\"AC\": card present?") { contains(aceClubs) shouldBe true }
        }
        with(PokerHand("AC 2D")) {
            withClue("\"AC 2D\" size") { size shouldBe 2 }
            withClue("\"AC 2D\": cards present?") {
                contains(aceClubs) shouldBe true
                contains(twoDiamonds) shouldBe true
            }
        }
        with(PokerHand("AC,2D")) {
            withClue("\"AC,2D\" size") { size shouldBe 2 }
            withClue("\"AC,2D\": cards present?") {
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
        withClue("Too many cards") {
            shouldThrow<IllegalArgumentException> {
                PokerHand("AC 2D 5H 7S 9C KC")
            }
        }
    }

    "Adding a card to a full hand causes an exception" {
        shouldThrow<IllegalArgumentException> {
            fiveCards.add(Card(Card.Rank.KING, Card.Suit.CLUBS))
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
            withClue("AC AD 5C 5H 5S") { isFullHouse shouldBe true }
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
