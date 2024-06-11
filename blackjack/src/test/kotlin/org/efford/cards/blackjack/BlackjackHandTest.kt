package org.efford.cards.blackjack

import io.kotest.assertions.withClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import org.efford.cards.Card
import org.efford.cards.CardCollection

@Suppress("unused")
class BlackjackHandTest: StringSpec({
    isolationMode = IsolationMode.InstancePerTest

    val aceClubs = Card(Card.Rank.ACE, Card.Suit.CLUBS)
    val aceDiamonds = Card(Card.Rank.ACE, Card.Suit.DIAMONDS)
    val twoClubs = Card(Card.Rank.TWO, Card.Suit.CLUBS)
    val threeSpades = Card(Card.Rank.THREE, Card.Suit.SPADES)
    val eightDiamonds = Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS)
    val nineHearts = Card(Card.Rank.NINE, Card.Suit.HEARTS)
    val tenHearts = Card(Card.Rank.TEN, Card.Suit.HEARTS)
    val tenSpades = Card(Card.Rank.TEN, Card.Suit.SPADES)
    val queenHearts = Card(Card.Rank.QUEEN, Card.Suit.HEARTS)

    val emptyHand = BlackjackHand()
    val aceHand = BlackjackHand(aceClubs)
    val aceNine = BlackjackHand(aceClubs, nineHearts)
    val aceTen = BlackjackHand(aceDiamonds, tenSpades)
    val aceQueen = BlackjackHand(aceClubs, queenHearts)
    val twoTens = BlackjackHand(tenHearts, tenSpades)
    val threeTenEight = BlackjackHand(threeSpades, tenHearts, eightDiamonds)
    val justBust = BlackjackHand(twoClubs, tenSpades, queenHearts)
    val clearlyBust = BlackjackHand(tenHearts, tenSpades, eightDiamonds)
    val aceAceTen = BlackjackHand(aceClubs, aceDiamonds, tenSpades)
    val aceAceNine = BlackjackHand(aceClubs, aceDiamonds, nineHearts)

    "BlackjackHand derives from CardCollection<Card>" {
        emptyHand.shouldBeInstanceOf<CardCollection<Card>>()
    }

    "Hands can be created with & without cards" {
        withClue("Size") {
            emptyHand.size shouldBe 0
            aceHand.size shouldBe 1
            twoTens.size shouldBe 2
        }
    }

    "Hand values are computed correctly" {
        withClue("Empty") { emptyHand.value shouldBe 0 }
        withClue("AC") { aceHand.value shouldBe 11 }
        withClue("TH,TS") { twoTens.value shouldBe 20 }
        withClue("AD,TS") { aceTen.value shouldBe 21 }
        withClue("AC QH") { aceQueen.value shouldBe 21 }
        withClue("3S,TH,8D") { threeTenEight.value shouldBe 21 }
        withClue("2C,TS,QH") { justBust.value shouldBe 22 }
        withClue("TH,TS,8D") { clearlyBust.value shouldBe 28 }
        withClue("AC,AD,TS") { aceAceTen.value shouldBe 12 }
        withClue("AC,AD,9H") { aceAceNine.value shouldBe 21 }
    }

    "Non-natural hands are recognised correctly" {
        withClue("AC a Natural?") {
            aceHand.isNatural shouldBe false
        }
        withClue("AC,9H a Natural?") {
            aceNine.isNatural shouldBe false
        }
        withClue("TH,TS a Natural?") {
            twoTens.isNatural shouldBe false
        }
        withClue("3S,TH,8D a Natural?") {
            threeTenEight.isNatural shouldBe false
        }
        withClue("AC,AD,9H a Natural?") {
            aceAceNine.isNatural shouldBe false
        }
        withClue("AC,AD,TS a Natural?") {
            aceAceTen.isNatural shouldBe false
        }
    }

    "Natural hands are recognised correctly" {
        withClue("AC,QH a Natural?") {
            aceQueen.isNatural shouldBe true
        }
        withClue("AD,TS a Natural?") {
            aceTen.isNatural shouldBe true
        }
    }

    "Bust hands are recognised correctly" {
        withClue("2C,TS,QH is Bust?") { justBust.isBust shouldBe true }
        withClue("TH,TS,8D is Bust?") { clearlyBust.isBust shouldBe true }
    }

    "Hands have the correct string representation" {
        emptyHand.toString() shouldBe ""
        aceQueen.toString() shouldBe "A\u2663 Q\u2665"
        threeTenEight.toString() shouldBe "3\u2660 T\u2665 8\u2666"
    }

    "Hands can be represented as plainer strings correctly" {
        emptyHand.plainString() shouldBe ""
        aceQueen.plainString() shouldBe "AC QH"
        threeTenEight.plainString() shouldBe "3S TH 8D"
    }
})
