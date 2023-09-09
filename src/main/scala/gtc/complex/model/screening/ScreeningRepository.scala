package gtc.complex.model.screening

import domain.ScreeningId
import gtc.complex.arch.{Mode, Read, Write}

import scala.collection.mutable.ArrayBuffer

trait ScreeningRepository {

  def list(): Seq[Screening[Read]]

  def findById[M <: Mode](id: ScreeningId): Option[Screening[M]]

  def insert(screening: Screening[Write]): Unit

  def update(screening: Screening[Write]): Unit

  def delete(screening: Screening[Write]): Unit
}

object MemoryScreeningRepository extends ScreeningRepository {

  private val screenings: ArrayBuffer[Screening[_]] = ArrayBuffer.empty

  def list(): Seq[Screening[Read]] = {
    screenings.toSeq.map(_.asRead)
  }

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
