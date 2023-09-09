package gtc.complex.arch

/**
 * Read or Write Mode.
 */
sealed trait Mode

sealed trait Read extends Mode

sealed trait Write extends Mode
