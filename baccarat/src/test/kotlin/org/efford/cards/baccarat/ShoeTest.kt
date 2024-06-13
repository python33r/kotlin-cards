package org.efford.cards.baccarat

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldNotBeSorted
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

import org.efford.cards.Card
import org.efford.cards.CardCollection

@Suppress("unused")
class ShoeTest: StringSpec({
    isolationMode = IsolationMode.InstancePerTest

    val aceClubs = BaccaratCard(Card.Rank.ACE, Card.Suit.CLUBS)
    val twoClubs = BaccaratCard(Card.Rank.TWO, Card.Suit.CLUBS)
    val aceDiamonds = BaccaratCard(Card.Rank.ACE, Card.Suit.DIAMONDS)
    val nineHearts = BaccaratCard(Card.Rank.NINE, Card.Suit.HEARTS)
    val kingSpades = BaccaratCard(Card.Rank.KING, Card.Suit.SPADES)

    val cardsInADeck = 52

    val shoe = Shoe()

    "Shoe derives from CardCollection<BaccaratCard>" {
        shoe.shouldBeInstanceOf<CardCollection<BaccaratCard>>()
    }

    "Default six-deck shoe can be created" {
        withClue("Number of cards in shoe") { shoe.size shouldBe 6 * cardsInADeck }
    }

    "Larger eight-deck shoe can be created" {
        val bigShoe = Shoe(8)
        withClue("Number of cards in shoe") { bigShoe.size shouldBe 8 * cardsInADeck }
    }

    "Shoe sizes other than 6 or 8 are not permitted" {
        val badSizes = (1..5).toList() + 7 + 9
        for (size in badSizes) {
            withClue("Size=$size") {
                val error = shouldThrow<IllegalArgumentException> { Shoe(size) }
                error.message shouldBe "Invalid number of decks"
            }
        }
    }

    "Shoe contains cards from all the suits" {
        withClue("AC") { (aceClubs in shoe) shouldBe true }
        withClue("2C") { (twoClubs in shoe) shouldBe true }
        withClue("AD") { (aceDiamonds in shoe) shouldBe true }
        withClue("9H") { (nineHearts in shoe) shouldBe true }
        withClue("KS") { (kingSpades in shoe) shouldBe true }
    }

    "Cards can be dealt from shoe" {
        val initialSize = shoe.size
        withClue("First card dealt") { shoe.deal() shouldBe aceClubs }
        withClue("Second card dealt") { shoe.deal() shouldBe twoClubs }
        withClue("Shoe size") { shoe.size shouldBe initialSize - 2 }
    }

    "Dealing from an empty shoe returns null" {
        shoe.discard()
        shoe.deal() shouldBe null
    }

    "Shoe can be shuffled" {
        shoe.shuffle()
        val cards = mutableListOf<BaccaratCard>()
        repeat(10) {
            shoe.deal()?.let {
                cards.add(it)
            }
        }
        cards.shouldNotBeSorted()
    }
})
