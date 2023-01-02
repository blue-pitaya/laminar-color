package xyz.bluepitaya.laminarcolor

import scala.util.Random

trait HtmlIdGenerator {
  def randomId: String
}

object HtmlIdGenerator {
  // TODO: side effects!
  implicit object ImpureRandomStringIdGenerator extends HtmlIdGenerator {
    private val length = 8
    private val seed = 42099769
    private val random = new Random(seed)

    override def randomId: String = random.alphanumeric
      .take(length)
      .toList
      .mkString

  }
}
