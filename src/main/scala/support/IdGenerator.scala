package support

trait IdGenerator {

  def genId(): Int
}

class SimpleIdGenerator extends IdGenerator {

  private var underlying: Int = 0

  def genId(): Int = {
    underlying += 1
    underlying
  }
}
