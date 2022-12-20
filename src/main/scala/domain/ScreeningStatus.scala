package domain

/**
 * 選考ステータス
 */
sealed trait ScreeningStatus {

  def isInProgress: Boolean = this == ScreeningStatus.InProgress
  def isAdopted: Boolean    = this == ScreeningStatus.Adopted
  def isRejected: Boolean   = this == ScreeningStatus.Rejected
}

object ScreeningStatus {

  /** 選考中 */
  case object InProgress extends ScreeningStatus

  /** 採用 */
  case object Adopted extends ScreeningStatus

  /** 不採用 */
  case object Rejected extends ScreeningStatus
}
