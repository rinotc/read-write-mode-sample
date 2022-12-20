package gtc.complex

import gtc.complex.SearchScreeningUseCase.{Input, Output}

class SearchScreeningUseCase(
    screeningQuery: ScreeningQuery
) {

  def handle(input: Input): Output = ???
}

object SearchScreeningUseCase {

  case class Input(applicantName: String)

  sealed trait Output
  object Output {}
}
