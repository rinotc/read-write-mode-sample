package domain

import java.util.UUID

/** 採用ポジションID */
final case class PositionId (value: UUID)

object PositionId {
  def gen(): PositionId = apply(UUID.randomUUID())
}
