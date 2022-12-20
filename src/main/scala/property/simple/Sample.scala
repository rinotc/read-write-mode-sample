package property.simple

class Sample private (
    private val canWrite: Boolean,
    val value: Int
) {
  def setValue(v: Int): Sample =
    if (canWrite) new Sample(canWrite, v)
    else throw new IllegalStateException
}

object Sample {

  def asRead(value: Int) = new Sample(false, value)

  def asWrite(value: Int) = new Sample(true, value)
}
