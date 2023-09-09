package domain

import support.IdGenerator

import java.util.UUID

/** 採用選考ID */
final case class ScreeningId(value: Int)

object ScreeningId {
  def gen()(using IdGenerator): ScreeningId = apply(summon[IdGenerator].genId())
}
