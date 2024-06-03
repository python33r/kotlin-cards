package org.efford.baccarat

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldNotBeSorted
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ShoeTest: StringSpec({
    isolationMode = IsolationMode.InstancePerTest

    val aceClubs = BaccaratCard(Card.Rank.ACE, Card.Suit.CLUBS)
    val twoClubs = BaccaratCard(Card.Rank.TWO, Card.Suit.CLUBS)
    val aceDiamonds = BaccaratCard(Card.Rank.ACE, Card.Suit.DIAMONDS)
    val nineHearts = BaccaratCard(Card.Rank.NINE, Card.Suit.HEARTS)
    val kingSpades = BaccaratCard(Card.Rank.KING, Card.Suit.SPADES)

    val cardsInADeck = 52

    val shoe = Shoe()

    "Shoe derives from CardCollection" {
        shoe.shouldBeInstanceOf<CardCollection>()
    }

    "Default six-deck shoe can be created" {
        withClue("Number of decks") { shoe.numDecks shouldBe 6 }
        withClue("Number of cards in shoe") { shoe.size shouldBe 6 * cardsInADeck }
    }

    "Larger eight-deck shoe can be created" {
        val bigShoe = Shoe(8)
        withClue("Number of decks") { bigShoe.numDecks shouldBe 8 }
        withClue("Number of cards in shoe") { bigShoe.size shouldBe 8 * cardsInADeck }
    }

    "Shoe sizes other than 6 or 8 are not permitted" {
        val badSizes = (1..5).toList() + 7 + 9
        for (size in badSizes) {
            withClue("Size=$size") {
                shouldThrow<IllegalArgumentException> { Shoe(size) }
            }
        }
    }

    "Shoe contains cards from all the suits" {
        withClue("AC") { shoe contains aceClubs shouldBe true }
        withClue("2C") { shoe contains twoClubs shouldBe true }
        withClue("AD") { shoe contains aceDiamonds shouldBe true }
        withClue("9H") { shoe contains nineHearts shouldBe true }
        withClue("KS") { shoe contains kingSpades shouldBe true }
    }

    "Cards can be dealt from shoe" {
        val initialSize = shoe.size
        withClue("First card dealt") { shoe.deal() shouldBe aceClubs }
        withClue("Second card dealt") { shoe.deal() shouldBe twoClubs }
        withClue("Shoe size") { shoe.size shouldBe initialSize - 2 }
    }

    "Shoe can be emptied" {
        shoe.discard()
        withClue("Shoe size") { shoe.size shouldBe 0 }
    }

    "Shoe can be shuffled" {
        shoe.shuffle()
        val cards = Array(cardsInADeck) { shoe.deal() }.toList()
        cards.shouldNotBeSorted()
    }
})
