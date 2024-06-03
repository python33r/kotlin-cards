package org.efford.baccarat

/**
 * Entry point for Baccarat simulation.
 *
 * This program can be run in interactive mode by including <code>-i</code>
 * or <code>--interactive</code> as a command line argument; otherwise
 * it defaults to non-interactive mode.
 */
fun main(args: Array<String>) {
    val game = BaccaratGame(6)
    val isInteractive = args.contains("-i") || args.contains("--interactive")
    game.play(isInteractive)
}
