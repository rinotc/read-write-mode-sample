package domain

import support.IdGenerator

import java.util.UUID

/** 採用ポジションID */
final case class PositionId(value: Int)

object PositionId {
  def gen()(using idGenerator: IdGenerator): PositionId = apply(idGenerator.genId())
}
