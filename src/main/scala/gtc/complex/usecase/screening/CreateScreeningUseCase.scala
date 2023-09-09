package gtc.complex.usecase.screening

import domain.{Applicant, PositionId}
import gtc.complex.arch.Read
import gtc.complex.model.screening.{Screening, ScreeningRepository}
import support.IdGenerator

class CreateScreeningUseCase(screeningRepository: ScreeningRepository)(using IdGenerator) {
  import CreateScreeningUseCase._

  def handle(input: Input): Output = {
    val newScreening = Screening.create(input.positionId, input.applicant)
    screeningRepository.insert(newScreening)
    Success(newScreening.asRead)
  }
}

object CreateScreeningUseCase {

  case class Input(positionId: PositionId, applicant: Applicant)

  sealed trait Output
  case class Success(screening: Screening[Read]) extends Output
}
