package gtc.complex

import domain.ScreeningId

import scala.collection.mutable.ArrayBuffer

class ScreeningRepository {

  private val screenings: ArrayBuffer[Screening[_]] = ArrayBuffer.empty

  def findById[M <: Mode](id: ScreeningId): Option[Screening[M]] = {
    screenings.find(_.id == id).map { s =>
      Screening.reconstruct[M](
        id = s.id,
        positionId = s.positionId,
        applicant = s.applicant,
        interviews = s.interviews,
        status = s.status,
        applyDateTime = s.applyDateTime
      )
    }
  }

  def insert(screening: Screening[Write]): Unit = {
    screenings.appended(screening).ensuring {
      screenings.map(_.id).toSet.size == screenings.length
    }
  }

  def update(screening: Screening[Write]): Unit = {
    screenings.filterNot(_.id == screening.id).appended(screening)
  }

  def delete(screening: Screening[Write]): Unit = {
    screenings.filterNot(_.id == screening.id)
  }
}
