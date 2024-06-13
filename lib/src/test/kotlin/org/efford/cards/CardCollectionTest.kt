package org.efford.cards

import io.kotest.assertions.withClue
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@Suppress("unused")
class CardCollectionTest: StringSpec({
    isolationMode = IsolationMode.InstancePerTest

    val nineDiamonds = Card(Card.Rank.NINE, Card.Suit.DIAMONDS)
    val twoClubs = Card(Card.Rank.TWO, Card.Suit.CLUBS)
    val kingSpades = Card(Card.Rank.KING, Card.Suit.SPADES)
    val aceHearts = Card(Card.Rank.ACE, Card.Suit.HEARTS)

    val col = object: CardCollection<Card>() {}

    val col1 = object: CardCollection<Card>() {}.apply {
        add(nineDiamonds)
    }

    val col2 = object: CardCollection<Card>() {}.apply {
        add(nineDiamonds)
        add(twoClubs)
    }

    val col3 = object: CardCollection<Card>() {}.apply {
        add(nineDiamonds)
        add(twoClubs)
        add(kingSpades)
    }

    val col4 = object: CardCollection<Card>() {}.apply {
        add(nineDiamonds)
        add(twoClubs)
        add(kingSpades)
        add(aceHearts)
    }

    "A collection starts off empty" {
        withClue("Size") { col.size shouldBe 0 }
        withClue("Is empty?") { col.isEmpty shouldBe true }
    }

    "Cards can be added to a collection" {
        withClue("Collection size") {
            col1.size shouldBe 1
            col2.size shouldBe 2
        }
    }

    "Collection values are computed correctly" {
        withClue("Empty") { col.value shouldBe 0 }
        withClue("9D") { col1.value shouldBe 9 }
        withClue("9D,2C") { col2.value shouldBe 11 }
        withClue("9D,2C,KS") { col3.value shouldBe 21 }
    }

    "Card containment is checked correctly" {
        withClue("9D in []?") { (nineDiamonds in col) shouldBe false }
        withClue("9D in [9D]?") { (nineDiamonds in col1) shouldBe true }
        withClue("2C in [9D,2C]?") { (twoClubs in col2) shouldBe true }
    }

    "Collection contents can be discarded" {
        withClue("Size before") { col3.size shouldBe 3 }
        col3.discard()
        withClue("Size after") { col3.size shouldBe 0 }
    }

    "Collections have the correct default string representation" {
        col.toString() shouldBe ""
        col1.toString() shouldBe "9\u2666"
        col2.toString() shouldBe "9\u2666 2\u2663"
        col3.toString() shouldBe "9\u2666 2\u2663 K\u2660"
        col4.toString() shouldBe "9\u2666 2\u2663 K\u2660 A\u2665"
    }

    "String representation can be configured" {
        col2.toString(",") shouldBe "9\u2666,2\u2663"
        col3.toString(",") shouldBe "9\u2666,2\u2663,K\u2660"
        col3.toString(", ") shouldBe "9\u2666, 2\u2663, K\u2660"
        col3.toString(",", "[", "]") shouldBe "[9\u2666,2\u2663,K\u2660]"
        col3.toString(":", "<", ">") shouldBe "<9\u2666:2\u2663:K\u2660>"
    }

    "Default plain string representation is correct" {
        col.plainString() shouldBe ""
        col1.plainString() shouldBe "9D"
        col2.plainString() shouldBe "9D 2C"
        col3.plainString() shouldBe "9D 2C KS"
        col4.plainString() shouldBe "9D 2C KS AH"
    }

    "Plain string representation can be configured" {
        col2.plainString(",") shouldBe "9D,2C"
        col3.plainString(",") shouldBe "9D,2C,KS"
        col3.plainString(", ") shouldBe "9D, 2C, KS"
        col3.plainString(",", "[", "]") shouldBe "[9D,2C,KS]"
        col3.plainString(":", "<", ">") shouldBe "<9D:2C:KS>"
    }
})
