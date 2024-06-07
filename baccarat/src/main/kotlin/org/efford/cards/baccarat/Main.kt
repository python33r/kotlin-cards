package org.efford.cards.baccarat

/**
 * Entry point for Baccarat simulation.
 *
 * This program can be run in interactive mode by including `-i` or
 * `--interactive` as a command line argument; otherwise it defaults to
 * non-interactive mode.
 */
fun main(args: Array<String>) {
    val game = BaccaratGame()
    val interact = args.contains("-i") || args.contains("--interactive")
    game.play(interact)
}
