package gtc.complex

import domain._

import java.time.LocalDateTime

/**
 * 採用選考
 *
 * @param id
 *   採用選考ID
 * @param positionId
 *   ポジションID
 * @param applicant
 *   応募者
 * @param interviews
 *   面接
 * @param status
 *   選考ステータス
 * @param applyDateTime
 *   応募日時
 */
final class Screening[M <: Mode] private (
    val id: ScreeningId,
    val positionId: PositionId,
    val applicant: Applicant,
    val interviews: Seq[Interview],
    val status: ScreeningStatus,
    val applyDateTime: LocalDateTime
) {

  def asRead: Screening[Read] = copy[Read]()

  /**
   * 面接の追加
   */
  def addInterview(interviewAt: LocalDateTime)(implicit ev: M =:= Write): Either[String, Screening[Write]] = {
    if (status.isInProgress) {
      val nowMaxPhase  = interviews.map(_.phase).maxOption.getOrElse(0)
      val newInterview = Interview(nowMaxPhase + 1, interviewAt)
      Right(copy(interviews = interviews.appended(newInterview)))
    } else {
      Left("cannot add interview.")
    }
  }

  /** 採用 */
  def adopt()(implicit ev: M =:= Write): Screening[M] = copy(status = ScreeningStatus.Adopted)

  /** 不採用 */
  def reject()(implicit ev: M =:= Write): Screening[M] = copy(status = ScreeningStatus.Rejected)

  override def equals(other: Any): Boolean = other match {
    case that: Screening[_] => id == that.id
    case _                  => false
  }

  override def hashCode(): Int = 31 * id.##

  private def copy[MM <: Mode](
      positionId: PositionId = this.positionId,
      applicant: Applicant = this.applicant,
      interviews: Seq[Interview] = this.interviews,
      status: ScreeningStatus = this.status,
      applyDateTime: LocalDateTime = this.applyDateTime
  ): Screening[MM] = new Screening[MM](this.id, positionId, applicant, interviews, status, applyDateTime)
}

object Screening {

  /**
   * 採用選考の新規作成
   */
  def create(
      positionId: PositionId,
      applicant: Applicant
  ): Screening[Write] = new Screening[Write](
    id = ScreeningId.gen(),
    positionId = positionId,
    applicant = applicant,
    interviews = Seq.empty,
    status = ScreeningStatus.InProgress,
    applyDateTime = LocalDateTime.now()
  )

  def reconstruct[M <: Mode](
      id: ScreeningId,
      positionId: PositionId,
      applicant: Applicant,
      interviews: Seq[Interview],
      status: ScreeningStatus,
      applyDateTime: LocalDateTime
  ): Screening[M] = new Screening[M](id, positionId, applicant, interviews, status, applyDateTime)
}
