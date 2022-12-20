package gtc.complex

import domain.ScreeningId
import gtc.complex.AddInterviewUseCase.{Input, Output}

import java.time.LocalDateTime

class AddInterviewUseCase(
    screeningRepository: ScreeningRepository
) {
  import Output._
  def handle(input: Input): Output = {
    val result = for {
      screening              <- screeningRepository.findById[Write](input.screeningId).toRight(NotFoundScreening)
      interviewAddedFeedback <- screening.addInterview(input.interviewAt).left.map { _ => CannotAddInterview }
    } yield {
      screeningRepository.insert(interviewAddedFeedback)
      Success(interviewAddedFeedback.asRead)
    }

    result.merge
  }
}

object AddInterviewUseCase {

  case class Input(
      screeningId: ScreeningId,
      interviewAt: LocalDateTime
  )

  sealed trait Output
  object Output {
    case class Success(screening: Screening[Read]) extends Output
    case object NotFoundScreening                  extends Output

    case object CannotAddInterview extends Output
  }
}
