package domain

import java.util.UUID

/** 採用選考ID */
final case class ScreeningId(value: UUID)

object ScreeningId {
  def gen(): ScreeningId = apply(UUID.randomUUID())
}
