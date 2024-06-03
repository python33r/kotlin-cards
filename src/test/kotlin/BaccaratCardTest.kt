import io.kotest.assertions.withClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeEqualComparingTo
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf

class BaccaratCardTest: StringSpec({
    val aceClubs = BaccaratCard(Card.Rank.ACE, Card.Suit.CLUBS)
    val twoClubs = BaccaratCard(Card.Rank.TWO, Card.Suit.CLUBS)
    val aceDiamonds = BaccaratCard(Card.Rank.ACE, Card.Suit.DIAMONDS)
    val twoDiamonds = BaccaratCard(Card.Rank.TWO, Card.Suit.DIAMONDS)
    val nineHearts = BaccaratCard(Card.Rank.NINE, Card.Suit.HEARTS)
    val tenHearts = BaccaratCard(Card.Rank.TEN, Card.Suit.HEARTS)
    val kingSpades = BaccaratCard(Card.Rank.KING, Card.Suit.SPADES)

    "BaccaratCard derives from Card" {
        aceClubs.shouldBeInstanceOf<Card>()
    }

    "Cards can be created with a rank and a suit" {
        withClue("AC") {
            aceClubs.rank shouldBe Card.Rank.ACE
            aceClubs.suit shouldBe Card.Suit.CLUBS
        }
        withClue("TH") {
            tenHearts.rank shouldBe Card.Rank.TEN
            tenHearts.suit shouldBe Card.Suit.HEARTS
        }
    }

    "Cards have the correct full name" {
        aceClubs.fullName shouldBe "Ace of Clubs"
        twoDiamonds.fullName shouldBe "Two of Diamonds"
        tenHearts.fullName shouldBe "Ten of Hearts"
        kingSpades.fullName shouldBe "King of Spades"
    }

    "Cards have the correct string representation" {
        aceClubs.toString() shouldBe "A\u2663"
        twoDiamonds.toString() shouldBe "2\u2666"
        tenHearts.toString() shouldBe "T\u2665"
        kingSpades.toString() shouldBe "K\u2660"
    }

    "Cards can be represented with plainer strings" {
        aceClubs.plainString() shouldBe "AC"
        twoDiamonds.plainString() shouldBe "2D"
        tenHearts.plainString() shouldBe "TH"
        kingSpades.plainString() shouldBe "KS"
    }

    "Cards have the correct values" {
        withClue("AC") { aceClubs.value shouldBe 1 }
        withClue("AD") { aceDiamonds.value shouldBe 1 }
        withClue("2D") { twoDiamonds.value shouldBe 2 }
        withClue("9H") { nineHearts.value shouldBe 9 }
        withClue("TH") { tenHearts.value shouldBe 0 }
        withClue("KS") { kingSpades.value shouldBe 0 }
    }

    "A card can be tested for equality" {
        withClue("With itself") {
            (aceClubs == aceClubs) shouldBe true
        }
        withClue("With other card of same rank & suit") {
            (aceClubs == BaccaratCard(Card.Rank.ACE, Card.Suit.CLUBS)) shouldBe true
            (aceClubs == Card(Card.Rank.ACE, Card.Suit.CLUBS)) shouldBe true
        }
        withClue("With other card of same suit & different rank") {
            (aceClubs == twoClubs) shouldBe false
        }
        withClue("With other card of same rank & different suit") {
            (aceClubs == aceDiamonds) shouldBe false
        }
        withClue("With its string representation") {
            aceClubs.equals("AC") shouldBe false
        }
    }

    "Cards can be compared" {
        aceClubs shouldBeEqualComparingTo aceClubs
        aceClubs shouldBeEqualComparingTo BaccaratCard(Card.Rank.ACE, Card.Suit.CLUBS)
        aceClubs shouldBeEqualComparingTo Card(Card.Rank.ACE, Card.Suit.CLUBS)
        tenHearts shouldBeEqualComparingTo tenHearts

        aceClubs shouldBeLessThan twoClubs
        twoClubs shouldBeLessThan aceDiamonds

        twoClubs shouldBeGreaterThan aceClubs
        aceDiamonds shouldBeGreaterThan twoClubs
    }

    "Cards have sensible hash codes" {
        withClue("Hashes for same rank & suit") {
            aceClubs.hashCode() shouldBe BaccaratCard(Card.Rank.ACE, Card.Suit.CLUBS).hashCode()
        }
        withClue("Hashes for different ranks") {
            aceClubs.hashCode() shouldNotBe twoClubs.hashCode()
        }
        withClue("Hashes for different suits") {
            aceClubs.hashCode() shouldNotBe aceDiamonds.hashCode()
        }
    }
})
