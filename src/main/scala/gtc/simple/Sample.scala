package gtc.simple

import gtc.simple.Sample.{Mode, Write}

class Sample[M <: Mode] private (val value: Int) {

  def setValue(v: Int)(implicit ev: M =:= Write) = new Sample[M](v)
}

object Sample {

  sealed trait Mode
  sealed trait Read  extends Mode
  sealed trait Write extends Mode

  def asWrite(value: Int): Sample[Write] = new Sample[Write](value)
  def asRead(value: Int): Sample[Read]   = new Sample[Read](value)
}

