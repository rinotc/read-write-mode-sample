package gtc.complex

trait ScreeningQuery {

  def searchBy(applicantName: String): Seq[Screening[Read]]
}
