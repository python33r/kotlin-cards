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

    val col = object: CardCollection<Card>() {}

    val col1 = object: CardCollection<Card>() {}.apply {
        add(nineDiamonds)
    }

    val col2 = object: CardCollection<Card>() {}.apply {
        add(nineDiamonds)
        add(twoClubs)
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
    }

    "Card containment is checked correctly" {
        withClue("9D in []?") {
            (nineDiamonds in col) shouldBe false
        }
        withClue("9D in [9D]?") {
            (nineDiamonds in col1) shouldBe true
        }
        withClue("2C in [9D,2C]?") {
            (twoClubs in col2) shouldBe true
        }
    }

    "Collection contents can be discarded" {
        col2.discard()
        withClue("Size") { col2.size shouldBe 0 }
    }
})
