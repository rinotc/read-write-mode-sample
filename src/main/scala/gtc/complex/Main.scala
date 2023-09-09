package gtc.complex

import domain.{Applicant, PositionId}
import gtc.complex.model.screening.{MemoryScreeningRepository, ScreeningRepository}
import gtc.complex.usecase.screening.CreateScreeningUseCase
import support.{IdGenerator, SimpleIdGenerator}

import java.util.UUID
import scala.io.StdIn.readLine

object Main {

  implicit val idGenerator: IdGenerator = new SimpleIdGenerator
  val screeningRepository               = MemoryScreeningRepository
  def main(args: Array[String]): Unit = {
    var loopFlg = true
    while (loopFlg) {
      val in = readLine("input> ")
      if (in == "q") loopFlg = false
      else if (in == "create") createScreeningUseCase()
      else if (in == "list") listScreeningUseCase()
      else ()
    }
  }

  private def createScreeningUseCase(): Unit = {
    val positionId    = readLine("positionId> ")
    val applicantName = readLine("applicant name> ")
    val email         = readLine("email> ")

    val input   = CreateScreeningUseCase.Input(PositionId(positionId.toInt), Applicant(applicantName, email))
    val usecase = new CreateScreeningUseCase(screeningRepository)
    usecase.handle(input) match {
      case CreateScreeningUseCase.Success(screening) => println(screening)
    }
  }

  private def listScreeningUseCase(): Unit = {
    println("all screenings")
    screeningRepository.list().foreach(println)
  }
}
